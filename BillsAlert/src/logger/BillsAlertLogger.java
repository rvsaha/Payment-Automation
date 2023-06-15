package logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BillsAlertLogger {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static Logger smpLogger() {

		FileHandler fh;

		try {

			fh = new FileHandler("E:\\Rishav\\Jars\\Bills_Alert_Automation\\Bills_Alert_Automation_Logs\\"+LocalDate.now().toString()+"_log.log",true);
			logger.addHandler(fh);
			fh.setLevel(Level.ALL);
			fh.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return record.getLevel() + " " + logTime.format(cal.getTime()) + " - "
							+ record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
									record.getSourceClassName().length())
							+ "." + record.getSourceMethodName() + "() : " + record.getMessage() + "\n";
				}
			});
			
			logger.info("======================================================================");

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe("Logging file Error");
			System.err.println("Logging file Error");
		}
		return logger;
	}
}
