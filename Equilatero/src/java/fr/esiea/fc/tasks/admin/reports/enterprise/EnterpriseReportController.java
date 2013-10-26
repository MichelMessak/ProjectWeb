package fr.esiea.fc.tasks.admin.reports.enterprise;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.util.Log;
import fr.esiea.fc.util.error.ControllerActionUnknown;
import fr.esiea.fc.util.error.DataMissingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EnterpriseReportController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String query = "select emp_id, emp_name from common.enterprises where emp_user = '"+SessionManager.getUserID(request)+"'";
        String[] fields = new String[]{"ID", "Nom"};
        String[] widths = {"30%", "60%", "5%", "5%"};
        String[] aligns = {"left", "left", "center", "center"};
        Boolean[] visible = {true, true, true, true};
        Boolean[] sort = {true, true, false, false};
        Boolean[] search = {true, true, false, false};

        String[] colExtras = new String[]{
            "<form action=\"deleteEnterprise.form\" method=\"post\" name=\"{0}\" id=\"{0}\"><input type=\"image\" src=\"images/deleteIcon.png\" title=\"Supprimer une entreprise\"/><input type=\"hidden\" name=\"emp_id\" value=\"{0}\"/><input type=\"hidden\" name=\"emp_name\" value=\"{1}\"/><input type=\"hidden\" name=\"isSubmit\" value=\"false\"/></form>",
            "<form action=\"modifyEnterprise.form\" method=\"post\" name=\"{0}\" id=\"{0}\"><input type=\"image\" src=\"images/editIcon.png\" style=\"width: 20px;\" title=\"Modifier une entreprise\"/><input type=\"hidden\" name=\"emp_id\" value=\"{0}\"/><input type=\"hidden\" name=\"emp_name\" value=\"{1}\"/><input type=\"hidden\" name=\"isSubmit\" value=\"false\"/></form>"
        };

        try {

            /**
             * Este controlador es llamado mas de 1 vez, por las siguientes razones:
             * 1. Desde el menu general. Aqui debe mostrar la ventana de filtros del reporte
             * 2. Por llamadas asincronas del grid "datatable" de jquery usando AJAX
             */
            Report report = null;

            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }
            String userIp = SessionManager.getIp(request);
            String userId = SessionManager.getUserID(request);

            if (Report.isAjaxCall(request)) {
                report = (Report) request.getSession().getAttribute("report");
                if (report == null) {
                    ActivityDAO.insert(userId, "Echec de la consultation des entreprises", userIp);
                    throw new DataMissingException("Les données n'ont pas pu être récupéré");
                }

                report.configureDatatableParameters(request);
                ModelAndView mv = new ModelAndView("pager");
                mv.addObject("data", report);

                return mv;

            } else if (Report.isMenuCall(request)) {
                ActivityDAO.insert(userId, "Consultation des entreprises", userIp);
                
                report = new Report(fields, query, request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnExtras(colExtras);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);

                request.getSession().setAttribute("report", report);
                report.ExecuteQuery(request);

                ModelAndView mv = new ModelAndView("enterprise");
                mv.addObject("data", report);
                
                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));


                return mv;
            } else {
                throw new ControllerActionUnknown("Appel à la consultation des entreprises inconnu.");
            }
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }
}
