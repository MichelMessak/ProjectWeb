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

    /**
     * Execute the query configurated. It is use to start the
     * Ejecuta el query configurado. Se usa para empezar la carga del objeto
     */
    public void ExecuteQuery(HttpServletRequest request) throws Exception {
        try {
            ExecuteQueryForTotals(request);
           
        } catch (Exception ex) {
            throw ex;
        }
    }

   
    /**
     * Obtiene el número de columnas del reporte
     * @return  Número de columnas en el reporte
     */
    public int getColumnCount() {
        if (this.columnNames != null) {
            return this.columnNames.length;
        } else {
            return 0;
        }
    }

    /**
     * Obtiene el número de la página actual
     * @return  número de página
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * Obtiene el número total de registros en el query
     * @return  número total de registros
     */
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * Obtiene el número total de registros filtrados en el último filtro consultado
     * @return  número total de registros filtrados
     */
    public int getRowFilterCount() {
        return this.rowFilterCount;
    }

    /**
     * Obtiene el número total de páginas de acuerdo al número de registros por página configurado
     * @return  número total de páginas
     */
    public int getPageCount() {
        return this.pageCount;
    }

    /**
     * Obtiene el arreglo de nombre de columnas del reporte
     * @return
     */
    public String[] getColumnNames() {
        return this.columnNames;
        //return Arrays.asList(colNames).iterator();
    }

    /**
     * Obtiene el URL que responde al paginado asíncrono
     * @return  La cadena conteniendo el URL que el objeto javascript debe llamar para procesamiento asíncrono de búsquedas,
     * ordenamiento y filtrado
     */
    public String getURL() {
        return this.URL;
    }

    /**
     * Obtiene los registros desde la base de datos en formato JSON
     * @return  Un arreglo JSON {@link JSONArray} de filas. Cada fila es otro arreglo JSON de valores de las columnas.
     */
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

    
    /**
     * Permite cargar los registros de la página anterior
     * @param conn  La conexión a la base de datos si se tiene una. Si la conexión es nula, se pide una del pool.
     * Ver {@link PoolConnection}
     * @throws Exception
     */
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

    /**
     * Permite obtener los registros de la base de datos de acuerdo a un inicio y un límite. Usado por el objeto
     * javascript de paginación. Esta función carga los registros en un arreglo JSON de filas. Donde cada fila contiene
     * un arreglo JSON de valores de columnas.
     * @param offset    Número inicial del registro donde empezar
     * @param limit     Número total de registros a cargar
     * @throws Exception
     */
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

    /**
     * Permite cargar los registros de la página siguiente
     * @param conn  La conexión a la base de datos si se tiene una. Si la conexión es nula, se pide una del pool.
     * Ver {@link PoolConnection}
     * @throws Exception
     */
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

    /**
     * Permite cargar los registros de la página actual
     * @param conn  La conexión a la base de datos si se tiene una. Si la conexión es nula, se pide una del pool.
     * Ver {@link PoolConnection}
     * @throws Exception
     */
    private void getCurrentPage(Connection conn) throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //Futuro: Mejorar rendimiento trayendo bloques de paginas y no pagina por pagina
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

    /**
     * Permite limpiar la información de ordenamiento. Se usa esta limpieza por cada request asíncrono
     * del objeto javascript que atiende el ordenamiento
     */
    public void clearSort() {
        this.sorter.clearSort();
    }

    /**
     * Añade una columna en el ordenamiento con su dirección (asc ó desc). Esta información es enviada por el objeto javascript
     * en cada request asíncrono
     * @param sColNumber
     * @param sColDir
     */
    public void addSort(String sColNumber, String sColDir) {
        this.sorter.addSort(sColNumber, sColDir);
    }

    /**
     * Permite limpiar la información de filtrado. Se usa por cada request asíncrono
     */
    public void clearFilter() {
        this.filter.clearFilter();
    }

    /**
     * Añade un nuevo filtro dado la columna y su valor
     * @param sColNumber    Índice de la columna a ser filtrada
     * @param sColValue     Valor de filtro de la columna
     */
    public void addFilter(String sColNumber, String sColValue) {
        this.filter.addFilter(sColNumber, sColValue);
    }

    /**
     * Configura la búsqueda genérica de todas las columnas
     * @param search    Valor a buscar
     */
    public void setSearch(String search) {
        this.filter.setSearch(search);
    }

    /**
     * Permite configurar los parámetros del requerimiento (request) asíncrono. Esta función
     * carga los registros de acuerdo a lo requerido y los deja en el arreglo JSON de datos.
     * El controlador se encarga de enviar este objeto a la vista de paginación para poder
     * generar el objeto javascript necesario para atender la petición.
     * @param request   Request asíncrono recibido desde el objeto javascript
     * @throws Exception
     */
    public void configureDatatableParameters(HttpServletRequest request) throws Exception {
        try {
            //Carga de datos provenientes del datatable
            //Sorting
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

            //Filters by column
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
            this.getRecords(offset, limit);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void configureDatatableParametersDocuments(HttpServletRequest request) throws Exception {
        try {
            //Carga de datos provenientes del datatable
            //Sorting
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

    /**
     * Obtiene la definición javascript de las columnas del objeto para armar la salida
     * @return  La definición en javascript de las columnas del objeto.<br/>
     * Ejemplo:<br/>
     * {<br/>
     *      {sWidth: 10%, sClass:left, bVisible: true, bSortable: true},<br/>
     *      {sWidth: 20%, sClass:right, bVisible: false, bSortable: false},<br/>
     * }
     */
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
                //SGH: El default en datatable es ordenar. Yo lo necesito al reves
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
                //SGH: El default en datatable es ordenar. Yo lo necesito al reves
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

    /**
     * Permite configurar las alineaciones de las columnas
     * @param columnAligns  Arreglo de cadenas con las alineaciones: left, right o center
     */
    public void setColumnAlignments(String[] columnAligns) {
        this.columnAligns = columnAligns;
    }

    /**
     * Permite configurar los anchos de las columnas
     * @param columnWidths  Arreglo de cadenas con los anchos en porcentajes. <br/>
     * Ejemplo:<br/>
     *  report.setColumnWidths(new String[]{"10%", "5%", "20%"});
     */
    public void setColumnWidths(String[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    /**
     * Permite configurar las columnas extras. Estas columnas extras son html puro que el objeto escribe en la salida.
     * @param columnExtras  Arreglo de cadenas de las columnas extras. Estas cadenas deben contener html.<br/>
     * Si se necesitan referenciar a datos de alguna columna de la fila actual, se debe usar la notación
     * <code>{<numeroDeColumna>}</code>
     * Ejemplo:<br/>
     * report.setColumnExtras(new String[]{"<form method=\"post\" action=\"procesa.html\"><input type=\"text\" name=\"campo1\" value=\"{0}\"/></form>"});
     */
    public void setColumnExtras(String[] columnExtras) {
        this.columnExtras = columnExtras;
    }

    /**
     * Permite obtener las columnas extras para escribirlas en la salida
     * @return  Arreglo de cadenas con las columnas extras en HTML
     */
    public String[] getColumnExtras() {
        return new String[(this.columnExtras != null ? this.columnExtras.length : 0)];
    }

    /**
     * Configura la visibilidad de las columnas
     * @param columnVisibles    Arreglo de lógicos que permite especificar si la columna es visible o invisible
     */
    public void setColumnVisibles(Boolean[] columnVisibles) {
        this.columnVisibles = columnVisibles;
    }

    /**
     * Configura la ordenabilidad de las columnas
     * @param columnSortables   Arreglo de lógicos que permite especificar si la columna permite ordenamiento o no
     */
    public void setColumnSortables(Boolean[] columnSortables) {
        this.columnSortables = columnSortables;
    }

    /**
     * Configura la buscabilidad de las columnas
     * @param columnSearchables Arreglo de lógicos que permite especificar si la columna permite filtros de búsqueda
     */
    public void setColumnSearchables(Boolean[] columnSearchables) {
        this.columnSearchables = columnSearchables;
    }

    /**
     * Obtiene la definición de búsquedas por columna en el objeto. Es usada en la presentación del objeto
     * @return  Arreglo de cadenas por columna que permite configurar la búsqueda o filtro por columnas
     */
    public String[] getJavascriptSearchDefinition() {
        //int len=(columnNames!=null?columnNames.length:0)+(columnExtras!=null?columnExtras.length:0);
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

    /**
     * Permite limpiar los filtros por columna. Usada por cada request asíncrono para empezar una nueva especificación
     * de filtros.
     */
    public void clearWhere() {
        this.where.clearWhere();
    }

    /**
     * Añade una búsqueda o filtro por columna especificada al momento de request asíncrono
     * mediante un valor determinado
     * @param colName   Nombre de la columna
     * @param colValue  Valor de filtro de la columna
     */
    public void addWhere(String colName, String colValue) {
        this.where.addWhere(colName, colValue);
    }

    /**
     * Añade una búsqueda o filtro por columna especificada al momento del request asíncrono
     * mediante rango de valores. Usada para rangos de fechas o de valores
     * @param colName   Nombre de la columna
     * @param from      Valor inicial
     * @param to        Valor final
     */
    public void addWhereBetween(String colName, String from, String to) {
        this.where.addWhereBetween(colName, from, to);
    }

    /**
     * Permite configurar los parámetros de una llamada desde otra tarea.
     * Se usa aquí una definición de llamada estándar de tareas por medio de parámetros del request:
     *  bTaskCallSubmit:        true si se trata de una llamada desde otra tarea
     *  iDocumentColumns:       Número de columnas especificadas en este request
     *  SColumnName_i:          Nombre de la columna a filtrar
     *  sColumnValue_i:         Valor de la columna a filtrar (mediante valor determinado)
     *  sColumnValue_start_i:   Valor inicial de la columna a filtrar (mediante un rango de valores)
     *  sColumnValue_end_i:     Valor final de la columna a filtrar (mediante un rango de valores)
     * @param request   Request HTTP de la llamada
     * @throws Exception
     */
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

    /**
     * Permite saber si la llamada corresponde a una desde otra tarea
     * @param request   Request HTTP
     * @return  Verdadero si la llamada se realizó desde otra tarea
     */
    public static boolean isTaskCall(HttpServletRequest request) {
        return ("true".equals(request.getParameter("bTaskCallSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    /**
     * Permite saber si la llamada se hizo desde el menú de tareas del usuario
     * @param request   Request HTPP
     * @return  Verdadeo si la llamada se realizó desde el menú
     */
    public static boolean isMenuCall(HttpServletRequest request) {
        return ("true".equals(request.getParameter("isSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }


    public static boolean isPrevious(HttpServletRequest request) {
        return ("Retour".equals(request.getParameter("isSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    public static boolean isPreviousReport(HttpServletRequest request) {
        return ("Previous".equals(request.getParameter("isSubmitPreviousReport")) /*&& request.getSession().getAttribute("report")==null*/);
    }

    /**
     * Permite saber si la llamada es un requerimiento asincrono ajax de paginación, ordenamiento y/o búsqueda
     * @param request   Request HTTP
     * @return  Verdadero si la llamada es un requerimiento asíncrono de ajax
     */
    public static boolean isAjaxCall(HttpServletRequest request) {
        return (request.getParameter("sEcho") != null /*&& request.getSession().getAttribute("report")!=null*/);
    }

    /**
     * Permite saber si la llamada es un filtrado de valores de alguna forma anterior al reporte. Algunos reportes
     * cuentan con una forma de filtrado antes de mostrar el reporte.
     * @param request   Request HTTP
     * @return  Verdadero si la llamada se hizo desde una pantalla de filtrado
     */
    public static boolean isFilterCall(HttpServletRequest request) {
        System.out.println(request.getParameter("isFilterSubmit"));
        return ("Consult".equals(request.getParameter("isFilterSubmit")) /*&& request.getSession().getAttribute("report")==null*/);
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
