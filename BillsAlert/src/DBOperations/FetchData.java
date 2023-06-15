package DBOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class FetchData {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static String[] getData() {

		Connection connection = null;
		Statement statement = null;
		int result;
		String[] output  = {"","","",""};
		String check= "";

		String date = LocalDate.now().toString();
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		String minus30mins = " "+time.minusMinutes(30).format(formatter);
		String now = " "+time.format(formatter);
		
		String second = "'"+date+minus30mins+"' and '"+date+now+"'";
		//second = "'2022-02-24 07:30:04' and '2022-24-24 08:00:04'";
		System.out.println(second);
		
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@hostname:port/PGPRD", "username",
					"password");

			if (connection != null) {
				System.out.println("Connected to the database!");
				logger.info("Successfully connected to the database!");
			} else {
				System.out.println("Failed to make connection to database!");
				logger.severe("Failed to make connection to database!");
			}

			statement = connection.createStatement();

			System.out.println("Retrieving data from DB server");
			logger.info("Getting data from DB");

			System.out.println("Running queries from "+second);
			logger.info("Running queries from "+second);

			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			pstmt.executeUpdate();

			String SadadNode1_now_sql = "select count(*) from pgprd.nwc_ORDER_TRANSACTION where created_date \r\n"
					+ "between " + second + " and ORDER_TRN_STATE_ID = '3599' and processed_by = 'Node1' \r\n"
					+ "and PAYMENT_PROCESSOR_ID='3564'";
			System.out.println("Running query Sadad Node1");
			logger.info("Running query Sadad Node1");
			ResultSet rs1 = statement.executeQuery(SadadNode1_now_sql);
			rs1.next();
			result = rs1.getInt(1);
			int SadadNode1_now = result;
			
			rs1.close();

			String SadadNode2_now_sql = "select count(*) from pgprd.nwc_ORDER_TRANSACTION where created_date \r\n"
					+ "between " + second + " and ORDER_TRN_STATE_ID = '3599' and processed_by = 'Node2' \r\n"
					+ "and PAYMENT_PROCESSOR_ID='3564'";
			System.out.println("Running query Sadad Node2");
			logger.info("Running query Sadad Node2");
			rs1 = statement.executeQuery(SadadNode2_now_sql);
			rs1.next();
			result = rs1.getInt(1);
			int SadadNode2_now = result;
			
			rs1.close();

			String SadadNode3_now_sql = "select count(*) from pgprd.nwc_ORDER_TRANSACTION where created_date \r\n"
					+ "between " + second + " and ORDER_TRN_STATE_ID = '3599' and processed_by = 'Node3' \r\n"
					+ "and PAYMENT_PROCESSOR_ID='3564'";
			System.out.println("Running query Sadad Node3");
			logger.info("Running query Sadad Node3");
			rs1 = statement.executeQuery(SadadNode3_now_sql);
			rs1.next();
			result = rs1.getInt(1);
			int SadadNode3_now = result;
			
			rs1.close();

			if(SadadNode1_now==0) {
				check += "Sadad Node 1 did not receive any bills from "+second+"\n";

			}
			if(SadadNode2_now==0) {
				check += "Sadad Node 2 did not receive any bills from "+second+"\n";
			}
				
			if(SadadNode3_now==0) {
				check += "Sadad Node 3 did not receive any bills from "+second+"\n";
			}
			
			output[0] = "Sadad  Node1 - "+SadadNode1_now;
			output[1] = "Sadad  Node2 - "+SadadNode2_now;
			output[2] = "Sadad  Node3 - "+SadadNode3_now;
			output[3] = date+now+" to "+date+minus30mins;
			

		} catch (SQLException e) {
			System.out.println("error while reading data from db");
			logger.info("error while reading data from db");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			System.out.println("error in db");
			logger.info("error in db");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("error closing db");
				logger.info("error closing db");
				e.printStackTrace();
			}
		}
		
		if(check.isEmpty()) {
			System.out.println("Bills were received from Sadad to EPG");
			logger.info("Bills were received from Sadad to EPG");
			System.out.println("Stopping Bills Alert");
			logger.info("Stopping Bills Alert");
			System.exit(1);
		}
		
		return output;
	}

}
