package fr.esiea.fc.model.security;

/**
 * @author Dispa Cécile
 */
public class User {

    protected String id;
    private String name;
    private String email;
    private String password, confirmationPassword, actualPassword;
    private String status;
    private Boolean resetPassword;

   
    public User() {
    }

    public User(String id, String name, String email, String password,
       String confirmationPassword, String status, Boolean reset_password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.status = status;
        this.resetPassword = reset_password;
    }

     public Boolean getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        this.resetPassword = resetPassword;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getPassword_actual() {
        return actualPassword;
    }

    public void setPassword_actual(String password_actual) {
        this.actualPassword = password_actual;
    }

    public Boolean isReset_password() {
        return resetPassword;
    }

  

    public String getResetPasswordCheckbox(){
                     
        if (resetPassword != null && resetPassword) {
            return "checked";
        }else
        {
            return "unchecked";
        }
    }

    public void setResetPasswordCheckbox(String resetPasswordCheckbox){
        if (resetPasswordCheckbox != null && "checked".equalsIgnoreCase(resetPasswordCheckbox)) {
            this.resetPassword = true;
        } else {
            this.resetPassword = false;
        }
    }

    public void setReset_password(Boolean reset_password) {
        this.resetPassword = reset_password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    
}
