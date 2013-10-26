package fr.esiea.fc.tasks.security.users.reports;

import fr.esiea.fc.control.Report;
import fr.esiea.fc.control.SessionManager;
import fr.esiea.fc.model.PoolConnection;
import fr.esiea.fc.model.admin.ActivityDAO;
import fr.esiea.fc.util.error.DBConnectionNotFound;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Genera reporte en PDF
 * @author Michel Messak
 */
public class ReporteUserPDFController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

        HttpSession se = request.getSession(false);
        Report report = (Report) se.getAttribute("report");

        String query  = report.getQueryB();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        String userId = (String) SessionManager.getUserID(request);
        String userIp = SessionManager.getIp(request);
        
        Document document = new Document();


			PdfWriter.getInstance(document, response.getOutputStream());

                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename="+report.getTask().replace(" ", "_") +".pdf");
			document.open();

                        PdfPTable header = new PdfPTable(report.getColumnCount());
                        PdfPTable table = new PdfPTable(report.getColumnCount());
                             table.setComplete(false);
                    for (int i = 0;i<report.getColumnCount();i++){
                        header.addCell(report.getColumnNames()[i]);
                    }
                        document.add(header);

                      try{
                            conn = PoolConnection.getPoolConnection();
                            if (conn == null) {
                                throw new DBConnectionNotFound("No hay conexiones disponibles");
                            }

                                statement = conn.prepareStatement(query);
                            rs = statement.executeQuery();
                        while(rs.next()){

                            for (int i = 1;i<=report.getColumnCount();i++){
                                if (rs.getObject(i) == null)
                                    table.addCell("");
                                else
                                    table.addCell(rs.getObject(i).toString());
                            }
                        }

                      }
                              catch (Exception ex)
                                {
                                    throw ex;
                                }
                                finally
                                {
                                    if (rs != null) {
                                        rs.close();
                                    }
                                    if (statement != null) {
                                        statement.close();
                                    }
                                    if (conn != null) {
                                        conn.close();
                                    }
                                }
                      
                     table.setComplete(true);
                     document.add(table);
                     document.close();

        ActivityDAO.insert(userId, report.getTask()+" con PDF", userIp);
        }
        catch(Exception e) {
        }
                     return null;
    }
}
