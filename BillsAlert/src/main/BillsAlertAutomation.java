package main;

import java.util.logging.Logger;

import Mailer.PrepareMail;
import logger.BillsAlertLogger;

public class BillsAlertAutomation {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void main(String[] args) {
		try {
			BillsAlertLogger.smpLogger();
			
			System.out.println("Running Bills Alert automation");
			logger.info("Running Bills Alert automation");
			
			PrepareMail.sendMail();
			
		} catch (Exception e) {
			logger.severe("Error in main");
			e.printStackTrace();
		}
	}
}
