/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.model.security.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Valida los datos de la forma antes de eliminar un usuario
 * @author Segundo García Heras
 * @since Versión 4.0.0.0
 */
public class DeleteUserValidator implements Validator
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
