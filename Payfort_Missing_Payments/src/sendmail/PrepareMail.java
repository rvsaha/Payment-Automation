package sendmail;

import java.time.LocalDate;
import java.util.ArrayList;
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

import utility.Compare;

public class PrepareMail {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void sendMail(){
		System.out.println("Preparing to send email");
		logger.info("Preparing to send email");
		try {
		ArrayList<ArrayList<String[]>> dataToBePublished = Compare.getAccountIdsForMailer();
		Properties properties = new Properties();

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
		InternetAddress[] ccList = InternetAddress.parse("username2@google.com,username3@google.com");
		
		
		// Prepare email message
		Message message = prepareMessage(dataToBePublished, session, properties.getProperty("mail.smtp.user"), toList, ccList);
		
		// Send mail
		System.out.println("Sending mail");
		logger.info("Sending mail");
		
		System.out.println("Mail sent successfully");
		logger.info("Mail sent successfully");
		
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
		
	}

	private static Message prepareMessage(ArrayList<ArrayList<String[]>> result, Session session, String myAccountEmail, InternetAddress[] toList, InternetAddress[] ccList) {
		Message message = null;
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipients(Message.RecipientType.TO, toList);
			message.addRecipients(Message.RecipientType.CC,ccList);
			
			String date = LocalDate.now().minusDays(3).toString();
			//date = "2022-11-29";
			message.setSubject("Payfort - Missing Payments - "+date);
			
			String tableData1 = "";
			for (int i = 0; i < result.get(0).size(); i++) {
				String arr[] = result.get(0).get(i);
				tableData1 += "<tr><td>" + arr[0] + "</td><td>" + arr[1] + "</td><td>" + arr[2] + "</td><td>" + arr[3] + "</td></tr>";
			}
			String htmlBody1 = "<table style=\"border: 1px solid #000 ;text-align: center; border-collapse: collapse\"> <tr> <th>Account Id</th><th>Amount</th><th>Transaction No</th><th>Payment Date</th></th> </tr> "
					+ tableData1 + " </table>";

			String tableData2 = "";
			for (int i = 0; i < result.get(1).size(); i++) {
				String arr[] = result.get(1).get(i);
				tableData2 += "<tr><td>" + arr[0] + "</td><td>" + arr[1] + "</td><td>" + arr[2] + "</td><td>" + arr[3] + "</td></tr>";
			}
			String htmlBody2 = "<table style=\"border: 1px solid #000 ;text-align: center; border-collapse: collapse\"> <tr> <th>Account Id</th><th>Amount</th><th>Order Transaction Id</th><th>Created Date</th></th> </tr> "
					+ tableData2 + " </table>";
			
			String []resourceSize = result.get(2).get(0);
			String n1 = "Total number of Account Ids in File are - "+resourceSize[1];
			String n2 = "Total number of Account Ids not in DBServer but in File are - "+result.get(0).size();
			String n3 = "Total number of Account Ids in DBServer are - "+resourceSize[0];
			String n4 = "Total number of Account Ids not in File but in DBServer are - "+result.get(1).size();
			String htmlbody ="<h3>"+n1+"</h3>"+"<h3>"+n2+"</h3>"+"<br>" + htmlBody1 + "<br>" +"<h3>"+n3+"</h3>"+"<h3>"+n4+"</h3>"+"<br>"+ htmlBody2;
			
			String html = "<!DOCTYPE html>\r\n"
					+ "<html>\r\n"
					+ "<style>\r\n"
					+ " table,th,tr,td,tbody{border: 1px solid #000 ;text-align: center; border-collapse: collapse}\r\n"
					+ " th{padding-left: 20px;padding-right: 20px;}\r\n"
					+ "</style>\r\n"
					+ "<body>\r\n"
					+ "\r\n"
					+htmlbody
					+ "</body>\r\n"
					+ "</html>\r\n"
					+ "\r\n"
					+ "";
			
			message.setContent(html, "text/html");
			
		} catch (Exception ex) {
			System.out.println("Error while preparing table for mail");
			logger.info("Error while preparing table for mail");
			ex.printStackTrace();
			
		}
		return message;
	}
}
