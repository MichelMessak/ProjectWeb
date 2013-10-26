package fr.esiea.fc.model.security;

import fr.esiea.fc.model.PoolConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Michel Messak
 */
public class NotificationDAO {

    public static final String TABLE_USERS = "common.users";
    public static final String USER = "user_id";
    public static final String EMP_ID = "emp_id";
    public static final String EMAIL = "user_email";
    public static final String ERRORS = "errors";
    public static final String CORRECT = "corrects";
    public static final String STATUS = "P";
    public static final String AS_User = "user";

    public static List <Notification> getUsersAdministrators() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();

            String query = "SELECT " + AS_User + "." + USER + " ,"
                    + EMP_ID + " , " + AS_User + "." + EMAIL +
                    " FROM " + TABLE_USERS + " AS " + AS_User +
                   " where actores.user_id= usuarios.user_id "
                    + " AND rol_id = 'ADMINISTRADOR'";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            return rs2vec(rs);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static List <Notification> rs2vec(ResultSet rs) throws Exception {
        Connection conn = null;
        PreparedStatement psdocErrors = null;
        PreparedStatement psdocCorrects = null;
        ResultSet rsErrors = null;
        ResultSet rsCorrects = null;
        try {

            List<Notification> notifications = new Vector<Notification>();
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setUser(rs.getString(USER));
                notification.setEnterpriseID(rs.getString(EMP_ID));
                notification.setEmail(rs.getString(EMAIL));
                conn = PoolConnection.getPoolConnection();
                
                String docErrors = "select count(*) AS errores from period.documents"
                        + " where emp_id = '" + notification.getEnterpriseID() + "'"
                        + " AND doc_status = 'E'";
                
                String docCorrects = "select count(*) AS correctos from period.documents"
                        + " where emp_id = '" + notification.getEnterpriseID() + "'"
                        + " AND doc_status = 'O'";
                
                psdocErrors = conn.prepareStatement(docErrors);
                rsErrors = psdocErrors.executeQuery();
                rsErrors.next();
                notification.setErrors(rsErrors.getString(ERRORS));
                rsErrors.close();
                psdocErrors.close();

                psdocCorrects = conn.prepareStatement(docCorrects);
                rsCorrects = psdocCorrects.executeQuery();
                rsCorrects.next();
                notification.setCorrect(rsCorrects.getString(CORRECT));
                rsCorrects.close();
                psdocCorrects.close();
                conn.close();
                
                notifications.add(notification);

            }
            return notifications;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rsCorrects != null) {
                rsCorrects.close();
            }
            if (rsErrors != null) {
                rsErrors.close();
            }
            if (psdocCorrects != null) {
                psdocCorrects.close();
            }
            if (psdocErrors != null) {
                psdocErrors.close();
            }
            if (psdocErrors != null) {
                psdocErrors.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }
}
