package fr.esiea.fc.model.security;

import fr.esiea.fc.model.PoolConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO to manage all the task
 * @author Michel Messak
 */

public class TaskDAO {

    public static final String ID = "tsk_id";
    public static final String NAME = "tsk_name";
    public static final String IMAGE = "tsk_image";
    public static final String URL = "tsk_url";
    public static final String TABLE_NAME = "common.tasks";
    public static final String COLUMNS =  ID + "," + NAME + "," + IMAGE + "," + URL;

    public TaskDAO(){}

    public static String selectNameTask(String url) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String taskName=null;
            conn = PoolConnection.getPoolConnection();
            ps = conn.prepareStatement("select " + COLUMNS + " from " + TABLE_NAME + " where " + URL + " = '" + url + "'");
            rs = ps.executeQuery();

            while(rs.next())
            {
                 taskName = rs.getString(NAME);
            }

            return taskName;

        } catch (Exception e) {
            throw e;
        }
        finally
        {
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            if(conn!=null) conn.close();
        }
    }
}
