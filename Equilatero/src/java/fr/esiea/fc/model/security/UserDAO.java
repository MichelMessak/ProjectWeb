package fr.esiea.fc.model.security;

import fr.esiea.fc.model.PoolConnection;
import fr.esiea.fc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO to manage all the Users
 * @author Dispa Cécile
 */
public class UserDAO {

    public static final String ID = "user_id";
    public static final String NAME = "user_name";
    public static final String EMAIL = "user_email";
    public static final String PASSWORD = "user_pwd";
    public static final String RESET_PASSWORD = "user_pwdreset";
    public static final String STATUS = "user_status";
    public static final String TYPE = "user_type";
    public static final String TABLE_NAME = "common.users";
    public static final String COLUMNS = ID + "," + NAME + "," + EMAIL + ","
            + PASSWORD + "," + STATUS + "," + RESET_PASSWORD;
    public static final String INSERT_QUERY = "insert into " + TABLE_NAME + "(" + COLUMNS + ") values(?,?,?,?,?,?)";
    public static final String DELETE_QUERY = "delete from " + TABLE_NAME + " where " + ID + " = ?";
    public static final String COUNT_QUERY = "select count(*) from " + TABLE_NAME;
    public static final String SELECT_QUERY = "select " + COLUMNS + " from " + TABLE_NAME;
    public static final String DISABLE_QUERY = "update " + TABLE_NAME + " set user_status = 'I' where " + ID + " = ?";
    public static final String MENU_TSKTYPE = "MENU";

    public UserDAO() throws Exception {
    }

    public static List<User> selectAll() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            ps = conn.prepareStatement(SELECT_QUERY+" ORDER BY "+NAME);
                    
