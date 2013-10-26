package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller fr modify an User
 * @author Micchel Messak
 */
public class ModifyUserController extends SimpleFormController {

    public ModifyUserController() {
        setCommandClass(User.class);
        setCommandName("modifyUserForm");
    }

    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("true".equals(isSubmit));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            User user = (User) command;

            if (!UserDAO.modify(user)) {
                Log.write("L'utilisateur n'a pas pu être modifié : " + user.getId());

                errors.rejectValue("id", "users.update.database", "L'utilisateur n'a pas pu être modifié");
                ActivityDAO.insert(SessionManager.getUserID(request), "Echec lors de la modification de l'utilisateur " + user.getId(), SessionManager.getIp(request));
                return this.showForm(request, response, errors);
            }            
          

            ActivityDAO.insert(SessionManager.getUserID(request), "Utilisateur " + user.getId() + " a été mdifié", SessionManager.getIp(request));
            return new ModelAndView(getSuccessView(), getCommandName(), user);
        } catch (Exception ex) {
            Log.write(ex);
            errors.rejectValue("id", "users.update.database", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        String resetPassword=request.getParameter("reset_password");
        if(resetPassword==null) resetPassword="unchecked";

        User user = new User(request.getParameter("user_id"), request.getParameter("user_name"),
                request.getParameter("user_email"), request.getParameter("user_pwd"),
                null,request.getParameter("user_status"),(resetPassword.equals("checked") || resetPassword.equals("true") || resetPassword.equals("on"))?true:false);

         request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

        return user;
    }

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {

		Map referenceData = new HashMap();

		List<String> statuses = UserDAO.getAllStatus();
		referenceData.put("statuses", statuses);

		return referenceData;
	}
}
