package fr.esiea.fc.model.admin;

/**
 *
 * @author Michel Messak
 */
public class Enterprise {
    protected String id;
    private String name;

    public Enterprise() {
    }

    public Enterprise(String id, String name) {
        this.id = id;
        this.name = name;
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

    
}
