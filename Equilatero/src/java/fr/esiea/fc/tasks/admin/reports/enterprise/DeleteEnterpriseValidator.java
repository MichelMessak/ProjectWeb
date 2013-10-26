package fr.esiea.fc.tasks.admin.reports.enterprise;

import fr.esiea.fc.model.admin.Enterprise;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validato for deleting an enterprise
 * @author Michel Messak
 */

public class DeleteEnterpriseValidator implements Validator
{
    public void validate(Object target, Errors errors)
    {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "Id de l'entreprise requis.");
    }

   

    public boolean supports(Class type)
    {
        return Enterprise.class.isAssignableFrom(type);
    }

}
