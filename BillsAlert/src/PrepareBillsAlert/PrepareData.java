package PrepareBillsAlert;

import java.util.logging.Logger;

import DBOperations.FetchData;

public class PrepareData {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static String getDataForMailer() {

		String[] dbResult = FetchData.getData();
		String html = "";

		System.out.println("generating output");
		logger.info("generating output");
		
		String data ="";
		
		for(int i=0;i<3;i++) {
			if(!dbResult[i].isEmpty())
				data += "<br>"+dbResult[i];
		}

		html = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<body>\r\n" + "\r\n" + "<b>There are no Bills received from Sadad to EPG for the last 30 mins</b><br>"
				+"<b>("+dbResult[3]+")</b><br>"
				+data
				+"</body>\r\n" + "</html>\r\n";
		System.out.println("publishing data to mailer");
		logger.info("publishing data to mailer");

		return html;
	}
}
