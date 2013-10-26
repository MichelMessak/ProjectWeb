package fr.esiea.fc.model.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import fr.esiea.fc.model.PoolConnection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO to manage all the enterprises
 * @author Michel Messak
 */
public class EnterpriseDAO {

    public static final String TABLE_NAME = "common.enterprises";
    public static final String ID = "emp_id";

    public EnterpriseDAO() throws Exception {
    }

    public static boolean delete(String enterpriseId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            ps = (PreparedStatement) conn.prepareStatement("delete from common.enterprises where emp_id=?");
            ps.setString(1, enterpriseId);
            int ret = ps.executeUpdate();
            return (ret >= 1);
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

    public static boolean modify(Enterprise enterprise) throws Exception {
        boolean update = false;
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            String query = "update common.enterprises set ";
            String set = "";

            if (enterprise.getId() == null || enterprise.getId().equals("")) {
                throw new Exception("L'ID de l'entreprise n'existe pas.");
            } else {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " emp_id = '" + enterprise.getId() + "'";
                update = true;
            }

            if (enterprise.getName() == null || enterprise.getName().equals("")) {
                throw new Exception("L'entreprise n'existe pas");
            } else {
                if (!set.equals("")) {
                    set += ",";
                }
                set += " emp_name = '" + enterprise.getName() + "'";
                update = true;
            }

            if (!update) {
                throw new Exception("Il n'y a rien a actualiser");
            }
            query += set + " where emp_id = '" + enterprise.id + "'";
            st = conn.createStatement();
            int ret = st.executeUpdate(query);
            return (ret >= 1);
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean add(Enterprise enterprise, String userName) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            rs = conn.createStatement().executeQuery("select count(*) from common.enterprises"
                    + " where emp_id = '" + enterprise.getId() + "'");
            if (rs.next() && rs.getInt(1) != 0) {
                throw new Exception("L'entreprise " + enterprise.getId() + " existe déjà");
            }
            ps = (PreparedStatement) conn.prepareStatement("insert into common.enterprises (emp_id, emp_name,emp_user) values(?, ?, ?);");
            ps.setString(1, enterprise.getId());
            ps.setString(2, enterprise.getName());
            ps.setString(3, userName);

            int count = ps.executeUpdate();
            return (count > 0);
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

    public static List<Enterprise> userEnterprises(String userID, String urlTask) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            st = conn.createStatement();

            String query = "select distinct E.emp_id, E.emp_name from common.enterprises E "
                        + "inner join common.users as U on U.user_id = E.emp_user "
                        + "inner join common.tasks as T on T.tsk_url = '" + urlTask + "' "
                        + "where U.user_id = '" + userID + "'";
            

            rs = st.executeQuery(query);

            List<Enterprise> enterpriseList = new ArrayList<Enterprise>();
            while (rs.next()) {
                Enterprise enterprise = new Enterprise(rs.getString(1), rs.getString(2));
                
                enterpriseList.add(enterprise);
            }
            return enterpriseList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
