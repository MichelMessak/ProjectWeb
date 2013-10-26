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
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controlador de la forma de administración de Usuarios
 * @author Jahir Barojas Muñoz
 * @since 4.0.0.0
 */
public class ReporteGenericoUsuariosController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        String ajaxRequest = request.getParameter("AjaxRequestChildTasks");
		//validacion para saber si el request es mediante una llamada via ajax desde el JSP

            String userId = SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);
        if(ajaxRequest!=null)
        {
            if (!SessionManager.hasSession(request))
                    {
                        throw new Exception("Sesión no ha sido inicializada");
                    }

                    String userID = request.getParameter("user_id");
                    String URI = request.getParameter("URI");
                    String usuario = request.getParameter("usuario");
                    String accion = request.getParameter("accion");
                    UrlValidatorService validar = new UrlValidatorService();
                    int resVal = validar.validateUrlChildTasks(URI,request);

                        if (resVal == 0)
                        {
                            ModelAndView mv = new ModelAndView("askTask");
                            mv.addObject("error", "Lo sentimos. El usuario: "+ userID+", no cuenta con acceso.");
                            mv.addObject("action", accion);
                            ActivityDAO.insert(userId, "Intento Accesar a una tarea no permitida: "+URI, userIp);
                            return mv;
                        }
                        else if (resVal == 1)
                        {
                           ModelAndView mv = new ModelAndView("askTask");
                           ActivityDAO.insert(userId, "Acceso a tarea Permitida: "+URI, userIp);
                           mv.addObject("correct", "paso");
                           mv.addObject("user", usuario);
                           mv.addObject("action", accion);
                           ActivityDAO.insert(userId, "Acceso a tarea PErmitida: "+URI, userIp);

                           return mv;
                        }
                        else
                        {
                            throw new ControllerActionUnknown("Petición de tarea desconocida.");
                        }
        }

        String[] fields = new String[]{"Usuario", "Nombre", "Dirección de correo","Estado","Limpiar Password"};
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
            //"<form action=\"modificaUsuario.form\" method=\"post\" name=\"{0}\" id=\"{0}\"><input type=\"image\" src=\"images/editIcon.png\" onclick=\"sendRequest('reporteGenericoUsuarios.task', 'modificaUsuario.form')\" style=\"width: 20px;\" title=\"Modificar Usuario\"/><input type=\"hidden\" name=\"user_id\" value=\"{0}\"/><input type=\"hidden\" name=\"user_name\" value=\"{1}\"/><input type=\"hidden\" name=\"user_email\" value=\"{2}\"/><input type=\"hidden\" name=\"user_status\" value=\"{3}\"/><input type=\"hidden\" name=\"reset_password\" value=\"{4}\"/><input type=\"hidden\" name=\"isSubmit\" value=\"false\"/></form>"
            "<form action=\"modifyUser.form\" method=\"post\" name=\"{0}\" id=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Modifica\" src=\"images/editIcon.png\" style=\"width: 20px; display:none;\" title=\"Modificar Usuario\"/>" +
                    "<input type=\"hidden\" name=\"user_id\" value=\"{0}\"/>" +
                    "<input type=\"hidden\" name=\"user_name\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"user_email\" value=\"{2}\"/>" +
                    "<input type=\"hidden\" name=\"user_status\" value=\"{3}\"/>" +
                    "<input type=\"hidden\" name=\"reset_password\" value=\"{4}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
           "</form>"+
           //"<input type=\"image\" src=\"images/editIcon.png\" onclick=\"sendRequest('reporteGenericoUsuarios.task', 'modificaUsuario.form')\" style=\"width: 20px;\" title=\"Modificar Usuario\"/>"
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

            //Visualizar los parametros que vienen desde el ajax de datatable

            //Report.showDatatableParameters(request);
            /*
            Report.showRequestAttributes(request);
            Report.showRequestParameters(request);
            Report.showSessionAttributes(request);
             */

            //Para consultas parametrizadas desde otras tareas
            if (Report.isAjaxCall(request)) {
                report = (Report) request.getSession().getAttribute("report");
                if (report == null) {
                    throw new DataMissingException("Datos de Reporte no han podido ser recuperados para procesamiento asincrono");
                }

                report.configureDatatableParameters(request);
                ModelAndView mv = new ModelAndView("pager");
                mv.addObject("data", report);
                return mv;

            } else if (Report.isMenuCall(request)) {
                ActivityDAO.insert(SessionManager.getUserID(request), "Consulta a usuarios", SessionManager.getIp(request));


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

                /*
                 * @author Froylan Villalba G.
                 * Fecha: 09-07-2013
                 * Descripción: Se agrega el Titulo a la pantalla, de acuerdo a la tarea obtenida con el URI en el request.
                 */
                mv.addObject("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
              return mv;
            } else {
                throw new ControllerActionUnknown("Llamada a Reporte Generico de Documentos desconocida. No es soportada por esta versión");
            }
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }

    }
}
