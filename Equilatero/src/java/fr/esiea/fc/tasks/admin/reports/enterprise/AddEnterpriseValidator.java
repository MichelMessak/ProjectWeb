package fr.esiea.fc.tasks.admin.reports.enterprise;

import fr.esiea.fc.model.admin.Enterprise;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator of addinf an enterprise
 * @author Dispa Cécile
 */

public class AddEnterpriseValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {
        Enterprise enterprise = (Enterprise)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "L'ID de l'entreprise est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Le nom de l'entreprise est requis.");

        Pattern RFC = Pattern.compile("^[a-zA-Z]{3,4}(\\d{6})((\\D|\\d){3})?$");
        Matcher validar = RFC.matcher(enterprise.getId());
        boolean esValido = validar.matches();
        if (!esValido)
        {
            errors.rejectValue("id", "invalid.id", "Le SIRET de l'entreprise est érroné");
        }
    }

    public boolean supports(Class type)
    {
        return Enterprise.class.isAssignableFrom(type);
    }
}
