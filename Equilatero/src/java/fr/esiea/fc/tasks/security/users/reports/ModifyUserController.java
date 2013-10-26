package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controlador que maneja la forma de Modificación de Usuarios
 * @author Segundo García Heras
 * @since Versión 4.0.0.0
 */
public class ModifyUserController extends SimpleFormController {

    /**
     * Constructor default. Configura la clase comando con la clase {@link User} y el nombre del comando
     * como la forma <code>modificaUsuarioForm</code>
     */
    public ModifyUserController() {
        setCommandClass(User.class);
        setCommandName("modifyUserForm");
    }

    /**
     * Sobre-escribe la manera de saber como la forma fue cargada, para saber si hay que mostrar datos, validar o ejecutar acciones
     * Las formas de <code>File Control 4</code> usan el parámetro <code>isSubmit</code> en true para indicar que se debe validar la forma
     * y no usan la manera default (por metodo GET o POST) de saber si hay que mostrar la forma o validarla
     * @param request   Request HTTP para poder obtener sus parametros
     * @return  Verdadero si el request debe considerarse como enviado y debe mandarse a validar.<br/>
     * Falso si debe mostrarse la forma como captura inicial
     */
    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("true".equals(isSubmit));
    }

    /**
     * Procesa la accion a realizar despues de haber pasado la validación.
     * Añade el usuario a la base de datos. Si hay alguna excepción manda a rechazar los datos
     * @param request   Request HTTP enviado
     * @param response  Response HTTP a recibir
     * @param command   Comando enviado por <code>Spring</code> de acuerdo al configurado en el constructor
     * @param errors    Errores que seran enviados a <code>Spring</code> para mostrarse en la forma
     * @return  <code>ModelAndView</code> que debera ser mostrada en la UI
     */
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {
            if (!SessionManager.hasSession(request)) {
                return SessionManager.getLoginView(request);
            }

            User user = (User) command;

            UserDAO dao = new UserDAO();
            if (!dao.modify(user)) {
                Log.write("No se pudo modificar el usuario " + user.getId());

                errors.rejectValue("id", "users.update.database", "No se pudo modificar usuario");
                ActivityDAO.insert(SessionManager.getUserID(request), "Intento fallido al modificar el usuario " + user.getId(), SessionManager.getIp(request));
                return this.showForm(request, response, errors);
            }            
          

            ActivityDAO.insert(SessionManager.getUserID(request), "Usuario " + user.getId() + " modificado", SessionManager.getIp(request));
            return new ModelAndView(getSuccessView(), getCommandName(), user);
        } catch (Exception ex) {
            Log.write(ex);
            errors.rejectValue("id", "users.update.database", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        String resetPassword=request.getParameter("reset_password");
        if(resetPassword==null) resetPassword="unchecked";

        User user = new User(request.getParameter("user_id"), request.getParameter("user_name"),
                request.getParameter("user_email"), request.getParameter("user_pwd"),
                null,request.getParameter("user_status"),(resetPassword.equals("checked") || resetPassword.equals("true") || resetPassword.equals("on"))?true:false);

        /**
         * @author Froylan Villalba G.
         * Fecha: 09-07-2013
         * Descripción: Se agrega el Titulo a la pantalla, de acuerdo a la tarea obtenida con el URI en el request.
         */
         request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));

//        User user = UserDAO.getById(request.getParameter("user_id"));
        return user;
    }

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {

		Map referenceData = new HashMap();

		List<String> statuses = UserDAO.getAllStatus();
		referenceData.put("statuses", statuses);
//                referenceData.put("status",request.getParameter("user_status"));
//                referenceData.put("reset_password",request.getParameter("reset_password"));

		return referenceData;
	}
}
