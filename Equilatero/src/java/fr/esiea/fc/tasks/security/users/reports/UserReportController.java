package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.services.UrlValidatorService;
import fr.esiea.fc.util.Log;
import fr.esiea.fc.util.error.ControllerActionUnknown;
import fr.esiea.fc.util.error.DataMissingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller for user report
 * @author Dispa Cécile
 */
public class UserReportController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        String ajaxRequest = request.getParameter("AjaxRequestChildTasks");

            String userId = SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);
        if(ajaxRequest!=null)
        {
            if (!SessionManager.hasSession(request))
                    {
                        throw new Exception("La session n'a pas été initialisé");
                    }

                    String userID = request.getParameter("user_id");
                    String URI = request.getParameter("URI");
                    String user = request.getParameter("usuario");
                    String action = request.getParameter("accion");
                    UrlValidatorService validate = new UrlValidatorService();
                    int resVal = validate.validateUrlChildTasks(URI,request);

                        if (resVal == 0)
                        {
                            ModelAndView mv = new ModelAndView("askTask");
                            mv.addObject("error", "Désolé,l'utilisateur: "+ userID+", n'a pas de permission.");
                            mv.addObject("action", action);
                            ActivityDAO.insert(userId, "Essai d'effectuer la tache: "+URI, userIp);
                            return mv;
                        }
                        else if (resVal == 1)
                        {
                           ModelAndView mv = new ModelAndView("askTask");
                           ActivityDAO.insert(userId, "Accès à la tache permise : "+URI, userIp);
                           mv.addObject("correct", "paso");
                           mv.addObject("user", user);
                           mv.addObject("action", action);
                           ActivityDAO.insert(userId, "Acceso a tarea PErmitida: "+URI, userIp);

                           return mv;
                        }
                        else
                        {
                            throw new ControllerActionUnknown("Petición de tarea desconocida.");
                        }
        }

        String[] fields = new String[]{"Utilisateur", "Nom", "Courriel","Etat","Changer de Mot de passe"};
        String query = "select user_id, user_name, user_email, user_status, user_pwdreset from " + UserDAO.TABLE_NAME;
        String[] widths = {"20%", "30%", "20%", "10%", "10%", "5%", "5%"};
        String[] aligns = {"left", "left", "left", "left", "left", "center", "center"};
        Boolean[] visible = {true, true, true, true, true, true, true};
        Boolean[] sort = {true, true, true, false, false, false, false};
        Boolean[] search = {true, true, true, false, false, false, false};

        String[] colExtras = new String[]
        {
            "<form action=\"deleteUser.form\" method=\"post\" name=\"{0}\" id=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Elimina\" src=\"images/deleteIcon.png\" style=\"width: 20px; display:none;\" title=\"Eliminar Usuario\"/>" +
                    "<input type=\"hidden\" name=\"user_id\" value=\"{0}\"/>" +
                    "<input type=\"hidden\" name=\"user_name\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
            "</form>"+
            "<input type=\"image\" src=\"images/deleteIcon.png\" onclick=\"sendRequest('reporteGenericoUsuarios.task', 'deleteUser.form','{0}','delete')\" style=\"width: 20px;\" title=\"Modificar Usuario\"/>",
            "<form action=\"modifyUser.form\" method=\"post\" name=\"{0}\" id=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Modifica\" src=\"images/editIcon.png\" style=\"width: 20px; display:none;\" title=\"Modificar Usuario\"/>" +
                    "<input type=\"hidden\" name=\"user_id\" value=\"{0}\"/>" +
                    "<input type=\"hidden\" name=\"user_name\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"user_email\" value=\"{2}\"/>" +
                    "<input type=\"hidden\" name=\"user_status\" value=\"{3}\"/>" +
                    "<input type=\"hidden\" name=\"reset_password\" value=\"{4}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
           "</form>"+
                    "<input type=\"image\" src=\"images/editIcon.png\" onclick=\"sendRequest('reporteGenericoUsuarios.task', 'modifyUser.form' ,'{0}','modify')\" style=\"width: 20px;\" title=\"Modificar Usuario\"/>"
        };

        try {

            /**
             * Este controlador es llamado mas de 1 vez, por las siguientes razones:
             * 1. Desde el menu general. Aqui debe mostrar la ventana de filtros del reporte
             * 2. Por llamadas asincronas del grid "datatable" de jquery usando AJAX
             */
            Report report = null;
            if (!SessionManager.hasSession(request))
            {
                return SessionManager.getLoginView(request);
            }


            //Para consultas parametrizadas desde otras tareas
            if (Report.isAjaxCall(request)) {
                report = (Report) request.getSession().getAttribute("report");
                if (report == null) {
                    throw new DataMissingException("Les données n'ont pas pu être récupéré");
                }

                report.configureDatatableParameters(request);
                ModelAndView mv = new ModelAndView("pager");
                mv.addObject("data", report);
                return mv;

            } else if (Report.isMenuCall(request)) {
                ActivityDAO.insert(SessionManager.getUserID(request), "Consultation des utilisateurs", SessionManager.getIp(request));


                if (!SessionManager.getUserID(request).equalsIgnoreCase("admin"))
                query = "select user_id, user_name, user_email, user_status, user_pwdreset from " + UserDAO.TABLE_NAME
                        + " where user_id <> 'admin'";
                report = new Report(fields, query, request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnExtras(colExtras);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);

                request.getSession().setAttribute("report", report);
                report.ExecuteQuery(request);

                ModelAndView mv = new ModelAndView("user");
                mv.addObject("data", report);

                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
              return mv;
            } else {
                throw new ControllerActionUnknown("Appel a la consultation des utilisateurs inconnu.");
            }
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }

    }
}
