package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import dbserver.DbConnection;
import sftpserver.ReconFilesReader;

public class Compare {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static ArrayList<ArrayList<String[]>> getAccountIdsForMailer() {
		Connection connection = null;
		Statement statement = null;

		ArrayList<String> accountIdsAndAmountInDBServer = new ArrayList<String>();
		ArrayList<String> accountIdsAndAmountFromFile = new ArrayList<String>();

		ArrayList<String> accountIdsInDBServer = new ArrayList<String>();
		ArrayList<String> ORDER_TRANSACTION_IDsInDBServer = new ArrayList<String>();
		ArrayList<String> AmountsInDBServer = new ArrayList<String>();
		ArrayList<String> created_datesInDBServer = new ArrayList<String>();

		ArrayList<ArrayList<String>> dataFromFile = null;
		ArrayList<String> accountIdsFromFile = new ArrayList<String>();
		ArrayList<String> AmountsFromFile = new ArrayList<String>();
		ArrayList<String> TransactionNosFromFile = new ArrayList<String>();
		ArrayList<String> PaymentDtsFromFile = new ArrayList<String>();

		ArrayList<String> intersection;
		ArrayList<String> copyOfAccountIdsInDBServer = new ArrayList<String>();
		ArrayList<String> copyOfAccountIdsFromFile = new ArrayList<String>();

		ArrayList<String[]> accountIdsNotInDBServerButInFile = new ArrayList<String[]>();
		ArrayList<String[]> accountIdsNotInFileButInDBServer = new ArrayList<String[]>();

		ArrayList<ArrayList<String[]>> result = new ArrayList<ArrayList<String[]>>();

		try {
			
			//Getting data from downloaded sftp file
			dataFromFile = ReconFilesReader.getAccountIds();
			
			connection = DbConnection.getDBConnection();
			statement = connection.createStatement();

			System.out.println("Retrieving data from DB server");
			logger.info("Getting data from DB");

			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			pstmt.executeUpdate();
			
			String from = LocalDate.now().minusDays(3).toString();
			String to = LocalDate.now().minusDays(2).toString();
			String runFromTO = "'" + from + " 00:00:00' and '" + to + " 00:00:30'";
			
			//runFromTO = "'2022-11-29 00:00:00' and '2022-11-30 00:00:30'";
			
			statement = connection.createStatement();
			String sql = "select ORDER_TRANSACTION_ID,Customer_account_number,Amount,created_date from pgprd.NWC_ORDER_TRANSACTION_ITEM where ORDER_TRANSACTION_ID \r\n"
					+ "in(select ORDER_TRANSACTION_ID from pgprd.nwc_order_transaction where created_date \r\n"
					+ "between "+runFromTO+" and order_trn_state_id = '3446' and PAYMENT_PROCESSOR_ID='3563' ) \r\n"
					+ "order by created_date asc";

			System.out.println("running query with date "+runFromTO);
			logger.info("running query with date "+runFromTO);
			
			ResultSet rs1 = statement.executeQuery(sql);
			
			System.out.println("reading data from db");
			logger.info("reading data from db");
			
			while (rs1.next()) {

				String Customer_account_number = rs1.getString("Customer_account_number");
				String ORDER_TRANSACTION_ID = rs1.getString("ORDER_TRANSACTION_ID");
				String Amount = rs1.getString("Amount");
				String created_date = rs1.getString("created_date");

				accountIdsInDBServer.add(Customer_account_number);
				ORDER_TRANSACTION_IDsInDBServer.add(ORDER_TRANSACTION_ID);
				AmountsInDBServer.add(Amount);
				created_datesInDBServer.add(created_date);
				accountIdsAndAmountInDBServer.add(Customer_account_number + "-" + Double.valueOf(Amount));
				//System.out.println(Customer_account_number);
			}


			accountIdsFromFile.addAll(dataFromFile.get(0));
			AmountsFromFile.addAll(dataFromFile.get(1));
			TransactionNosFromFile.addAll(dataFromFile.get(2));
			PaymentDtsFromFile.addAll(dataFromFile.get(3));

			int sizeOfFile = dataFromFile.get(0).size();
			for (int i = 0; i < sizeOfFile; i++) {
				accountIdsAndAmountFromFile
						.add(dataFromFile.get(0).get(i) + "-" + Double.valueOf(dataFromFile.get(1).get(i)));
			}

			copyOfAccountIdsInDBServer.addAll(accountIdsAndAmountInDBServer);
			copyOfAccountIdsFromFile.addAll(accountIdsAndAmountFromFile);
			
			System.out.println("Comparing data from file and db");
			logger.info("Comparing data from file and db");
			
			//Comparing data from file and db
			intersection = intersection(copyOfAccountIdsInDBServer, copyOfAccountIdsFromFile);

			copyOfAccountIdsInDBServer.removeAll(intersection);
			copyOfAccountIdsFromFile.removeAll(intersection);

			for (String id : copyOfAccountIdsFromFile) {
				int index = accountIdsAndAmountFromFile.indexOf(id);
				String[] arr = new String[4];
				arr[0] = accountIdsFromFile.get(index);
				arr[1] = AmountsFromFile.get(index);
				arr[2] = TransactionNosFromFile.get(index);
				arr[3] = PaymentDtsFromFile.get(index);
				accountIdsNotInDBServerButInFile.add(arr);
			}

			for (String id : copyOfAccountIdsInDBServer) {
				int index = accountIdsAndAmountInDBServer.indexOf(id);
				String[] arr = new String[4];
				arr[0] = accountIdsInDBServer.get(index);
				arr[1] = AmountsInDBServer.get(index);
				arr[2] = ORDER_TRANSACTION_IDsInDBServer.get(index);
				arr[3] = created_datesInDBServer.get(index);
				accountIdsNotInFileButInDBServer.add(arr);
			}

		} catch (SQLException e) {
			System.out.println("error while reading data from db");
			logger.info("error while reading data from db");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println(
				"Total number of Ids not in DBServer but in File are - " + accountIdsNotInDBServerButInFile.size());
		System.out.println(
				"Total number of Ids not in File but in DBServer are - " + accountIdsNotInFileButInDBServer.size());

		result.add(accountIdsNotInDBServerButInFile);
		result.add(accountIdsNotInFileButInDBServer);

		ArrayList<String[]> resourceSize = new ArrayList<String[]>();
		resourceSize.add(new String[] { accountIdsInDBServer.size() + "",accountIdsFromFile.size() + "" });

		result.add(resourceSize);
		
		System.out.println("publishing data to mailer");
		logger.info("publishing data to mailer");
		
		return result;

	}
	
	public static ArrayList<String> intersection(ArrayList<String> list1,ArrayList<String> list2) {
		ArrayList<String> list =new ArrayList<String>();

        for (String t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
}
