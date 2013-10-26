package fr.esiea.fc.tasks.security.users.add;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller to change the password
 * @author Dispa Cécile
 */
public class ChangePassword extends SimpleFormController {

    public ChangePassword() {
        setCommandClass(User.class);
        setCommandName("setPasswordForm");
    }

    @Override
    public boolean isFormSubmission(HttpServletRequest request)
    {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("1".equals(isSubmit));
    }

   
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {

            String user_id=null;
            String userIp = SessionManager.getIp(request);
            HttpSession session = request.getSession(false);
            if(session!=null)
            {
                user_id = (String)session.getAttribute("user_id_tochange");
            }
            
            if(user_id==null)
            {
                errors.rejectValue("password", "users.modify.database", "Les paramètres sont incorrects");
                return this.showForm(request, response, errors);
            }
            
            SessionManager.close(request);

            User user = (User) command;

            user.setReset_password(false);

            if (!UserDAO.changePassword(user)) {
                Log.write("L'utilisateur [" + user.getId() + "] n'a pas pu être actualisé");

                errors.rejectValue("password", "users.modify.database", "Le mot de passe de l'utilisateur n'a pas pu être modifié");
                ActivityDAO.insert(
                                   user.getId(), "Echec lors du changement de mot de passe de l'utilisateur" + user.getId(), userIp);
                return this.showForm(request, response, errors);
            }
            ActivityDAO.insert(user.getId(), "Le mot de passe de l'utilisateur " + user.getId() + " a été actualisé", userIp);
            RedirectView view=new RedirectView("auth.do");
            ModelAndView mv=new ModelAndView(view);
            mv.addObject("user_id", user.getId());
            mv.addObject("user_pwd", user.getPassword());
            return mv;
        } catch (Exception ex) {
            Log.write(ex);
            errors.rejectValue("password", "users.modify.database", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception 
    {
        User user = new User();
        String user_id=null;
        HttpSession session=request.getSession(false);
        if(session!=null)
        {
            user_id=(String)session.getAttribute("user_id_tochange");
        }
        user.setId(user_id);
        return user;
    }

}
