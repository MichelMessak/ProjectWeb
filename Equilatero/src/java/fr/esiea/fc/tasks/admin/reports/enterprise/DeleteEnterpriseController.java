package fr.esiea.fc.tasks.admin.reports.enterprise;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.admin.Enterprise;
import fr.esiea.fc.model.admin.EnterpriseDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller dor deleting an enterprise
 * @author Michel Messak
 */
public class DeleteEnterpriseController extends SimpleFormController {

    public DeleteEnterpriseController() {
        setCommandClass(Enterprise.class);
        setCommandName("deleteEnterpriseForm");
    }
    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("1".equals(isSubmit));
    }
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            String userId = SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);

            Enterprise enterprise = (Enterprise) command;

            if (!EnterpriseDAO.delete(enterprise.getId())) {
                Log.write("L'entrreprise [" + enterprise.getId() + "] n'a pas pu être supprimé");

                errors.rejectValue("id", "enterprises.delete.database", "L'entreprise n'a pas pu être éliminé");
                ActivityDAO.insert(userId, "Echec de la suppression de l'entreprise " + enterprise.getId(), userIp);
                return this.showForm(request, response, errors);
            }

            ActivityDAO.insert(userId, "Entreprise " + enterprise.getId() + " supprimée", userIp);
            return new ModelAndView(getSuccessView(), getCommandName(), enterprise);
        } catch (Exception ex) {
            errors.rejectValue("id", "enterprises.delete.database", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        Enterprise enterprise = new Enterprise(request.getParameter("emp_id"), request.getParameter("emp_name"));
         request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

        return enterprise;
    }
}
