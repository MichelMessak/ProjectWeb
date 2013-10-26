package fr.esiea.fc.notification.mail;

import fr.esiea.fc.util.error.ConfigurationMissingException;
import com.itc.repository.fc4Repository;
import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;

/**
 * Notificacion simple via mail
 * @author Segundo García Heras
 * @since 1.0.0.0
 */

public class SimpleMail
{
    public static final int TYPE_PLAIN = 1;
    public static final int TYPE_HTML = 2;

    public static final int USER_MAX_FAIL_ATTEMPT_REACHED = 1;
    public static final int USER_REMINDER_PASSWORD = 2;
    public static final int NOTIFICATIONS_CORRECTS_ERRORS = 3;
    public static final int ADMIN_MAX_FAIL_ATTEMPT_REACHED = 4;

    private static String rootPath=null;

    public static void setRootPath(String rootPath)
    {
        SimpleMail.rootPath=rootPath;
    }

    public static void send(final String[] to, String subject, final String body, String[] attachments, int type) throws Exception
    {

        // Sender's email ID needs to be mentioned
        String from = fc4Repository.getNotificationSenderAddress();

        // Assuming you are sending email from localhost
        String host = fc4Repository.getNotificationSenderHost();

        final String userName = fc4Repository.getNotificationSenderUser();
        final String userPass = fc4Repository.getNotificationSenderPassword();
        String port = fc4Repository.getNotificationSenderPort();
        String auth=fc4Repository.getNotificationSenderAuthentication();
        String sslEnabled=fc4Repository.getNotificationSenderSSLEnabled();

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.starttls.enable", sslEnabled);


        Authenticator authenticator = new Authenticator ()
        {
            public PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(userName, userPass);
            }
        };
        //properties.setProperty("mail.user", userName);
        //properties.setProperty("mail.password", userPass);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, authenticator);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         for(int i=0;i<to.length;i++)
         {
             message.addRecipient(Message.RecipientType.TO,
                  new InternetAddress(to[i]));
         }

         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Create the message part for body
         MimeBodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         switch(type)
         {
             case TYPE_HTML:
                messageBodyPart.setContent(body, "text/html; charset=UTF-8");
                messageBodyPart.setHeader("Content-Type","text/html; charset=\"utf-8\"");
                messageBodyPart.getContentID();
                messageBodyPart.getContent();
                //multipart.addBodyPart(pass);
                break;
             default:
                messageBodyPart.setText(body, "UTF-8");
                messageBodyPart.setHeader("Content-Type","text/plain; charset=\"utf-8\"");
                break;
         }

        messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");

        //messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         for(int i=0;attachments!=null && i<attachments.length;i++)
         {
             // Part two is attachment
             messageBodyPart = new MimeBodyPart();
             DataSource source = new FileDataSource(attachments[i]);
             messageBodyPart.setDataHandler(new DataHandler(source));
             messageBodyPart.setFileName(attachments[i]);
             multipart.addBodyPart(messageBodyPart);
         }

         // Send the complete message parts
         message.setContent(multipart, "charset=UTF-8");

         // Set Subject: header field
         message.setSubject(subject, "UTF-8");

         // Now set the actual message if there was not attatchments
         //message.setText(body);

         // Send message
         Transport.send(message);
      }
      catch (MessagingException ex)
      {
         throw ex;
      }
   }

    public static String loadTemplate(int templateCode) throws Exception
    {
        try 
        {
            String templateFile = null;
            switch (templateCode)
            {
                
                case USER_MAX_FAIL_ATTEMPT_REACHED:
                    templateFile = "userdisabled.html";
                    break;
                    
                case ADMIN_MAX_FAIL_ATTEMPT_REACHED:
                    templateFile = "adminpassword.html";  
                    break;
                    
                    
                case USER_REMINDER_PASSWORD:
                    templateFile = "userpassword.html";
                    break;
                case NOTIFICATIONS_CORRECTS_ERRORS:
                    templateFile = "notificationerrorscorrects.html";
                    break;
            }
            if (templateFile == null)
            {
                throw new ConfigurationMissingException("Tenmplate para código " + templateCode + " no ha sido encontrado");
            }
            templateFile=rootPath+"/"+templateFile;
            return FileUtils.readFileToString(new File(templateFile));
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

}
