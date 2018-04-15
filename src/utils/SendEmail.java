/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Timothy
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * explicitly enable "less secure apps" in my gmail settings: google.com/settings/security/lesssecureapps. Once "less secure apps" was enabled, the code worked
 * https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail
 */
public final class SendEmail {
    private SendEmail() {
        
    }

    public static void sendFromGMail(String from, String password, String[] recipient, String emailSubject, String emailBody) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[recipient.length];

            // To get the array of addresses
            for( int i = 0; i < recipient.length; i++ ) {
                toAddress[i] = new InternetAddress(recipient[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(emailSubject);
            message.setText(emailBody);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
