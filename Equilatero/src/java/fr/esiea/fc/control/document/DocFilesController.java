package fr.esiea.fc.control.document;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.document.DocFile;
import fr.esiea.fc.model.document.DocFileDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.util.Log;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controlador para visualizar los archivos de un documento
 * @author Ángel García Alcántara
 * @since 1.0.0.0
 */
public class DocFilesController implements Controller {


    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            String userIp = SessionManager.getIp(request);
                        if (Report.isPreviousReport(request)){

                 RedirectView view= new RedirectView ("reporteDocumentos.do");
                    ModelAndView mv = new ModelAndView(view);
                    mv.addObject("isSubmitPreviousReport","Previous");

                    return mv;

            }

            /*if(!PeriodDAO.exists(perID))
             *
             *
             *
            throw new PeriodNotFoundException("Periodo "+perID+"no ha sido encontrado");*/
            List<DocFile> docFiles = DocFileDAO.getDocFiles( "*", request.getParameter("emp_id"),
                    request.getParameter("dty_id"), request.getParameter("dst_id"), request.getParameter("doc_id"));

            ModelAndView mv = new ModelAndView("docFiles");
            mv.addObject("docs", docFiles);

            /**
             * @author Froylan Villalba G.
             * Fecha: 09-07-2013 
             * Descripción: Se agrega el Titulo a la pantalla, de acuerdo a la tarea obtenida con el URI en el request.
             */
             request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
             

            String userId = SessionManager.getUserID(request);
            ActivityDAO.insert(userId, "Consulta de Documento " + request.getParameter("doc_id") + " de la empresa " + request.getParameter("emp_id"), userIp);

            return mv;
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            Log.write(ex);
            return mv;
        }
    }
}
