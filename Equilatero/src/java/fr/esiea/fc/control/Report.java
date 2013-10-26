package fr.esiea.fc.control;

import fr.esiea.fc.model.PoolConnection;
import fr.esiea.fc.model.security.TaskDAO;
import fr.esiea.fc.util.Util;
import fr.esiea.fc.util.error.DBConnectionNotFound;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;

/**
 *
 * Manager reporting and paging
 * Configure querys to order, to filter by column, to search by all the column etc...
 * @author Michel Messak
 */
public class Report {

    private String[] columnNames;
    private String[] columnWidths;
    private String[] columnAligns;
    private String[] columnExtras;
    private Boolean[] columnVisibles;
    private Boolean[] columnSortables;
    private Boolean[] columnSearchables;
    private String query, queryTotal, queryB, URL;
    private String task;
    private JSONArray data;
    private int rowCount, rowFilterCount = 0;
    private int columnCount, currentPage, pageCount;
    private ReportColumnSorter sorter;
    private ReportColumnFilter filter;
    private ReportWhere where;
    private final int ROWS_PER_PAGE = 20;
    private int OFFSET = 0;

    public Report(String[] columnNames, String query, String URL) throws Exception {
        try {
            this.columnNames = columnNames;
            this.query = query;
            this.URL = URL;
            this.rowCount = 0;
            this.columnCount = columnNames.length;
            this.queryTotal = Util.GetQueryForTotal(query);
            this.pageCount = 0;
            String[] colNames = Util.GetColumnNamesFromQuery(query);
            this.sorter = new ReportColumnSorter(colNames);
            this.filter = new ReportColumnFilter(colNames);
            this.where = new ReportWhere();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void ExecuteQuery(HttpServletRequest request) throws Exception {
        try {
            ExecuteQueryForTotals(request);
           
        } catch (Exception ex) {
            throw ex;
        }
    }

  
    public int getColumnCount() {
        if (this.columnNames != null) {
            return this.columnNames.length;
        } else {
            return 0;
        }
    }

    public int getCurrentPage() {
        return this.currentPage;
    }
    public int getRowCount() {
        return this.rowCount;
    }

    public int getRowFilterCount() {
        return this.rowFilterCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    
    public String[] getColumnNames() {
        return this.columnNames;
    }

    
    public String getURL() {
        return this.URL;
    }

    public JSONArray getData() {
        return this.data;
    }

    public Boolean ExecuteQueryForTotals(HttpServletRequest request) throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        this.task = TaskDAO.selectNameTask(request.getRequestURI().substring(request.getContextPath().length() + 1));

        try {
            conn = PoolConnection.getPoolConnection();
            if (conn == null) {
                throw new DBConnectionNotFound("Aucune connexion disponible");
            }
            this.rowCount = 0;

            statement = conn.prepareStatement(this.query);

            rs = statement.executeQuery();

            while (rs.next()) {
                this.rowCount = rs.getRow();
            }

            if (this.rowCount == 0) {
                this.pageCount = 0;
            } else {
                this.pageCount = this.rowCount / this.ROWS_PER_PAGE;
                if (this.pageCount * this.ROWS_PER_PAGE < this.rowCount) {
                    this.pageCount++;
                }
            }

            this.currentPage = -1;

            return true;
        } catch (Exception ex) {
            throw ex;
        } finally {
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
    }

   
    public void getPreviousPage(Connection conn) throws Exception {
        try {
            if (this.currentPage - 1 < 0) {
                return;
            }
            this.currentPage--;
            getCurrentPage(conn);

        } catch (Exception e) {
            throw e;
        } finally {
           
        }
    }

    public void getRecords(int offset, int limit) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        ResultSet rsTotal = null;
        PreparedStatement statementTotal = null;
        try {
            {
                conn = PoolConnection.getPoolConnection();
                if (conn == null) {
                    throw new DBConnectionNotFound("Aucune connexion disponible");
                }

                String sortPartQuery = this.sorter.GenerateSortSql();
                if (sortPartQuery == null) {
                    sortPartQuery = "";
                }
                String filterPartQuery = this.filter.GenerateFilterSql();
                if (filterPartQuery == null) {
                    filterPartQuery = "";
                }
                String wherePartQuery = this.where.GenerateWhereSql();
                if (wherePartQuery == null) {
                    wherePartQuery = "";
                }

                String sWhere = "";
                if (!"".equals(filterPartQuery)) {
                    if ("".equals(sWhere)) {
                        sWhere = " where " + filterPartQuery;
                    } else {
                        sWhere += " and (" + filterPartQuery + ")";
                    }
                }
                if (!"".equals(wherePartQuery)) {
                    if ("".equals(sWhere)) {
                        sWhere = " where " + wherePartQuery;
                    } else {
                        sWhere += " and (" + wherePartQuery + ")";
                    }
                }
                this.queryB = this.query + sWhere;
                String queryTotalPrepared = this.queryTotal + sWhere;
                statementTotal = conn.prepareStatement(queryTotalPrepared);
                rsTotal = statementTotal.executeQuery();
                this.rowCount = 0;
                while (rsTotal.next()) {
                    this.rowCount = rsTotal.getInt(1);
                }

                String queryPrepared = "";

                if (this.task.equals("Rapport d'activité")) {
                    queryPrepared = this.query + sWhere + " ORDER BY act_date,act_time " + " LIMIT " + limit + " OFFSET " + offset;
                } else {
                    queryPrepared = this.query + sWhere + sortPartQuery + " LIMIT " + limit + " OFFSET " + offset;
                }

                statement = conn.prepareStatement(queryPrepared);
                rs = statement.executeQuery();
                this.data = null;
                this.data = new JSONArray();
                this.rowFilterCount = 0;
                while (rs.next()) {
                    JSONArray row = new JSONArray();
                    this.rowFilterCount++;
                    for (int i = 1; i <= this.columnNames.length; i++) {
                        Object value = rs.getObject(i);
                        row.add((value != null ? value.toString() : "&nbsp;"));
                    }
                    for (int i = 0; this.columnExtras != null && i < this.columnExtras.length; i++) {
                        String html = Util.replaceValuesHTML(this.columnExtras[i], rs);
                        row.add(html);
                    }
                    this.data.add(row);
                }

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (rsTotal != null) {
                rsTotal.close();
            }
            if (statementTotal != null) {
                statementTotal.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        }


    }


    public void getRecordsDocuments(HttpServletRequest request, int offset, int limit) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        ResultSet rsTotal = null;
        PreparedStatement statementTotal = null;
        try {
            {
                conn = PoolConnection.getPoolConnection();
                if (conn == null) {
                    throw new DBConnectionNotFound("Aucune connexion disponible");
                }

                String sortPartQuery = this.sorter.GenerateSortSql();
                if (sortPartQuery == null) {
                    sortPartQuery = "";
                }
                String filterPartQuery = this.filter.GenerateFilterSql();
                if (filterPartQuery == null) {
                    filterPartQuery = "";
                }
                String wherePartQuery = this.where.GenerateWhereSqlDocuments(request);
                if (wherePartQuery == null) {
                    wherePartQuery = "";
                }

                String sWhere = "";
                if (!"".equals(filterPartQuery)) {
                    if ("".equals(sWhere)) {
                        sWhere = " where " + filterPartQuery;
                    } else {
                        sWhere += " and (" + filterPartQuery + ")";
                    }
                }
                if (!"".equals(wherePartQuery)) {
                    if ("".equals(sWhere)) {
                        sWhere = " where " + wherePartQuery;
                    } else {
                        sWhere += " and (" + wherePartQuery + ")";
                    }
                }
                this.queryB = this.query + sWhere;
                String queryTotalPrepared = this.queryTotal + sWhere;
                statementTotal = conn.prepareStatement(queryTotalPrepared);
                rsTotal = statementTotal.executeQuery();
                this.rowCount = 0;
                while (rsTotal.next()) {
                    this.rowCount = rsTotal.getInt(1);
                }

                String queryPrepared = this.query + sWhere + sortPartQuery + " LIMIT " + limit + " OFFSET " + offset;

                statement = conn.prepareStatement(queryPrepared);
                rs = statement.executeQuery();
                this.data = null;
                this.data = new JSONArray();
                this.rowFilterCount = 0;
                while (rs.next()) {
                    JSONArray row = new JSONArray();
                    this.rowFilterCount++;
                    for (int i = 1; i <= this.columnNames.length; i++) {
                        Object value = rs.getObject(i);
                        row.add((value != null ? value.toString() : "&nbsp;"));
                    }
                    for (int i = 0; this.columnExtras != null && i < this.columnExtras.length; i++) {
                        String html = Util.replaceValuesHTML(this.columnExtras[i], rs);
                        row.add(html);
                    }
                    this.data.add(row);
                }

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (rsTotal != null) {
                rsTotal.close();
            }
            if (statementTotal != null) {
                statementTotal.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        }


    }

    public void getNextPage(Connection conn) throws Exception {
        try {
            if ((this.currentPage + 1) * this.ROWS_PER_PAGE > this.rowCount) {
                return;
            }
            this.currentPage++;
            getCurrentPage(conn);
        } catch (Exception ex) {
            throw ex;
        } 
    }

    private void getCurrentPage(Connection conn) throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            
            this.OFFSET = currentPage * this.ROWS_PER_PAGE;
            if (rowCount > 0) {
                if (conn == null) {
                    conn = PoolConnection.getPoolConnection();
                    if (conn == null) {
                        throw new DBConnectionNotFound("Aucune connexion disponible");
                    }
                }

                String sortPartQuery = this.sorter.GenerateSortSql();
                if (sortPartQuery == null) {
                    sortPartQuery = "";
                }
                String filterPartQuery = this.filter.GenerateFilterSql();
                if (filterPartQuery == null) {
                    filterPartQuery = "";
                }
                String queryPrepared = this.query + filterPartQuery + sortPartQuery + " LIMIT " + this.ROWS_PER_PAGE + " OFFSET " + this.OFFSET;

                statement = conn.prepareStatement(queryPrepared);
                rs = statement.executeQuery();
                this.data = null;
                this.data = new JSONArray();
                this.rowFilterCount = 0;
                while (rs.next()) {
                    JSONArray row = new JSONArray();
                    this.rowFilterCount++;
                    for (int i = 1; i <= this.columnNames.length; i++) 
                        row.add(rs.getObject(i));
                    
                    this.data.add(row);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
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

    }

    public void clearSort() {
        this.sorter.clearSort();
    }

    public void addSort(String sColNumber, String sColDir) {
        this.sorter.addSort(sColNumber, sColDir);
    }

    public void clearFilter() {
        this.filter.clearFilter();
    }

    public void addFilter(String sColNumber, String sColValue) {
        this.filter.addFilter(sColNumber, sColValue);
    }

    public void setSearch(String search) {
        this.filter.setSearch(search);
    }

    public void configureDatatableParameters(HttpServletRequest request) throws Exception {
        try {
            String sSortingCols = request.getParameter("iSortingCols");
            if (sSortingCols != null) {
                this.clearSort();
                int iSortingCols = Integer.parseInt(sSortingCols);
                for (int i = 0; i < iSortingCols; i++) {
                    String sColNumber = request.getParameter("iSortCol_" + i);
                    String sColDir = request.getParameter("sSortDir_" + i);
                    this.addSort(sColNumber, sColDir);
                }
            } else {
                this.clearSort();
            }

            this.clearFilter();
            for (int i = 0; i < this.columnNames.length; i++) {
                String sColValue = request.getParameter("sSearch_" + i);
                if (sColValue != null && !sColValue.trim().equals("")) {
                    this.addFilter("" + i, sColValue);
                }
            }

            String sSearch = request.getParameter("sSearch");
            this.setSearch(sSearch);

            String sLimit = request.getParameter("iDisplayLength");
            String sOffset = request.getParameter("iDisplayStart");
            int limit = 0, offset = 0;
            if (sLimit != null) {
                limit = Integer.parseInt(sLimit);
            }
            if (sOffset != null) {
                offset = Integer.parseInt(sOffset);
            }
            this.getRecords(offset, limit);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void configureDatatableParametersDocuments(HttpServletRequest request) throws Exception {
        try {
            
            String sSortingCols = request.getParameter("iSortingCols");
            if (sSortingCols != null) {
                this.clearSort();
                int iSortingCols = Integer.parseInt(sSortingCols);
                for (int i = 0; i < iSortingCols; i++) {
                    String sColNumber = request.getParameter("iSortCol_" + i);
                    String sColDir = request.getParameter("sSortDir_" + i);
                    this.addSort(sColNumber, sColDir);
                }
            } else {
                this.clearSort();
            }

            this.clearFilter();
            for (int i = 0; i < this.columnNames.length; i++) {
                String sColValue = request.getParameter("sSearch_" + i);
                if (sColValue != null && !sColValue.trim().equals("")) {
                    this.addFilter("" + i, sColValue);
                }
            }

            //Filter general
            String sSearch = request.getParameter("sSearch");
            this.setSearch(sSearch);

            String sLimit = request.getParameter("iDisplayLength");
            String sOffset = request.getParameter("iDisplayStart");
            int limit = 0, offset = 0;
            if (sLimit != null) {
                limit = Integer.parseInt(sLimit);
            }
            if (sOffset != null) {
                offset = Integer.parseInt(sOffset);
            }
            this.getRecordsDocuments(request, offset, limit);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String getJavascriptColumnDefinition() {
        String totalDef = null;
        for (int i = 0; i < this.columnNames.length; i++) {
            String def = null;
            if (this.columnWidths != null && i < this.columnWidths.length && this.columnWidths[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sWidth\": \"" + this.columnWidths[i] + "\"";
            }
            if (this.columnAligns != null && i < this.columnAligns.length && this.columnAligns[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sClass\": \"" + this.columnAligns[i] + "\"";
            }
            if (this.columnVisibles != null && i < this.columnVisibles.length && this.columnVisibles[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bVisible\": " + this.columnVisibles[i];
            }
            if (this.columnSortables != null && i < this.columnSortables.length && this.columnSortables[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": " + this.columnSortables[i];
            } else {
                
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": false";
            }
            if (def != null) {
                def = "{ " + def + " }";
            }
            if (def != null) {
                if (totalDef == null) {
                    totalDef = "";
                }
                totalDef += def + ",";
            }
        }
        for (int i = 0; this.columnExtras != null && i < this.columnExtras.length; i++) {
            int otherIndex = i + (this.columnNames != null ? this.columnNames.length : 0);
            String def = null;
            if (this.columnExtras[i] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sType\": \"html\"";
            }
            if (this.columnWidths != null && otherIndex < this.columnWidths.length && this.columnWidths[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sWidth\": \"" + this.columnWidths[otherIndex] + "\"";
            }
            if (this.columnAligns != null && otherIndex < this.columnAligns.length && this.columnAligns[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"sClass\": \"" + this.columnAligns[otherIndex] + "\"";
            }
            if (this.columnVisibles != null && otherIndex < this.columnVisibles.length && this.columnVisibles[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bVisible\": " + this.columnVisibles[otherIndex];
            }
            if (this.columnSortables != null && otherIndex < this.columnSortables.length && this.columnSortables[otherIndex] != null) {
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": " + this.columnSortables[otherIndex];
            } else {
                
                if (def == null) {
                    def = "";
                }
                if (!"".equals(def)) {
                    def += ", ";
                }
                def += "\"bSortable\": false";
            }
            if (def != null) {
                def = "{ " + def + " }";
            }
            if (def != null) {
                if (totalDef == null) {
                    totalDef = "";
                }
                totalDef += def + ",";
            }
        }
        if (totalDef == null) {
            return "";
        }
        return totalDef;
    }

    public void setColumnAlignments(String[] columnAligns) {
        this.columnAligns = columnAligns;
    }

    public void setColumnWidths(String[] columnWidths) {
        this.columnWidths = columnWidths;
    }

   
    public void setColumnExtras(String[] columnExtras) {
        this.columnExtras = columnExtras;
    }

    public String[] getColumnExtras() {
        return new String[(this.columnExtras != null ? this.columnExtras.length : 0)];
    }

    public void setColumnVisibles(Boolean[] columnVisibles) {
        this.columnVisibles = columnVisibles;
    }

    public void setColumnSortables(Boolean[] columnSortables) {
        this.columnSortables = columnSortables;
    }


    public void setColumnSearchables(Boolean[] columnSearchables) {
        this.columnSearchables = columnSearchables;
    }

    public String[] getJavascriptSearchDefinition() {
        if (this.columnSearchables == null) {
            return null;
        }
        String[] search = new String[this.columnSearchables.length];
        for (int i = 0; i < this.columnSearchables.length; i++) {
            if (this.columnSearchables[i] != null && this.columnSearchables[i]) {
                search[i] = "<input type=\"text\" name=\"search_col_\"" + i + "\" placeholder=\"" + (i < this.columnNames.length ? this.columnNames[i] : "") + "\" class=\"search_init\" style=\"color: #383838\"/>";
            } else {
                search[i] = "";
            }
        }
        return search;

    }

    public void clearWhere() {
        this.where.clearWhere();
    }

    public void addWhere(String colName, String colValue) {
        this.where.addWhere(colName, colValue);
    }

    public void addWhereBetween(String colName, String from, String to) {
        this.where.addWhereBetween(colName, from, to);
    }

    public void setTaskCallParameters(HttpServletRequest request) throws Exception {
        try {
            if (!isTaskCall(request)) {
                return;
            }

            int cols = Integer.parseInt(request.getParameter("iDocumentColumns"));
            for (int i = 0; i < cols; i++) {
                String field = request.getParameter("sColumnName_" + i);
                String value = request.getParameter("sColumnValue_" + i);

                if (field != null && !"".equals(field)) {
                    if (value == null) {
                        String sValueStart = request.getParameter("sColumnValue_start_" + i);
                        String sValueEnd = request.getParameter("sColumnValue_end_" + i);
                        if (sValueStart != null && sValueEnd != null) {
                            addWhereBetween(field, sValueStart, sValueEnd);
                        }
                    } else {
                        addWhere(field, value);
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean isTaskCall(HttpServletRequest request) {
        return ("true".equals(request.getParameter("bTaskCallSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public static boolean isMenuCall(HttpServletRequest request) {
        return ("true".equals(request.getParameter("isSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public static boolean isPrevious(HttpServletRequest request) {
        return ("Retour".equals(request.getParameter("isSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public static boolean isPreviousReport(HttpServletRequest request) {
        return ("Previous".equals(request.getParameter("isSubmitPreviousReport")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public static boolean isAjaxCall(HttpServletRequest request) {
        return (request.getParameter("sEcho") != null /*&& request.getSession().getAttribute("report")!=null*/);
    }

    public static boolean isFilterCall(HttpServletRequest request) {
        return ("Valider".equals(request.getParameter("isFilterSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public ReportWhere getWhere() {
        return this.where;
    }

    public String getQuery() {
        return this.query;
    }

    public String getQueryB() {
        return this.queryB;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
