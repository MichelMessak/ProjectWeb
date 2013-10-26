package fr.esiea.fc.tasks.security.users.add;

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

/**
 * Controller for addinf an user
 * @author Michel Messak
 */
public class AddUserController extends SimpleFormController {

    public AddUserController() {
        setCommandClass(User.class);
        setCommandName("addUserForm");
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
    protected Object formBackingObject(HttpServletRequest request) throws Exception
    {
        request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
        return new User();
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            User user = (User) command;
            if (!UserDAO.add(user)) {
                Log.write("L'utilisateur [" + user.getId() + "] n'a pas pu être ajouté");

                errors.rejectValue("id", "users.add.database", "L'utilisateur n'a pas pu être ajouté");
                ActivityDAO.insert(SessionManager.getUserID(request), "Echec lors de l'ajout de l'utilisateur " + user.getId(), SessionManager.getIp(request));
                return this.showForm(request, response, errors);
            }
            
            ActivityDAO.insert(SessionManager.getUserID(request), "Utilisateur " + user.getId() + " Ajouté", SessionManager.getIp(request));
            return new ModelAndView(getSuccessView(), getCommandName(), user);
        } catch (Exception ex) {
            Log.write(ex);
            errors.rejectValue("id", "users.add.database", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }
}
