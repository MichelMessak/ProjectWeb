package fr.esiea.fc.util;

import fr.esiea.fc.model.Configuration;
import fr.esiea.fc.util.error.ConfigurationMissingException;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Handler for logs
 * @author Dispa Cécile
 */
public class Log
{
    public static final SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd"),
            timeFormat=new SimpleDateFormat("HHmmssSSS"),
            yearFormat=new SimpleDateFormat("yyyy"),
            monthFormat=new SimpleDateFormat("MM"),
            dayFormat=new SimpleDateFormat("dd");

    public static DataOutputStream getFile(Date date) throws FileNotFoundException, Exception
    {
        try
        {
            Configuration.Load();
            String dateLog=format.format(date);
            String logName=Configuration.getProperty("fc4.log");
            if(logName==null) throw new ConfigurationMissingException("Localisation du dossier impossible");
            logName+=File.separator+yearFormat.format(date)+File.separator+monthFormat.format(date);
            File file=new File(logName);
            file.mkdirs();
            logName+=File.separator+"fc4."+dateLog+".log";
            return new DataOutputStream(new FileOutputStream(logName, true));
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    public static void write(Exception exc)
    {
        DataOutputStream out=null;
        try
        {
            Calendar calendar=Calendar.getInstance();
            Date date=calendar.getTime();
            out=getFile(date);
            String hour=timeFormat.format(date);
            StackTraceElement[] elem=exc.getStackTrace();
            for(int i=0;elem!=null && i<elem.length;i++)
            {
                String msg=(i==0?hour+": ":"           ")+elem[i]+"\n";
                out.write(msg.getBytes("UTF-8"));
            }
            out.close();
            out=null;
        }
        catch(Exception ex)
        {
            System.out.println("Exeption: "+ex.getMessage());
        }
        finally
        {
            try
            {
                if(out!=null) out.close();
            }
            catch(Exception ex)
            {

            }
        }

    }

    public static void write(String message)
    {
        DataOutputStream out=null;
        try
        {
            Calendar calendar=Calendar.getInstance();
            Date date=calendar.getTime();
            out=getFile(date);
            String hour=timeFormat.format(date);
            String ansi=hour+": "+message+"\n";
            out.write(ansi.getBytes("UTF-8"));
            out.close();
            out=null;
        }
        catch(Exception ex)
        {
            System.out.println("Exeption: "+ex.getMessage());
        }
        finally
        {
            try
            {
                if(out!=null) out.close();
            }
            catch(Exception ex)
            {

            }
        }
    }

    public static String getFileName(Date date) throws Exception
    {
        try
        {
            Configuration.Load();
            String logPath=Configuration.getProperty("fc4.log");
            String dateLog=yearFormat.format(date)+monthFormat.format(date)+dayFormat.format(date);
            String logName=logPath+File.separator+yearFormat.format(date)+File.separator+monthFormat.format(date)+File.separator+"fc4."+dateLog+".log";
            return logName;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

}
