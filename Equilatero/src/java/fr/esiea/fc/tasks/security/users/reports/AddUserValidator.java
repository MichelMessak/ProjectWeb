package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.model.security.User;
import java.util.Vector;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator fir adding a user
 * @author Michel Messak
 */

public class AddUserValidator implements Validator
{
   
 boolean statusValidacionContrasena;
    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "ID de 'utiisateur est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Le nom de 'utiisateur est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Le courriel de l'utilisateur est requis.");
   
        if(user!=null && user.getPassword()!=null && !"".equals(user.getPassword()))
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
