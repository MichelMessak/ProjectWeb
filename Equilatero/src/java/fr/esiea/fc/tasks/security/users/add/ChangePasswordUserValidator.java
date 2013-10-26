package fr.esiea.fc.tasks.security.users.add;

import fr.esiea.fc.model.security.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for changing his own password
 * @author Dispa Cécile
 */
public class ChangePasswordUserValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Contraseña requerida");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword",
                "required.confirmationPassword", "Comfirmation du mot de passe requise");

        if(user!=null && user.getPassword()!=null)
        {
            if(!(user.getPassword().equals(user.getConfirmationPassword())))
                    errors.rejectValue("password", "notmatch.password", "Les mots de passes ne concordent pas");
        }

    }

    public boolean supports(Class type)
    {
        return User.class.isAssignableFrom(type);
    }
}
