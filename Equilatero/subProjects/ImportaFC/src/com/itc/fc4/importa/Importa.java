package com.itc.fc4.importa;

import com.itc.fc4.importa.errores.Errores;
import com.itc.fc4.importa.errores.IncorrectPeriod;
import com.itc.repository.fc4Repository;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Importa
{
    public static void main(String[] args) 
    {
        try
        {
            if (args.length < 1)
            {
                System.err.println("Error en los parametros.");
                showUsage();
                System.exit(1);
            }

            fc4Repository.Init();

            Datos xml = Xml.getValuesFromXML(args);

            if(args.length > 1)
            {
                Vector vectArchivos = new Vector();
                for(int i=1;i<args.length;i++)
                {
                    vectArchivos.add(args[i]);
                }
                xml.setListaArchivosEntrada(vectArchivos);
            }

            Conexion conn = new Conexion(xml);
            conn.exec();
     
            for(int i=0;i<args.length;i++)
            {
                    fc4Repository.fc4MoveFile(xml.getRutaArchivoEntrada(),args[i]);
            }
            
            System.exit(0);
        }
        catch (ClassNotFoundException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.CONEXIONERROR);
            
        }
        catch (SQLException ex)
        {

            System.err.println(ex.getMessage());
            System.exit(Errores.SQLERROR);
        }
        catch (ParseException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.PARSEOFECHAERROR);
        }
        catch (SAXException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.PARSEOXMLERROR);
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.ARCHIVONOENTRADO);
        }
        catch (ParserConfigurationException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.ERRORDOCMEMORIA);
        }
         catch (IncorrectPeriod ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.ERRPERIODOINCORRECTO);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            System.exit(Errores.ERRLIBREPOSITORY);
        }
    }
   // validar que el archivo exista
   // validar que sea un CFD
    private static void showUsage()
    {
        System.out.println("*************************************\n" +
                "     |== ImportaFC version 1.0.0.1 ==|\n\n" +
                "Uso: java -jar ImportaFC.jar <archivoXML>\n" +
                "*************************************");
    }
}