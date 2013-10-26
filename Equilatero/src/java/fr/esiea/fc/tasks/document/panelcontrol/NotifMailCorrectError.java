package fr.esiea.fc.tasks.document.panelcontrol;

/**
 * Envio de correos para notificaciones de documentos generados correctamente y con errores
 * @author Edgar Adrian Muñoz Morales
 */
import fr.esiea.fc.model.Configuration;
import fr.esiea.fc.model.security.Notification;
import fr.esiea.fc.model.security.NotificationDAO;
import fr.esiea.fc.notification.mail.SimpleMail;
import fr.esiea.fc.util.Log;
import com.itc.repository.fc4Repository;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class NotifMailCorrectError extends TimerTask implements ServletContextListener {
    private Timer timer;
    private int time;
    private Boolean error;
    private Boolean totals;
    String user = null;
    String email = null;
    String SIRET = null;
    String errors = null;
    String corrects = null;
    private boolean hasToStop=false;
    
    public void contextInitialized(ServletContextEvent evt) {
        try {
            Log.write("Inicializando envio de correos.");
            //Primero cargamos la configuracion en el archivo properties
            Configuration.Load();
            error = Boolean.valueOf(Configuration.getProperty("fc4.notification.notifyByError"));
            totals = Boolean.valueOf(Configuration.getProperty("fc4.notification.notifyTotals"));
            int time = Integer.parseInt(Configuration.getProperty("fc4.notification.notifyTotals.time"));

            Log.write("Equilátero enviara notificaciones de correo cada "+time+" minutos.");
            if(error)
                Log.write("El envio de notificaciones de correos con error se encuentra activado");
            else
                Log.write("El envio de notificaciones de correos con error se encuentra desactivado");
            if(totals)
                Log.write("El envio de notificaciones de correos con los documentos totales se encuentra activado");
            else
                Log.write("El envio de notificaciones de correos con los documentos totales se encuentra desactivado");
            //int time = Integer.parseInt(Stime);
            timer = new Timer();
            //Para pasar minutos a milisegundos unicamente multiplicamos time que viene en el properties en minutos
            //lo multiplicamos por 60000 que es 1 minuto en milisegundos
            timer.schedule(this, 1000, time * 60000);
            //timer.schedule(this, 1000, time * 60 * 1000)
        } catch (Exception ex) {
        }
    }
    public void contextDestroyed(ServletContextEvent evt)
    {
        hasToStop=true;
        timer.cancel();
        timer.purge();
        Log.write("Se detuvo el envio de notificación de correos");
    }

    public void run(){
        try {
            //nuevamente se carga el archivo properties para recargar la bandera de encio de notificaciones
            Configuration.Load();
            //Se convierte a booleanos los Strijng para revisar banderas en el archivo properties
            error = Boolean.valueOf(Configuration.getProperty("fc4.notification.notifyByError"));
            totals = Boolean.valueOf(Configuration.getProperty("fc4.notification.notifyTotals"));
            //verificamos si la bandera para envio de doctos generados con error esta activada
            if(error)
            {
                if(hasToStop) return;

                List<Notification> envios = NotificationDAO.getUsersAdministrators();

                for (Notification notifica : envios)
                {
                    if(hasToStop) return;
                    fc4Repository.Init();
                    SimpleMail.setRootPath(fc4Repository.getMailPath());
                    user = notifica.getUser();
                    email = notifica.getEmail();
                    SIRET = notifica.getEnterpriseID();
                    errors = notifica.getErrors();
                    corrects = notifica.getCorrect();
                    Log.write("Enviando correo de notificacion de errores al usuario: "+user);
                    //Verifica por medio del vector que el RFC contenga documentos asociados
                    //generados con error
                        if(Integer.parseInt(errors) >= 1){
                                String htmlBody = SimpleMail.loadTemplate(SimpleMail.NOTIFICATIONS_CORRECTS_ERRORS);
                                SimpleMail.send(new String[]{email}, "Notificacion de Documentos con Errores",
                                    htmlBody.replace("$replace",
                                    "<br><span>RFC:"+SIRET+"<br>"+
                                    "<span style='color: red'>Errores: "+errors+"</span><br>"
                                ), null, SimpleMail.TYPE_HTML);

                        }
                    
                    }
                }

                if(hasToStop) return;

                //verificamos si la bandera para envio de doctos totales esta levantada
                if(totals)
                {

                    if(hasToStop) return;
                    List<Notification> envios = NotificationDAO.getUsersAdministrators();

                for (Notification notifica : envios)
                {
                    if(hasToStop) return;
                    fc4Repository.Init();
                    SimpleMail.setRootPath(fc4Repository.getMailPath());
                    user = notifica.getUser();
                    email = notifica.getEmail();
                    SIRET = notifica.getEnterpriseID();
                    errors = notifica.getErrors();
                    //errores ="2";
                    corrects = notifica.getCorrect();
                    Log.write("Enviando correo de notificacion de totales al usuario: "+user);
                    //Verifica por medio del vector que el RFC contenga documentos asociados
                    //generados con error
                            String htmlBody = SimpleMail.loadTemplate(SimpleMail.NOTIFICATIONS_CORRECTS_ERRORS);
                            SimpleMail.send(new String[]{email}, "Notificacion de Documentos con Correctos y con Errores",
                                htmlBody.replace("$replace",
                                "<br><span>RFC:"+SIRET+"<br>"+
                                "<span style='color: red'>Errores: "+errors+"</span><br>"+
                                "<span style='color: green'>Correctos: "+corrects+"</span><br>"
                            ), null, SimpleMail.TYPE_HTML);
                    }
                }
            
        } catch (Exception ex) {
            Log.write(ex);
        }
     }
}
