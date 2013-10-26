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

public class AddUserValidator implements Validator
{
    /**
     * Realiza la validacion de los datos que recibe a traves del objeto comando
     * @param target    Objeto comando donde recibe los datos de la forma
     * @param errors    Objeto errores usado para reportar los errores y que se vean en la capa de presentacion
     */
 boolean statusValidacionContrasena;
    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "ID Usuario es requerido.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Nombre es requerido.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Dirección de correo electrónica es requerida.");

           
        
                /* validacion de contraseña */
        Vector<String> Errores = new Vector<String>();
       if(user.getPassword().equals("")){
            statusValidacionContrasena = true;
       }
       else
       /* se recorre el vector para colocar toda la lista de errores */
        //|| 
       for (int i = 0;i < Errores.size();i++)
       {
        if (statusValidacionContrasena == false)
        {
            errors.rejectValue("password", "notmatch.password", Errores.elementAt(i));
        }
        else
        {
            // Verificacion de contraseña
            if(user!=null && user.getPassword()!=null)
            {
                if(!(user.getPassword().equals(user.getConfirmationPassword())))
                    errors.rejectValue("password", "notmatch.password", "Contraseñas no concuerdan");
            }

        }
       }
        /**
         * Permite dejar la contraseña en blanco. Si es así, no se modifica la contraseña en la base de datos
         * Cuando se especifica una nueva contraseña, se valida que esta sea confirmada.
         */
        if(user!=null && user.getPassword()!=null && !"".equals(user.getPassword()))
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
