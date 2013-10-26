package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.model.security.User;
import java.util.Vector;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validador de la forma de modificación de datos de un usuario
 * @author Segundo García Heras
 * @since Versión 4.0.0.0
 */

public class ModifyUserValidator implements Validator
{
    /**
     * Realiza la validacion de los datos que recibe a traves del objeto comando
     * @param target    Objeto comando donde recibe los datos de la forma
     * @param errors    Objeto errores usado para reportar los errores y que se vean en la capa de presentacion
     */
    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "ID Usuario es requerido.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Nombre es requerido.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Dirección de correo electrónica es requerida.");

        /*
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.email", "La contraseña es requerida.");
*/
       
        /* validacion de contraseña */
        boolean statusValidacionContrasena = false;
        Vector<String> ErroresContrasena = new Vector<String>();



        /*if(!"".equals(user.getPassword()))
        {
            Sox validacionContrasena = new Sox();
            statusValidacionContrasena = validacionContrasena.isValidPassword(user.getId(), user.getPassword(), ErroresContrasena);
        }
        



        if (statusValidacionContrasena == false)
        {
           for(int i=0;i<ErroresContrasena.size();i++)
            errors.rejectValue("password", "invalid.password", ErroresContrasena.get(i));
        }
*/
       /*
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword",
        "required.confirmationPassword", "Confirmación de contraseña es requerida.");
        */
        // Verificacion de contraseña
        if(user!=null && user.getPassword()!=null)
        {
            if(!(user.getPassword().equals(user.getConfirmationPassword())))
                errors.rejectValue("password", "notmatch.password", "Contraseñas no concuerdan");
        }

    }

    /**
     * Permite validar la clase del objeto comando que soporta la validacion
     * @param type  Clase del objeto comando
     * @return  Verdadero si la clase del objeto comando es la requerida
     */

    public boolean supports(Class type)
    {
        return User.class.isAssignableFrom(type);
    }

}
