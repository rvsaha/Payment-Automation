package dbserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbConnection {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static Connection getDBConnection() {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@hostname:port/PGPRD", "username",
					"password");

			if (conn != null) {
				System.out.println("Connected to the database!");
				logger.info("Successfully connected to the database!");
			} else {
				System.out.println("Failed to make connection to database!");
				logger.severe("Failed to make connection to database!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			logger.severe("Error while connecting to DB");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
}
