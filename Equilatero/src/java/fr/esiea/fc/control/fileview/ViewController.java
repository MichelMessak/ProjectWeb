package fr.esiea.fc.control.fileview;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to visualize an archive
 * @author Dispa Cécile
 */
public class ViewController implements Controller
{

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            if(!SessionManager.hasSession(request))
                return SessionManager.getLoginView(request);

            String archive = request.getParameter("archive");
            String repository = request.getParameter("repository");
            String download = request.getParameter("download");
            ModelAndView mv = new ModelAndView("urlencoder");
            mv.addObject("archive", archive);
            mv.addObject("repository", repository);
            mv.addObject("download", download);
            return mv;
        }
        catch (Exception ex)
        {
            ModelAndView mv=new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }
}
