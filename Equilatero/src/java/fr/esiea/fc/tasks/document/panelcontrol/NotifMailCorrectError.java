package fr.esiea.fc.tasks.document.panelcontrol;

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
      
            timer = new Timer();


            timer.schedule(this, 1000, time * 60000);
        } catch (Exception ex) {
        }
    }
    public void contextDestroyed(ServletContextEvent evt)
    {
        hasToStop=true;
        timer.cancel();
        timer.purge();
        Log.write("L'envoie de notification par courrier s'est arrété");
    }

    public void run(){
        try {
            Configuration.Load();
            error = Boolean.valueOf(Configuration.getProperty("fc4.notification.notifyByError"));
            totals = Boolean.valueOf(Configuration.getProperty("fc4.notification.notifyTotals"));
            if(error)
            {
                if(hasToStop) return;

                List<Notification> send = NotificationDAO.getUsersAdministrators();

                for (Notification notification : send)
                {
                    if(hasToStop) return;
                    fc4Repository.Init();
                    SimpleMail.setRootPath(fc4Repository.getMailPath());
                    user = notification.getUser();
                    email = notification.getEmail();
                    SIRET = notification.getEnterpriseID();
                    errors = notification.getErrors();
                    corrects = notification.getCorrect();
                    Log.write("Enviando correo de notificacion de errores al usuario: "+user);
                        if(Integer.parseInt(errors) >= 1){
                                String htmlBody = SimpleMail.loadTemplate(SimpleMail.NOTIFICATIONS_CORRECTS_ERRORS);
                                SimpleMail.send(new String[]{email}, "Notificacion de Documents avec Erreurs",
                                    htmlBody.replace("$replace",
                                    "<br><span>SIRET:"+SIRET+"<br>"+
                                    "<span style='color: red'>ERREURS: "+errors+"</span><br>"
                                ), null, SimpleMail.TYPE_HTML);

                        }
                    
                    }
                }

                if(hasToStop) return;

                if(totals)
                {

                    if(hasToStop) return;
                    List<Notification> sends = NotificationDAO.getUsersAdministrators();

                for (Notification notification : sends)
                {
                    if(hasToStop) return;
                    fc4Repository.Init();
                    SimpleMail.setRootPath(fc4Repository.getMailPath());
                    user = notification.getUser();
                    email = notification.getEmail();
                    SIRET = notification.getEnterpriseID();
                    errors = notification.getErrors();
                    corrects = notification.getCorrect();
                    Log.write("Envoie de courrier de notificatgion total de l'utilisateur: "+user);
                    
                            String htmlBody = SimpleMail.loadTemplate(SimpleMail.NOTIFICATIONS_CORRECTS_ERRORS);
                            SimpleMail.send(new String[]{email}, "Notificacion de Documents Correcte et Erronée",
                                htmlBody.replace("$replace",
                                "<br><span>SIRET:"+SIRET+"<br>"+
                                "<span style='color: red'>ERREUR: "+errors+"</span><br>"+
                                "<span style='color: green'>COrrects: "+corrects+"</span><br>"
                            ), null, SimpleMail.TYPE_HTML);
                    }
                }
            
        } catch (Exception ex) {
            Log.write(ex);
        }
     }
}
