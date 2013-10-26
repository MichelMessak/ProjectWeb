package fr.esiea.fc.model.document;

import fr.esiea.fc.model.PoolConnection;
import com.itc.repository.fc4Repository;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Archive of a document
 * @author Dispas Cécile
 */
public class DocFileDAO {

    public static List<DocFile> getDocFiles( String returnFields, String empId, String docTypeId,
            String docSubtypeId, String docId) throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = PoolConnection.getPoolConnection();
            st = conn.createStatement();
            
            st.execute("select " + returnFields
                    + " from period.document_files where emp_id = '" + empId + "'"
                    + " and dty_id = '" + docTypeId + "'"
                    + " and dst_id = '" + docSubtypeId + "'"
                    + " and doc_id = '" + docId + "'");
            rs = st.getResultSet();
            List<DocFile> vec = rs2vec(rs);
            return vec;
        } catch (Exception e) {
            return null;
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

    private static List<DocFile> rs2vec(ResultSet rs) throws Exception {
        List<DocFile> vec = new ArrayList<DocFile>();
        try {
            while (rs.next()) {
                DocFile docFiles = new DocFile();
                docFiles.setEnterpriseID(rs.getString("emp_id"));
                docFiles.setDocTypeId(rs.getString("dty_id"));
                docFiles.setDocSubtypeId(rs.getString("dst_id"));
                docFiles.setDocId(rs.getString("doc_id"));
                docFiles.setFileId(rs.getInt("file_id"));
                docFiles.setPath(fc4Repository.getAbsolutePath(rs.getString("path"))); 
                docFiles.setFilename(rs.getString("filename"));

                vec.add(docFiles);
            }
            rs.close();
            return vec;
        } catch (Exception e) {
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }
}
