package fr.esiea.fc.tasks.admin.reports.enterprise;

import fr.esiea.fc.model.admin.Enterprise;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for a modification of a enterprise
 * @author Dispa Cécile
 */

public class ModifyEnterpriseValidator implements Validator
{
    /**
     * Realiza la validacion de los datos que recibe a traves del objeto comando
     * @param target    Objeto comando donde recibe los datos de la forma
     * @param errors    Objeto errores usado para reportar los errores y que se vean en la capa de presentacion
     */

    public void validate(Object target, Errors errors)
    {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "L'ID de l'entreprise est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Le nom de l'entreprise est requis.");

    }

    /**
     * Permite validar la clase del objeto comando que soporta la validacion
     * @param type  Clase del objeto comando
     * @return  Verdadero si la clase del objeto comando es la requerida
     */

    public boolean supports(Class type)
    {
        return Enterprise.class.isAssignableFrom(type);
    }

}
