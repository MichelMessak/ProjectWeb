package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import fr.esiea.fc.util.Log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controlador para manejo de eliminaciones de usuarios. Usa <code>Spring Framework<code> para el control de errores y
 * y flujo de trabajo.
 * @author Segundo García Heras
 * @since Versión 4.0.0.0
 */
public class DeleteUserController extends SimpleFormController {

    /**
     * Constructor default. Configura la clase comando con la clase {@link User} y el nombre del comando
     * como la forma <code>eliminaUsuarioForm</code>
     */
    public DeleteUserController() {
        setCommandClass(User.class);
        setCommandName("deleteUserForm");
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
        return ("1".equals(isSubmit));
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
            if (!dao.delete(user.getId())) {

                Log.write("No se pudo eliminar el usuario " + user.getId() + "]");
                errors.rejectValue("id", "users.delete.database", "No se pudo eliminar el usuario");
                ActivityDAO.insert(SessionManager.getUserID(request), "Intento fallido al eliminar el usuario " + user.getId(), SessionManager.getIp(request));
                return this.showForm(request, response, errors);
            }
            ActivityDAO.insert(SessionManager.getUserID(request), "Usuario " + user.getId() + " eliminado", SessionManager.getIp(request));
            return new ModelAndView(getSuccessView(), getCommandName(), user);
        } catch (Exception ex) {
            Log.write(ex);
            errors.rejectValue("id", "users.delete.database", "El Usuario no se puede borar.");
            return this.showForm(request, response, errors);
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        User user = new User(request.getParameter("user_id"), request.getParameter("user_name"),
                request.getParameter("user_email"), request.getParameter("user_pwd"),
                request.getParameter("user_pwd"),null,null);

        /**
         * @author Froylan Villalba G.
         * Fecha: 09-07-2013
         * Descripción: Se agrega el Titulo a la pantalla, de acuerdo a la tarea obtenida con el URI en el request.
         */
        request.setAttribute("title", TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length()+1)));
        return user;
    }
}