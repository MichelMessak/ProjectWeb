package fr.esiea.fc.model;

import fr.esiea.fc.tasks.document.panelcontrol.NotifMailCorrectError;
import fr.esiea.fc.util.Log;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listen the initalisation and the finalization of the application to init a pool connexion app-managed
 * @author Michel Messak
 */
public class FileControlContextListener implements ServletContextListener
{
    private NotifMailCorrectError notificationMail=null;

    /** The application is starting */
    public void contextInitialized(ServletContextEvent sce)
    {
        Log.write("File Control initialisé");
        PoolConnection.createPool();
        notificationMail=new NotifMailCorrectError();
        notificationMail.contextInitialized(sce);
    }

    /** The application is ending.*/
    public void contextDestroyed(ServletContextEvent sce)
    {
        Log.write("File Control éteint");
        PoolConnection.destroyPool();
        if(notificationMail!=null)
            notificationMail.contextDestroyed(sce);
            notificationMail=null;
    }

}
