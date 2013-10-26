package fr.esiea.fc.tasks.security.users.add;

import fr.esiea.fc.model.security.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validactor for adding an user
 * @author Michel Messak
 */

public class AddUserValidator implements Validator
{
    
    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "ID de l'utilisateur est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Le nom est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Le couriel est requis.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.email", "Le mot de passe est requis.");
        
        
          
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword",
        "required.confirmationPassword", "La comfirmation du mot de passe est requis.");

        // Verificacion de contraseña
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
