package fr.esiea.fc.control.auditor;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.util.Log;
import fr.esiea.fc.util.error.ControllerActionUnknown;
import fr.esiea.fc.util.error.DataMissingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller for the activity report
 * @author  Michel Messak
 */
public class ActivityController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] fields = new String[]{"Utilisateur", "Date", "Heure", "Description", "Adresse IP"};
        String[] widths = {"10%", "10%", "10%", "60%","10%"};
        String[] aligns = {"left", "left", "left", "left", "left"};
        Boolean[] visible = {true, true, true, true, true};
        Boolean[] sort = {true, true, true, true, true};
        Boolean[] search = {true, true, true, true, true};

        try {

            /**
             * Este controlador es llamado mas de 1 vez, por las siguientes razones:
             * 1. Desde el menu general. Aqui debe mostrar la ventana de filtros del reporte
             * 2. Por errores en captura de datos del filtro. Aqui debe mostrar los campos capturados junto con los errores
             * 3. Por submit correcto (validacion OK) de los filtros del reporte. Aqui debe mostrar el reporte
             * 4. Por llamadas asincronas del grid "datatable" de jquery usando AJAX
             * 5. Por llamadas desde otras tareas. Aqui debe mostrar la ventana de reporte porque los filtros son proporcionados por la tarea
             */
            Report report = null;

            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }
            String userId = SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);
            
            String query = "select user_id, act_date, act_time, act_description, act_ip_address from period.activities ";

            if (Report.isFilterCall(request)) {
                report = new Report(fields, query, request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);

                setFilterCallParameters(request, report);

                request.getSession().setAttribute("report", report);

                report.ExecuteQuery(request);
                ModelAndView mv = new ModelAndView("report");
                mv.addObject("data", report);
             
                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
                ActivityDAO.insert(userId, "Consultation du rapport d'activité", userIp);
                
                return mv;
            } else if (Report.isAjaxCall(request)) {
                report = (Report) request.getSession().getAttribute("report");
                if (report == null) {
                    throw new DataMissingException("Les données n'ont pas pu être récupéré.");
                }
                report.configureDatatableParameters(request);
                ModelAndView mv = new ModelAndView("pager");
                mv.addObject("data", report);
                return mv;

            } else if (Report.isMenuCall(request)) {
                List<User> users = UserDAO.selectAll();
                ModelAndView mv = new ModelAndView("filterActivity");

                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

                mv.addObject("users", users);
                return mv;
            }

            else if (Report.isPrevious(request)) {
                List<User> users = UserDAO.selectAll();
                ModelAndView mv = new ModelAndView("filterActivity");

                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
                mv.addObject("users", users);
                return mv;
            }

            else {
                throw new ControllerActionUnknown("L'appel à la consultation du rapport d'activité est inconu");
            }
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }

    private void setFilterCallParameters(HttpServletRequest request, Report report) throws Exception {
        try {
            report.clearWhere();
            report.addWhere("user_id", request.getParameter("user_id"));
            report.addWhereBetween("act_date", request.getParameter("date_init"), request.getParameter("date_end"));
            report.addWhereBetween("act_time", request.getParameter("hour_init"), request.getParameter("hour_end"));
        } catch (Exception ex) {
            throw ex;
        }

    }
}
