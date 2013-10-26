package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.model.security.User;
import java.util.Vector;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validador for modify an user
 * @author Dispa Cécile
 */

public class ModifyUserValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "ID de l'utilisateur est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Le nom de l'utilisateur est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Le courriel de l'utilisateur est requis.");

        if(user!=null && user.getPassword()!=null)
        {
            if(!(user.getPassword().equals(user.getConfirmationPassword())))
                errors.rejectValue("password", "notmatch.password", "Les mots de passe ne concordent pas");
        }

    }

    public boolean supports(Class type)
    {
        return User.class.isAssignableFrom(type);
    }

}
