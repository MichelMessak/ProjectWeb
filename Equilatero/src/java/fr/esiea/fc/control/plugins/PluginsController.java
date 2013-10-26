package fr.esiea.fc.control.plugins;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.util.Log;
import fr.esiea.fc.util.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to manage additionals task
 * @author Dispas Cécile
 */

public class PluginsController implements Controller
{
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            if(!SessionManager.hasSession(request))
                return SessionManager.getLoginView(request);

            String taskURL=request.getRequestURI();
            int pos=taskURL.lastIndexOf("/");
            if(pos>=0)
                taskURL=taskURL.substring(pos+1);
            pos=taskURL.lastIndexOf(".do");
            if(pos>=0)
                taskURL=taskURL.substring(0, pos);
            String className=taskURL+".TaskController";
            Object object=Util.createClassInstance(className, new Object[]{});
            if(object==null)
                throw new ClassNotFoundException("Class "+className+" do not exist");
            ModelAndView mv = (ModelAndView)Util.invokeClassMethod(object, "handleRequest", new Object[]{request, response},
                    new Class[]{HttpServletRequest.class, HttpServletResponse.class});

            return mv;
        }
        catch(Exception ex)
        {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }


}
