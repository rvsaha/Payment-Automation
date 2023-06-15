package main;

import java.util.logging.Logger;

import Mailer.PrepareMail;
import logger.TransactionTableLogger;

public class TransactionTableAutomation {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void main(String[] args) {
		try {
			TransactionTableLogger.smpLogger();
			
			System.out.println("Running transaction table automation");
			logger.info("Running transaction table automation");
			
			PrepareMail.sendMail();
			
		} catch (Exception e) {
			logger.severe("Error in main");
			e.printStackTrace();
		}
	}
}