            rs = ps.executeQuery();
            return rs2vec(rs);
        } catch (Exception e) {
            throw e;
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

    public static User getById(String user_id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            ps = conn.prepareStatement(SELECT_QUERY + " where " + ID + " = '" + user_id + "'");
            rs = ps.executeQuery();
            return rs2vec(rs).get(0);
        } catch (Exception e) {
            throw e;
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

    public static User getUserExist(String user_id) throws Exception {
        Connection conn = null;
        String password = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User UserExist = null;
        try {
            conn = PoolConnection.getPoolConnection();
            ps = conn.prepareStatement(SELECT_QUERY + " where " + ID + " = '" + user_id + "'");
            rs = ps.executeQuery();
            while (rs.next()) {
                UserExist = new User(user_id, rs.getString(1), rs.getString(3), password, password, rs.getString(4), rs.getBoolean(6)/*(rs.getBoolean(6) ? "true" : "false")*/);
            }
            return UserExist;
        } catch (Exception e) {
            throw e;
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

    public static boolean delete(String userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = PoolConnection.getPoolConnection();

            ps = (PreparedStatement) conn.prepareStatement("DELETE FROM period.activities WHERE user_id = '" +userId+ "'");
            ps.executeUpdate();

            ps = (PreparedStatement) conn.prepareStatement(DELETE_QUERY);
            ps.setString(1, userId);
            int ret = ps.executeUpdate();
            
            return (ret >= 1);
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

    public static boolean modify(User user) throws Exception {
        Connection conn = null;
        Statement st = null;
        boolean update = false;
        try {
            conn = PoolConnection.getPoolConnection();
            String query = "update " + TABLE_NAME + " set ";
            String set = "";

            if (user.getName() != null && !user.getName().equals("")) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + NAME + " = '" + user.getName() + "'";
                update = true;
            }
            if (user.getEmail() != null && !user.getEmail().equals("")) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + EMAIL + " = '" + user.getEmail() + "'";
                update = true;
            }
            if (user.getPassword() != null && !user.getPassword().equals("")) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + PASSWORD + " = '" + Util.GetSHA256(user.getPassword()) + "'";
                update = true;
            }
            if (user.getStatus() != null && !user.getStatus().equals("")) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + STATUS + " = '" + user.getStatus().substring(0, 1).toUpperCase() + "'";
                update = true;
            }
            if (user.isReset_password() != null) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + RESET_PASSWORD + " = '" + (user.isReset_password()?"true":"false") + "'";
                update = true;
            }
            if (!update) {
                throw new Exception("Rien n'est à actualiser");
            }
            query += set + " where " + ID + " = '" + user.id + "'";
            st = conn.createStatement();
            int ret = st.executeUpdate(query);
            return (ret >= 1);
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static boolean changePassword(User user) throws Exception {
        Connection conn = null;
        Statement st = null;
        boolean update = false;
        try {
            conn = PoolConnection.getPoolConnection();
            String query = "update " + TABLE_NAME + " set ";
            String set = "";
            if (user.getPassword() != null && !user.getPassword().equals("")) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + PASSWORD + " = '" + Util.GetSHA256(user.getPassword()) + "'";
                update = true;
            }
            if (user.isReset_password() != null) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + RESET_PASSWORD + " = '" + user.isReset_password() + "'";
                update = true;
            }
            if (!update) {
                throw new Exception("Rien n'est à actualiser");
            }
            query += set + " where " + ID + " = '" + user.id + "'";
            st = conn.createStatement();
            int ret = st.executeUpdate(query);
            return (ret >= 1);
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static boolean changePasswordWoSession(User user, String perID) throws Exception {
        Connection conn = null;
        Statement st = null;
        boolean update = false;
        try {
            conn = PoolConnection.getPoolConnection();
            String query = "update " + TABLE_NAME + " set ";
            String set = "";
            if (user.getPassword() != null && !user.getPassword().equals("")) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + PASSWORD + " = '" + Util.GetSHA256(user.getPassword()) + "'";
                update = true;
            }
            if (user.isReset_password() != null) {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " " + RESET_PASSWORD + " = '" + user.isReset_password() + "'";
                update = true;
            }
            if (!update) {
                throw new Exception("Rien n'est à actualiser");
            }
            query += set + " where " + ID + " = '" + user.id + "'";
            st = conn.createStatement();
            int ret = st.executeUpdate(query);
            return (ret >= 1);
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static boolean add(User user) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            rs = conn.createStatement().executeQuery("select count(" + ID + ") from " + TABLE_NAME + ""
                    + " where " + ID + " = '" + user.getId() + "'");
            if (rs.next() && rs.getInt(1) > 0) {
                throw new Exception("L'utilisateur existe déjà");
            }
            ps = (PreparedStatement) conn.prepareStatement(
                    "insert into " + TABLE_NAME + " (" + ID + ", "
                    + NAME + ", " + EMAIL + ", " + PASSWORD + ", " + STATUS + ", " + TYPE
                    + ") values(?, ?, ?, ?, ?, ?);");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, Util.GetSHA256(user.getPassword()));
            ps.setString(5, "A");
            ps.setString(6, "USER");
            int count = ps.executeUpdate();


            if (count > 0) {
                return true;
            } else {
                return false;
            }
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

    public static User Authenticate(String user, String plainpassword) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        try {

            User userDTO = null;
            conn = PoolConnection.getPoolConnection();

            st = conn.createStatement();
            rs = st.executeQuery("select " + NAME + ", " + PASSWORD + ", user_email, user_status, user_pwdreset" + " from " + TABLE_NAME + " where " + ID + " = '" + user + "'");
            while (rs.next()) {
                String dbpassword = rs.getString(2);
                String password = Util.GetSHA256(plainpassword);

                if (dbpassword != null && password != null && dbpassword.equals(password)) {
                    userDTO = new User(user, rs.getString(1), rs.getString(3), password, password, rs.getString(4), rs.getBoolean(5));    
                }
            }
            return userDTO;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    

    public static List<Task> GetAllTaskFromUser(String user) throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<Task> tasks = null;
        try {
            conn = PoolConnection.getPoolConnection();

            st = conn.createStatement();
            String query = "select distinct t.tsk_id, tsk_name, tsk_image, tsk_url, tsk_type "
                        + "from common.tasks as t "
                        + "inner join common.permissions as p on p.tsk_id=t.tsk_id "
                        + "inner join common.users as r on r.user_type=p.rol_id "
                        + "where r." + ID + " = '" + user + "' "
                        + "order by t.tsk_id";

            rs = st.executeQuery(query);
            tasks = new ArrayList<Task>();
            while (rs.next()) {
               Task task = new Task(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                tasks.add(task);
            }
            return tasks;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static List getAllStatus() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            rs = conn.createStatement().executeQuery(
                    "select distinct " + STATUS + " from " + TABLE_NAME
                    + " order by " + STATUS);
            ArrayList result = new ArrayList(rs.getFetchSize());
            while (rs.next()) {
                result.add(rs.getString(STATUS));
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    private static List<User> rs2vec(ResultSet rs) throws Exception {
        List<User> vec = new ArrayList<User>();
        try {
            while (rs.next()) {
                User user = new User(rs.getString("" + ID + ""),
                        rs.getString("" + NAME + ""),
                        rs.getString("" + EMAIL + ""),
                        rs.getString("" + PASSWORD + ""),
                        rs.getString("" + PASSWORD + ""),
                        rs.getString(STATUS).trim(),
                        rs.getBoolean(RESET_PASSWORD));
                vec.add(user);
            }
            return vec;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static boolean isUserExist (String userId) throws Exception{
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query ="SELECT COUNT(*) FROM common.users WHERE user_id='"+userId+"'";
        
        try {
            conn = PoolConnection.getPoolConnection();
            
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            
            if (rs.getInt(1)==1)
                return true;
            
            return false;
            
        } catch (SQLException ex) {
            return false;
         
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        
        
    }

    public static boolean Disable(String userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = PoolConnection.getPoolConnection();
            ps = (PreparedStatement) conn.prepareStatement(DISABLE_QUERY);
            ps.setString(1, userId);
            int ret = ps.executeUpdate();
            ps.close();
            conn.close();
            return (ret >= 1);
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

     public static String getPwdResetForUser(String userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = PoolConnection.getPoolConnection();

            String query ="UPDATE "+TABLE_NAME+" SET "+RESET_PASSWORD+"='true' WHERE "+ID+"= ?";
  
            ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, userId);

            ps.executeUpdate();

            return query;
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

    public static String getEmailAddressForUser(String userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = PoolConnection.getPoolConnection();

            String query = "select user_email from " + TABLE_NAME + " where " + ID + " = ?";
            ps = (PreparedStatement) conn.prepareStatement(query);
            ps.setString(1, userId);

            rs = ps.executeQuery();

            String email = null;
            if (rs != null) {
                while (rs.next()) {
                    email = rs.getString(1);
                }
            }
            return email;
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

    public static String getPwdForUser(String userId, String newPwd) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = PoolConnection.getPoolConnection();

            String query = "UPDATE common.users SET user_pwd= ? WHERE user_id= ? ";

            String SHAPwd = Util.GetSHA256(newPwd);

            ps = (PreparedStatement) conn.prepareStatement(query);

            ps.setString(1, SHAPwd);
            ps.setString(2, userId);
            ps.executeUpdate();

            String pwd = null;
            if (rs != null) {
                while (rs.next()) {
                    pwd = rs.getString(1);
                }
            }
            return pwd;
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
}
