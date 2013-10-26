
package fr.esiea.fc.tasks.document.reports.generic;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.admin.Enterprise;
import fr.esiea.fc.model.admin.EnterpriseDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.util.Log;
import fr.esiea.fc.util.error.ControllerActionUnknown;
import fr.esiea.fc.util.error.DataMissingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller for document's report
 * @author Michel Messak
 */
public class DocumentReportController implements Controller {


    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] fields = new String[]{"ID de l'entreprise", "Type", "Sous-Type", "ID", "UUID", "Date de création", "Heure de création",  "Statut"};
        String[] widths = {"15%", "10%", "5%", "30%", "15%","10%", "10%", "5%"};
        String[] aligns = {"center", "center", "center","center", "Center", "left", "left", "right" };
        Boolean[] visible = {true, true, true,true, true, true, true, true};
        Boolean[] sort = {true, true, true, true,true,  true, true, true};
        Boolean[] search = {true, true, true,true, true,  true, true, true};

        String[] colExtras = {
             };

        try {

            Report report = null;

            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }
            request.getAttributeNames();

            String query = "SELECT documents.emp_id, documents.dty_id, documents.dst_id, documents.doc_id, dtype_cfd.cfd_id2, documents.doc_creation_date, documents.doc_creation_time, documents.doc_status from period.documents"
                    +" LEFT OUTER JOIN period.dtype_cfd ON period.documents.doc_id = period.dtype_cfd.doc_id";


            String userId = (String) SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);

            if (Report.isFilterCall(request)) {
                
                report = new Report(fields, query, request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnExtras(colExtras);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);
                setFilterCallParameters(request, report);

                request.getSession().setAttribute("report", report);

                    report.ExecuteQuery(request);

                ModelAndView mv = new ModelAndView("report");
                mv.addObject("data", report);

                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
                ActivityDAO.insert(userId, "Consultation des documents de l'entreprise "+request.getParameter("emp_id"), userIp);

                return mv;
            }


            else if (Report.isPrevious(request))
            {

                 ModelAndView mv = null;

                    mv = new ModelAndView("filterDocGen");        
                    mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

                List<Enterprise> emps = EnterpriseDAO.userEnterprises((String) request.getSession().getAttribute("user_id"), "reportDocument.task");
                mv.addObject("enterprises", emps);
                return mv;
            }

            else if (Report.isTaskCall(request)) {

                report = new Report(fields, query, request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnExtras(colExtras);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);
                report.clearWhere();

                report.setTaskCallParameters(request);

                request.getSession().setAttribute("report", report);
                report.ExecuteQuery(request);
                ModelAndView mv = new ModelAndView("report");
                mv.addObject("data", report);

                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

                return mv;
            }

            else if (Report.isAjaxCall(request))
            {
                try
                {
                    report = (Report) request.getSession().getAttribute("report");
                    if (report == null) {
                        throw new DataMissingException("Les données n'ont pas pu être récupéré");
                    }

                    report.configureDatatableParametersDocuments(request);
                    ModelAndView mv = new ModelAndView("pager");
                    mv.addObject("data", report);
                    return mv;
                }
                catch(Exception ex)
                {
                    ModelAndView mv = new ModelAndView("pager");
                    return mv;
                }

            } else if (Report.isMenuCall(request))
            {

                List<Enterprise> emps = EnterpriseDAO.userEnterprises((String) request.getSession().getAttribute("user_id"), request.getRequestURI().substring(request.getContextPath().length()+1));
                ModelAndView mv = new ModelAndView("filterDocGen");
                mv.addObject("enterprises", emps);

                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

                return mv;
            }
            else {
                throw new ControllerActionUnknown("Appel à la consultation des documents inconnu");
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


                if (request.getParameter("emp_id")!= null)
                    report.addWhere("emp_id", request.getParameter("emp_id"));

                else
                    report.addWhere("emp_id", "Not an enterprise");

            report.addWhereBetween("doc_creation_date", request.getParameter("date_init"), request.getParameter("date_end"));
            
            if (request.getParameter("UUID").equals(""))
                report.addWhereBetween("doc_id", request.getParameter("ID_ini"), request.getParameter("ID_fin"));
            
            else 
                report.addWhere("doc_id",request.getParameter("UUID"));
            
        } catch (Exception ex) {
            throw ex;
        }

    }
}
