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
 * Controller for adding an enterprise
 * @author Dispa Cécile
 */
public class AddEnterpriseController extends SimpleFormController {

    public AddEnterpriseController() {
        setCommandClass(Enterprise.class);
        setCommandName("addEnterpriseForm");
    }

    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("true".equals(isSubmit));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception
    {
         request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
    return new Enterprise();
    }
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            Enterprise enterprise = (Enterprise) command;

            EnterpriseDAO enterpriseDAO = new EnterpriseDAO();

            String userId = SessionManager.getUserID(request);
            String userIp = SessionManager.getIp(request);

            if (!enterpriseDAO.add(enterprise, userId)) {
                Log.write("Erreur: L'entreprise [" + enterprise.getId() + "] n'a pas pu être ajoutée");

                errors.rejectValue("id", "enterprises.add.database", "L'entreprise n'a pas pu être ajoutée");
                ActivityDAO.insert(userId, "Echec lors de l'ajout de l'entreprise " + enterprise.getId(), userIp);
                return this.showForm(request, response, errors);
            }
            ActivityDAO.insert(userId, "L'entreprise " + enterprise.getId() + " a été ajoutée", userIp);
            return new ModelAndView(getSuccessView(), getCommandName(), enterprise);
        } catch (Exception ex) {
            errors.rejectValue("id", "enterprises.add.database", ex.getMessage());
            Log.write(ex);
            return this.showForm(request, response, errors);
        }
    }
}
