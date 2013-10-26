package fr.esiea.fc.model.admin;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Dispa Cécile
 */
public class Activity {
    protected Integer id;
    private String ip;
    private String userId;
    private Date date;
    private Time time;
    private String description;

    public Activity() {
    }

    public Activity(String userId, Date date, Time time, String description, String ip) {
        this.ip = ip;
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    
}
