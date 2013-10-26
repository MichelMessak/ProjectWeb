package com.itc.fc4.importa;

import com.itc.fc4.importa.errores.Errores;
import com.itc.fc4.importa.errores.IncorrectPeriod;
import com.itc.repository.fc4Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author J. Jahir Barojas M.
 */
public class Conexion
{
    Datos xml=null;
    StringBuilder queryB=new StringBuilder();
    StringBuilder queryFilesB=new StringBuilder();
    StringBuilder queryDelFilesB=new StringBuilder();
    StringBuilder queryEventosB = new StringBuilder();
    StringBuilder queryTypeFile = new StringBuilder();
    /**
     * Schema a utilizar. Puede ser el obtenido del documento o en caso de no encontrar el periodo en el documento
     * lo toma de la BD.
     */
    String schemaCurrent="period_";
    String currentPeriod="";
    /**
     *
     * @param xmlData
     */
    public Conexion(Datos xmlData)
    {
        this.xml = xmlData;
    }

    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void exec() throws ClassNotFoundException, SQLException, IncorrectPeriod
    {

        StringBuilder queryQuest = new StringBuilder("SELECT emp_id, dty_id, dst_id, doc_id  FROM "); //= "SELECT emp_id, dty_id, dst_id, doc_id  FROM common.documents WHERE emp_id = '"+xml.getEmpresa()+"' AND dty_id = '"+/*this.xml.getTipo()*/"CFD"+"' AND dst_id = '"+this.xml.getSubTipo()+"' AND doc_id = '"+xml.getSerie()+xml.getFolio()+"'";
        StringBuilder getPeriod = new StringBuilder("SELECT per_id FROM common.periods WHERE per_id = '");
        StringBuilder getCurrentPeriod = new StringBuilder("SELECT per_id FROM common.periods WHERE per_status = 'P'");
        String anioDoc=null;
        String driverJDBC=fc4Repository.fc4Properties.getProperty("fc4.jdbcDriverClass");
        String urlJDBC=fc4Repository.fc4Properties.getProperty("fc4.jdbcUrl");
        String usrBD=fc4Repository.fc4Properties.getProperty("fc4.jdbcUser");
        String passBD=fc4Repository.fc4Properties.getProperty("fc4.jdbcPassword");


        Class.forName(driverJDBC);
        Connection con = DriverManager.getConnection(urlJDBC, usrBD, passBD);
        con.setAutoCommit(false);
        Statement stmt = con.createStatement();


        //Obtiene el año del documento,si es nullo lo pondra en el periodo (año corriente) obteniendolo de una consulta.
        anioDoc=xml.getAnio();
        if(anioDoc != null)//Si se pudo obtener el perido (año) del documento
        {
            getPeriod.append(anioDoc+"'");
            ResultSet rsGetPeriod = stmt.executeQuery(getPeriod.toString());

            int i=0;
            while(rsGetPeriod.next())
            {                
                i++;
                currentPeriod = rsGetPeriod.getString("per_id");
            }
            //Si no encuentra el periodo obtenido del documento en la bd, manda excepción.
            if(i == 0)
                throw new IncorrectPeriod(Errores.ERRPERIODOINCORRECTO,"Error en el periodo. No se econtró el periodo ["+anioDoc+"] en la BD");

            
            schemaCurrent = schemaCurrent+currentPeriod;
        }
        else//Si no se pudo obtener el año del documento, va a grabar en el periodo corriente.
        {
        ResultSet rsGetCurrent = stmt.executeQuery(getCurrentPeriod.toString());
            int i=0;
            while(rsGetCurrent.next())
            {
                i++;
                currentPeriod = rsGetCurrent.getString("per_id");
            }
            schemaCurrent = schemaCurrent+currentPeriod;
        }

        //Verifica que tenga las 4 llaves, en caso de que NO, manda error a tabla de errores
            if(this.xml.getEmpresa().equals("") || this.xml.getFolio().equals("") || this.xml.getTipo().equals(""))
            {
                StringBuilder queryErrorB = new StringBuilder();
                //Modifica el path de ruta de almacenamiento, ya que tiene error.
                String tmpRutaError = this.xml.getRutaArchivoEntrada();
                String tmpComp="";
                int pos = tmpRutaError.indexOf("\\");
                String antes = tmpRutaError.substring(0, pos);
                String despues = tmpRutaError.substring(pos+1);
                if(this.xml.getEmpresa().equals(""))
                    tmpComp = antes+"\\Err\\"+despues;
                else
                    tmpComp = "Err\\"+despues;
                this.xml.setRutaArchivoEntrada(tmpComp);
                //Crea sentencia sql para insertar en la tabla de errores.
                queryErrorB.append("INSERT INTO ");
                queryErrorB.append(schemaCurrent+".errors (err_date, err_time, err_path, err_filename, err_code, err_description, err_stack, err_num)");
                queryErrorB.append("VALUES ('"+this.xml.getFechaMod()+"','"+this.xml.getHoraMod()+"','"+this.xml.getRutaArchivoEntrada()+"','"+
                            this.xml.getArchivoEntrada()+"','1','Error, faltó algun dato requerido PK','','1')");

                ResultSet error = stmt.executeQuery(queryErrorB.toString());
                error.close();
                return;
            }

        //Arma Query de consulta
        queryQuest.append(schemaCurrent+".documents ");
        queryQuest.append("WHERE emp_id = '"+xml.getEmpresa()+"' AND dty_id = '"+this.xml.getTipo()+"' AND dst_id = '"+this.xml.getSubTipo()+"' AND doc_id = '"+xml.getId()+"'");

        ResultSet rsQuest = stmt.executeQuery(queryQuest.toString());

        int numRows = 0;
        while(rsQuest.next())
        {
            numRows++;
        }
        rsQuest.close();
        if(numRows > 0)
        {

            this.queryB.append("UPDATE ");
            this.queryB.append(schemaCurrent+".documents ");
            this.queryB.append("SET doc_creation_date='"+xml.getFechaGen()+"',doc_creation_time='"+xml.getHoraGen()+
                               "',doc_last_mod_date='"+xml.getFechaMod()+"',doc_last_mod_time='"+xml.getHoraMod()+
                               "',emp_id_from='"+xml.getEmpresaReceptora()+"',doc_status='"+xml.getStatus()+
                               "' WHERE emp_id = '"+this.xml.getEmpresa()+"' AND dty_id = '"+this.xml.getTipo()+"' AND dst_id = '"+this.xml.getSubTipo()+
                               "' AND doc_id = '"+xml.getId()+"'");

            this.queryTypeFile.append("UPDATE ");
            this.queryTypeFile.append(schemaCurrent+".dtype_cfd ");
            this.queryTypeFile.append("SET emp_id='"+xml.getEmpresa()+"',dty_id='"+xml.getTipo()+
                               "',dst_id='"+xml.getSubTipo()+"',doc_id='"+xml.getId()+"',cfd_id2='"+xml.getId2()+
                               "',cfd_folio='"+xml.getFolio()+"',cfd_type='"+xml.getTipo()+"',cfd_currency='"+xml.getMoneda()+"'" +
                               ",cfd_total='"+xml.getTotal()+"',cfd_status='"+xml.getStatus()+"',cfd_serie='"+xml.getSerie()+"'");


            this.queryDelFilesB.append("DELETE FROM ");
            this.queryDelFilesB.append(schemaCurrent+".document_files ");
            this.queryDelFilesB.append("WHERE emp_id = '"+this.xml.getEmpresa()+"' AND dty_id = '"+this.xml.getSubTipo()+"' AND dst_id = '"+
                                        this.xml.getTipo()+"' AND doc_id = '"+xml.getId()+"'");

        }
        else
        {
                this.queryB.append("INSERT INTO ");
                this.queryB.append(schemaCurrent+".documents VALUES ");
                this.queryB.append("('"+xml.getEmpresa()+"','"+xml.getTipo()+"','"+xml.getSubTipo()+"','"+xml.getId()+"','"+
                                   xml.getFechaGen()+"','"+xml.getHoraGen()+"','"+xml.getFechaMod()+"','"+xml.getHoraMod()+"','"+
                                   xml.getEmpresaReceptora()+"','"+xml.getStatus()+"')");


            this.queryTypeFile.append("INSERT INTO ");
            this.queryTypeFile.append(schemaCurrent+".dtype_cfd VALUES ");
            this.queryTypeFile.append("('"+xml.getEmpresa()+"','"+xml.getTipo()+"','"+xml.getSubTipo()+"','"+xml.getId()+"','"+
                                        xml.getId2()+"','"+xml.getFolio()+"','"+xml.getTipo()+"','"+xml.getMoneda()+"','"+
                                        xml.getTotal()+"','"+xml.getStatus()+"','"+xml.getSerie()+"')");

        }
        
        this.queryFilesB.append("INSERT INTO ");
        this.queryFilesB.append(schemaCurrent+".document_files ");
        this.queryFilesB.append("(emp_id, dty_id, dst_id, doc_id, path, filename) VALUES ('"+
                    xml.getEmpresa()+"','"+xml.getTipo()+"','"+xml.getSubTipo()+"','"+xml.getId()+"','"+
                    xml.getRutaArchivoEntrada()+"','"+xml.getArchivoEntrada()+"')");

        this.queryEventosB.append("INSERT INTO ");
        this.queryEventosB.append(schemaCurrent+".document_events ");
        this.queryEventosB.append("(emp_id, dty_id, dst_id, doc_id, evt_date, evt_time, evt_err_code, evt_err_severity, evt_err_description, evt_err_stack) VALUES ('"+
                       xml.getEmpresa()+"','"+xml.getTipo()+"','"+xml.getSubTipo()+"','"+xml.getId()+"','"+xml.getFechaMod()+"','"+xml.getHoraMod()+"','0', '0','OK','-')");

        if(this.queryDelFilesB.length()>0)
            stmt.executeUpdate(this.queryDelFilesB.toString());
        
        stmt.executeUpdate(this.queryB.toString());
        stmt.executeUpdate(this.queryTypeFile.toString());

        Vector vectArchivos=xml.getListaArchivosEntrada();
        if(vectArchivos == null)
            stmt.executeUpdate(this.queryFilesB.toString());
        else
        {
            stmt.executeUpdate(this.queryFilesB.toString());
            for(int i=0;i<vectArchivos.size();i++)
            {
                this.queryB.append("INSERT INTO ");
                this.queryB.append(schemaCurrent+".document_files ");
                this.queryB.append("(emp_id, dty_id, dst_id, doc_id, path, filename) VALUES ('"+
                        xml.getEmpresa()+"','"+xml.getTipo()+"','"+xml.getSubTipo()+"','"+xml.getId()+"','"+
                        xml.getRutaArchivoEntrada()+"','"+vectArchivos.get(i).toString()+"')");
                stmt.executeUpdate
                        (this.queryFilesB.toString());
            }
        }
        stmt.executeUpdate(this.queryEventosB.toString());
        con.commit();
        stmt.close();
        con.close();
    }
}

