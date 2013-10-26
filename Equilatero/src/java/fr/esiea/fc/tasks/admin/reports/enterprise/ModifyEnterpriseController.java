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
 * Controller for the modification of an enterprise
 * @author Dispa Cécile
 */

public class ModifyEnterpriseController extends SimpleFormController
{
   
    public ModifyEnterpriseController()
    {
        setCommandClass(Enterprise.class);
        setCommandName("modifyEnterpriseForm");
    }

    @Override
    public boolean isFormSubmission(HttpServletRequest request)
    {
        Object isSubmit=request.getParameter("isSubmit");
        if(isSubmit==null)
            return super.isFormSubmission(request);
        return ("true".equals(isSubmit));
    }

   
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
    {
        try
        {
            if(!SessionManager.hasSession(request))
                return SessionManager.getLoginView(request);

            String userIp = SessionManager.getIp(request);
            String userId = SessionManager.getUserID(request);
            Enterprise enterprise=(Enterprise)command;

            if(!EnterpriseDAO.modify(enterprise))
            {
                Log.write("L'entreprise ["+enterprise.getId()+"] n'a pas pu être modifé");
                errors.rejectValue("id", "enterprises.update.database", "L'entreprise n' pas pu être modifié");
                ActivityDAO.insert(userId, "Echec de la modification de l'entreprise " + enterprise.getId(), userIp);
                return this.showForm(request, response, errors);
            }

            ActivityDAO.insert(userId, "Entreprise " + enterprise.getId() + " Modifiée", userIp);
            return new ModelAndView(getSuccessView(), getCommandName(), enterprise);
        }
        catch(Exception ex)
        {
            errors.rejectValue("id", "enterprises.update.database", ex.getMessage());
            Log.write(ex);
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception
    {

        Enterprise enterprise = new Enterprise(request.getParameter("emp_id"), request.getParameter("emp_name"));
         request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
        return enterprise;
    }

}
