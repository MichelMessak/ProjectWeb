package fr.esiea.fc.tasks.admin.log;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Cotroller to manage all the logs
 * @author  Michel Messak
 */
public class LogController implements Controller {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        try {
            
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            String userId = SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);            
            
            String ajaxRequest = request.getParameter("AjaxRequestChildTasks");
            
            if(ajaxRequest!=null)
            {
                
                String dateTest = request.getParameter("date");
                if (dateTest == null) {
                    throw new Exception("La date n'a pas été sélectioné");
                }

                Date date = dateFormat.parse(dateTest);
                String logName = Log.getFileName(date);

                File file = new File(logName);
                if (!file.exists()) {
                    ModelAndView mv = new ModelAndView("askTask");
                    mv.addObject("error", "true");
                    return mv;
                }
                ModelAndView mv = new ModelAndView("askTask");
                mv.addObject("correct", "true");
                return mv;

            }
            if (Report.isMenuCall(request)) {
                ModelAndView mv = new ModelAndView("filterLogs");

                              
                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

                return mv;
            }
            
            String dateTest = request.getParameter("date_init");
            if (dateTest == null) {
                throw new Exception("Aucune date n'a été sélectioné");
            }
            Date date = dateFormat.parse(dateTest);
            String logName = Log.getFileName(date);

            String chain = "";
            int i = 0;
            InputStream ips=new FileInputStream(logName);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String line;
            while ((line=br.readLine())!=null){
                    System.out.println(line);
                    chain+=line+"\n";i++;
            }
            br.close();

            ModelAndView mv = new ModelAndView("logs");
            mv.addObject("chain", chain);
            ActivityDAO.insert(userId, "Consultation des logs :" + logName + " du jour : " + dateTest, userIp);
            return mv;
            
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }
}
