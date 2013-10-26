package fr.esiea.fc.model.admin;

import fr.esiea.fc.model.PoolConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;

/**
 *
 * @author Dispas Cécile
 */
public class ActivityDAO {

    public static final String ID = "act_id";
    public static final String IP = "act_ip_address";
    public static final String USER = "user_id";
    public static final String DATE = "act_date";
    public static final String TIME = "act_time";
    public static final String DESCRIPTION = "act_description";
    public static final String dot_TABLE_NAME = ".activities";
    public static final String COLUMNS_WOID = USER + "," + DATE + "," + TIME + "," + DESCRIPTION + "," + IP;
    public static final String COLUMNS = ID + "," + COLUMNS_WOID;
    public static final Integer ID_COL = 1;
    public static final Integer USER_COL = 2;
    public static final Integer DATE_COL = 3;
    public static final Integer TIME_COL = 4;
    public static final Integer DESCRIPTION_COL = 5;
    public static final Integer IP_COL = 6;

    public static boolean insert( Activity activity) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = PoolConnection.getPoolConnection();
            
            ps = conn.prepareStatement("insert into period" + dot_TABLE_NAME
                    + "(" + COLUMNS_WOID + ") values(?,?,?,?,?)");
            ps.setString(1, activity.getUserId());
            ps.setDate(2, activity.getDate());
            ps.setTime(3, activity.getTime());
            ps.setString(4, activity.getDescription());
            ps.setString(5, activity.getIp());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static boolean insert( String user, String description, String ip) throws Exception {
        return (insert( new Activity(user, getToday(), getNow(), description, ip)));
    }

    public static Date getToday() {
        return new Date((new java.util.Date()).getTime());
    }

    public static Time getNow() {
        return new Time((new java.util.Date()).getTime());
    }

    public static JSONArray rs2JSONArray(ResultSet rs) throws Exception {
        JSONArray jsonArr = new JSONArray();
        try {
            while (rs.next()) {
                JSONArray row = new JSONArray();
                row.add(rs.getInt(ID));
                row.add(rs.getString(USER));
                row.add(rs.getDate(DATE));
                row.add(rs.getTime(TIME));
                row.add(rs.getString(DESCRIPTION));
                row.add(rs.getString(IP));
                jsonArr.add(row);
            }
            return jsonArr;
        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Activity> rs2vec(ResultSet rs) throws Exception {
        try {
            List<Activity> activities = new ArrayList<Activity>();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setIp(rs.getString(IP));
                activity.setUserId(rs.getString(USER));
                activity.setDate(rs.getDate(DATE));
                activity.setTime(rs.getTime(TIME));
                activity.setDescription(rs.getString(DESCRIPTION));
                activities.add(activity);
            }
            return activities;
        } catch (Exception e) {
            throw e;
        }
    }
}
