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
   

    public void validate(Object target, Errors errors)
    {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "L'ID de l'entreprise est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Le nom de l'entreprise est requis.");

    }


    public boolean supports(Class type)
    {
        return Enterprise.class.isAssignableFrom(type);
    }

}
