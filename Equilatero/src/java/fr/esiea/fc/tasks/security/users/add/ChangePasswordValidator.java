package fr.esiea.fc.tasks.security.users.add;

import fr.esiea.fc.model.security.User;
import fr.esiea.fc.model.security.UserDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for changing a password
 * @author Dispa Cécile
 */


public class ChangePasswordValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {        
      
        try {
            User user = (User)target;

            User verUser = UserDAO.Authenticate(user.getId(), user.getPassword_actual());
            if(verUser != null )
            {
                
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                        "required.password", "Contraseña requerida");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword",
                        "required.confirmationPassword", "Comfirmation du mot de passe requise");


                if(user!=null && user.getPassword()!=null)
                {
                    if(!(user.getPassword().equals(user.getConfirmationPassword())))
                            errors.rejectValue("password", "notmatch.password", "Les mots de passes ne condordent pas");
                }
            }
            else
            {
                errors.rejectValue("password_actual", "notmatch.password.actual", "Le mot de passe actuel est erroné");
            }
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    public boolean supports(Class type)
    {
        return User.class.isAssignableFrom(type);
    }
}
