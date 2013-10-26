package fr.esiea.fc.model.security;

import java.io.Serializable;

/**
 * @author Dispa Cécile
 */

public class Task implements Serializable
{
    String ID;
    String name;
    String image;
    String URL;
    String type;

    public Task(String ID, String name, String image, String URL, String type)
    {
        this.ID=ID;
        this.name=name;
        this.image=image;
        this.URL=URL;
        this.type=type;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID=ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image=image;
    }

    public String getURL()
    {
        return URL;
    }

    public void setURL(String URL)
    {
        this.URL=URL;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type=type;
    }

}
