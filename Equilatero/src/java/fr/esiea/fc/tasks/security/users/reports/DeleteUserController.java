package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class DeleteUserController extends SimpleFormController {

    public DeleteUserController() {
        setCommandClass(User.class);
        setCommandName("deleteUserForm");
    }
    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("1".equals(isSubmit));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            User user = (User) command;

            if (!UserDAO.delete(user.getId())) {

                Log.write("L'utilisateur  " + user.getId() + "] n'a pas pu etre supprimé");
                errors.rejectValue("id", "users.delete.database", "!L'utilisateur n'a pas pu etre supprimé");
                ActivityDAO.insert(SessionManager.getUserID(request), "Echec lors de la supression de l'utilisateur" + user.getId(), SessionManager.getIp(request));
                return this.showForm(request, response, errors);
            }
            ActivityDAO.insert(SessionManager.getUserID(request), "L'utilisateur " + user.getId() + " a été éliminé", SessionManager.getIp(request));
            return new ModelAndView(getSuccessView(), getCommandName(), user);
        } catch (Exception ex) {
            Log.write(ex);
            errors.rejectValue("id", "users.delete.database", "L'utilisateur n'a pas pu être éliminé.");
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        User user = new User(request.getParameter("user_id"), request.getParameter("user_name"),
                request.getParameter("user_email"), request.getParameter("user_pwd"),
                request.getParameter("user_pwd"),null,null);

        request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
        return user;
    }
}