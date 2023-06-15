package Mailer;

import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import PrepareTransactionTable.PrepareTable;

public class PrepareMail {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void sendMail() {
		System.out.println("Preparing to send email");
		Properties properties = new Properties();
		logger.info("Preparing to send email");

		
		try {
		String generatedTable = PrepareTable.getTable();
		
		properties.setProperty("mail.smtp.host", "smtp.nwc.com.sa");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.socketFactory.port", "587");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.user", "username@google.com");
		properties.setProperty("mail.smtp.password", "password");
		
		
		// Create a session with account credentials
		System.out.println("Creating a session with account credentials");
		logger.info("Creating a session with account credentials");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.password"));
			}
		});
		
		System.out.println("successfully Created a session with account credentials");
		logger.info("successfully Created a session with account credentials");
		
		//Recipients
		InternetAddress[] toList = InternetAddress.parse("username1@google.com");
		InternetAddress[] ccList = InternetAddress.parse("username3@google.com,username4@google.com");
		
		

		// Prepare email message
		Message message = prepareMessage(generatedTable, session, properties.getProperty("mail.smtp.user"), toList,ccList);

		// Send mail
		
		System.out.println("Sending mail");
		logger.info("Sending mail");
		
		Transport.send(message);
		}catch (MessagingException e) {
			System.out.println("Error while connecting to mail server");
			logger.info("Error while connecting to mail server");
			e.printStackTrace();
			
		}
		catch (Exception e) {
			System.out.println("Error while preparing mail");
			logger.info("Error while preparing mail");
			e.printStackTrace();
		}
		System.out.println("Mail sent successfully");
		logger.info("Mail sent successfully");
	}

	private static Message prepareMessage(String table, Session session, String myAccountEmail, InternetAddress[] toList, InternetAddress[] ccList) {
		Message message = null;
		try {
			
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipients(Message.RecipientType.TO, toList);
			message.addRecipients(Message.RecipientType.CC, ccList);
			message.setSubject("Transaction Table for " +LocalDate.parse(LocalDate.now().toString()).minusDays(1).toString());
			message.setContent(table, "text/html");
			
		} catch (Exception ex) {
			System.out.println("Error while preparing table for mail");
			logger.info("Error while preparing table for mail");
			ex.printStackTrace();
		}
		return message;
	}
}

