package fr.esiea.fc.control.security;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to manage the main page
 * @author Dispa Cécile
 */
public class HomeController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            ModelAndView mv = new ModelAndView("menu");
            mv.addObject("message", request.getParameter("message"));
            
            request.getSession().setAttribute("report", null);
            return mv;
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }
}
