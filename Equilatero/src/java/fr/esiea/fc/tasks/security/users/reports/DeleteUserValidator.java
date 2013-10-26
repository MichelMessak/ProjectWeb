package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.model.security.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DeleteUserValidator implements Validator
{
    
    public void validate(Object target, Errors errors)
    {

        User user = (User)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",
                "required.id", "ID de l'utilisateur est requis.");

    }

    
    public boolean supports(Class type)
    {
        return User.class.isAssignableFrom(type);
    }

}
