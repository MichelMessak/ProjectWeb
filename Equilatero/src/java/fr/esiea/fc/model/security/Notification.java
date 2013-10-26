package fr.esiea.fc.model.security;

/**
 * @author Michel Messak
 */
public class Notification {
    protected String user;
    protected String enterpriseID;
    protected String task;
    protected String email;
    protected String errors;
    protected String correct;

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


}
