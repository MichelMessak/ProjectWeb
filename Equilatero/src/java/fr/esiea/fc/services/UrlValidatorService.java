package fr.esiea.fc.services;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.security.Task;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * URL Validation
 * @author Michel Messak
 */
public class UrlValidatorService {

    public int validateUrl(HttpServletRequest request)
    {
        int status = 0;

            HttpSession session = request.getSession(false);
            Boolean isReset_password =  (Boolean) session.getAttribute("isReset_password");

            String URL = (String) request.getRequestURL().toString();
            
                if(SessionManager.getUserID(request) != null)
                {
                    if(!SessionManager.getUserID(request).equals("admin"))
                    {
                        if(isReset_password==null) isReset_password=false;
                        if(isReset_password)
                        {
                            status = 3;
                        }
                        else
                        {
                            List<Task> menuUser = SessionManager.getAllUserTasks(request);
                            for (Task task : menuUser) {
                                if (task.getURL().equals(request.getRequestURI().substring(request.getContextPath().length()+1)))
                                {
                                    status = 1;
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        status = 1;
                    }
                }
                else
                {
                     status = 2;
                }
            
        return status;
    }

    /**
 * Valida las URL via AJAX precibiendo la URL a la que se solicita saber si el usuario cuenta con permiso de acceso.
 */
    public int validateUrlChildTasks(String string ,HttpServletRequest request)
    {
        int status = 0;
        List<Task> menuUser = SessionManager.getAllUserTasks(request);
        for (Task task : menuUser)
        {
            if (task.getURL().equals(string))
            {
                status = 1;
                break;
            }
        }
        return status;
    }
}
