package fr.esiea.fc.control.config;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.Configuration;
import fr.esiea.fc.util.Log;
import com.itc.repository.fc4Repository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to manage the configuration
 * @author Michel Messak
 */

public class SettingsController implements Controller
{
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            if(!SessionManager.hasSession(request))
                return SessionManager.getLoginView(request);

            ModelAndView mv=new ModelAndView("settings");
            Configuration cnf = new Configuration();
            fc4Repository.Init();
            mv.addObject("jdbcURL", fc4Repository.fc4Properties.getProperty("fc4.jdbcUrl"));
            mv.addObject("jdbcDriver", fc4Repository.fc4Properties.getProperty("fc4.jdbcDriverClass"));
            mv.addObject("jdbcUser", fc4Repository.fc4Properties.getProperty("fc4.jdbcUser"));
            mv.addObject("jdbcPassword", fc4Repository.fc4Properties.getProperty("fc4.jdbcPassword"));
            mv.addObject("Repositorio", fc4Repository.fc4Properties.getProperty("fc4.repository"));
            mv.addObject("Logs", fc4Repository.fc4Properties.getProperty("fc4.log"));
            mv.addObject("poolMaximoActivos", fc4Repository.fc4Properties.getProperty("fc4.jdbcMaxActive"));
            mv.addObject("poolTamanhoInicial", fc4Repository.fc4Properties.getProperty("fc4.jdbcInitialSize"));
            mv.addObject("MaxIntentosFallidos", fc4Repository.fc4Properties.getProperty("fc4.maxFailAttemps"));
            mv.addObject("settings", cnf);
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
