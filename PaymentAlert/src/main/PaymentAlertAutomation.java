package main;

import java.util.logging.Logger;

import Mailer.PrepareMail;
import logger.PaymentAlertLogger;

public class PaymentAlertAutomation {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void main(String[] args) {
		try {
			PaymentAlertLogger.smpLogger();
			
			System.out.println("Running Payment Alert automation");
			logger.info("Running Payment Alert automation");
			
			PrepareMail.sendMail();
			
		} catch (Exception e) {
			logger.severe("Error in main");
			e.printStackTrace();
		}
	}
}
