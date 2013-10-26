package fr.esiea.fc.control;

import fr.esiea.fc.model.security.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

/**
 * Session Manager of the user
 * @author Michel Messak
 */

public class SessionManager
{
    public static boolean hasSession(HttpServletRequest request)
    {
        HttpSession session=request.getSession(false);
        String user_id = null;
        if(session!=null)
        {
            user_id = (String)session.getAttribute("user_id");
        }
        return(session!=null && user_id!=null);
    }

    public static void redirectToLogin(HttpServletResponse response) throws IOException
    {
        response.sendRedirect("/index.htm");
    }

    public static ModelAndView getLoginView(HttpServletRequest request)
    {
        ModelAndView view=new ModelAndView("index");
        view.addObject("from", request.getRequestURI());
        if(!hasSession(request))
            view.addObject("error", "Session expirée");
        return view;
    }

    public static ModelAndView getLoginView(HttpServletRequest request, String error)
    {
        ModelAndView view=new ModelAndView("index");
        view.addObject("from", request.getRequestURI());
        view.addObject("error", error);
        return view;
    }

    public static String getIp(HttpServletRequest request){

         String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(ip!=null && ip.equals("0:0:0:0:0:0:0:1"))
            ip="127.0.0.1"; //localhost
        return ip;  

    }

    public static String getUserID(HttpServletRequest request)
    {
        HttpSession session=request.getSession(false);
        if(session==null) return null;
        String userID = (String)session.getAttribute("user_id");
        return userID;
    }

    public static void close(HttpServletRequest request)
    {
        HttpSession session=request.getSession(false);
        if(session!=null)
            session.invalidate();
    }

    public static List<Task> getAllUserTasks(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        List<Task> allTaskForUser = (List) session.getAttribute("UserTasks");
        return allTaskForUser;
    }

    public static List<Task> getMenuTask(HttpServletRequest request){

        HttpSession session=request.getSession(false);
        List<Task> allTaskForUser = (Vector) session.getAttribute("UserTasks");
        List tasksForMenu = new ArrayList<Task>();
        
        for (int i = 0 ; i < allTaskForUser.size(); i++ )
        {           
            if (allTaskForUser.get(i).getType().equals("MENU")) {
                tasksForMenu.add(allTaskForUser.get(i));
            }
        }
        
        return tasksForMenu;
    }
}
