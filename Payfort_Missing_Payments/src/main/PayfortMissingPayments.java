package main;
import java.util.logging.Logger;

import logger.Payfort_Missing_Payments_Logger;
import sendmail.PrepareMail;

public class PayfortMissingPayments {

	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {

		Payfort_Missing_Payments_Logger.smpLogger();
		logger.info("Running Payfort Missing Payments");
		try {
			System.out.println("Running Payfort Missing Payments automation");
			PrepareMail.sendMail();
			} catch (Exception e) {
				e.printStackTrace();
				logger.severe("Error in main");
			}
	}
}
