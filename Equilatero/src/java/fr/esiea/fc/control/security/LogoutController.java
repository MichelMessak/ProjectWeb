package fr.esiea.fc.control.security;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to manage the end of a session
 * @author Michel Messak
 */
public class LogoutController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = null;

        try
        {
            session = request.getSession(false);
            if (session != null)
            {
                String userName = (String) session.getAttribute("user_name");
                String userId = (String)session.getAttribute("user_id");
                String perID = (String)session.getAttribute("per_id");
                String userIp = SessionManager.getIp(request);
                if (userName != null)
                {
                    Log.write("Finalisation de la session de l'utilisateur [" + userName + "]");
                } 
                else
                {
                    Log.write("Finalisation de la session");
                }
                if(perID!=null && userId!=null)
                    ActivityDAO.insert(userId, "Fin de session", userIp);
                if (session != null) {
                    session.invalidate();
                }
            }
            ModelAndView mv = new ModelAndView("index");
            return mv;
        } catch (Exception ex)
        {
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("exception", ex);
            Log.write(ex);
            if(session!=null) session.invalidate();
            return mv;
        }
    }
}
