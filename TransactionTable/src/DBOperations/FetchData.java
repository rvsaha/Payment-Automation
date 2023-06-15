package DBOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FetchData {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static ArrayList<String> getData() {
		
		Connection connection = null;
		Statement statement = null;
		ArrayList<String> output = new ArrayList<String>();
		int result;
		
		String from = LocalDate.parse(LocalDate.now().toString()).minusDays(1).toString();
		String to = LocalDate.now().toString().toString();
		String runFrom_to = "'"+from+" 00:00:00' and '"+to+" 00:00:00'";
		String runTill = "'"+LocalDate.now().toString().toString()+" 00:00:00'";
		
		
		//from = "2022-02-02";
		//runFrom_to = "'2022-10-19 00:00:00' and '2022-10-20 00:00:00'";
		//runTill = "'2022-10-20 00:00:00'";
		
		try {
			connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@hostname:port/PGPRD", "username", "password");

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
			
			System.out.println("Running queries with dates "+runFrom_to);
			logger.info("Running queries with dates "+runFrom_to);
			
			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			pstmt.executeUpdate();
			
			String TotalBillsUploaded_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION where created_date between "+runFrom_to+"  \r\n"
					+ "and PAYMENT_PROCESSOR_ID='3564' and ORDER_TRN_STATE_ID in ('3601','3598','3599')";
			System.out.println("Running query 1");
			logger.info("Running query 1");
			ResultSet rs1 = statement.executeQuery(TotalBillsUploaded_sql);
			rs1.next();
			result = rs1.getInt(1);
			String TotalBillsUploaded = result+"";
			
			rs1.close();
			
			String TotalBillsAcknowledgedOrAvailable_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION where created_date between "+runFrom_to+"\r\n"
					+ " and PAYMENT_PROCESSOR_ID='3564' and ORDER_TRN_STATE_ID in ('3599')";
			System.out.println("Running query 2");
			logger.info("Running query 2");
			rs1 = statement.executeQuery(TotalBillsAcknowledgedOrAvailable_sql);
			rs1.next();
			result = rs1.getInt(1);
			String TotalBillsAcknowledgedOrAvailable = result+"";
			rs1.close();
			
			String TotalBillstobeAcknowledgedorUnderConfirmation_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION where created_date between "+runFrom_to+"\r\n"
					+ " and  PAYMENT_PROCESSOR_ID='3564' and ORDER_TRN_STATE_ID in ('3598')";
			System.out.println("Running query 3");
			logger.info("Running query 3");
			rs1 = statement.executeQuery(TotalBillstobeAcknowledgedorUnderConfirmation_sql);
			rs1.next();
			result = rs1.getInt(1);
			String TotalBillstobeAcknowledgedorUnderConfirmation = result+"";
			rs1.close();
			
			String TotalBillsFailed_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION where created_date between "+runFrom_to+"\r\n"
					+ " and PAYMENT_PROCESSOR_ID='3564' and ORDER_TRN_STATE_ID in ('3602')";
			System.out.println("Running query 4");
			logger.info("Running query 4");
			rs1 = statement.executeQuery(TotalBillsFailed_sql);
			rs1.next();
			result = rs1.getInt(1);
			String TotalBillsFailed = result+"";
			rs1.close();
			
			String Node1_sql = "select count(1) from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where Biller_notified = 'Y' and\r\n"
					+ "ORDER_TRANSACTION_ID in (select ORDER_TRANSACTION_ID from pgprd.nwc_ORDER_TRANSACTION where created_date between \r\n"
					+ runFrom_to
					+ "and ORDER_TRN_STATE_ID = '3599' and processed_by = 'Node1' and PAYMENT_PROCESSOR_ID='3564')";
			System.out.println("Running query 5");
			logger.info("Running query 5");
			rs1 = statement.executeQuery(Node1_sql);
			rs1.next();
			result = rs1.getInt(1);
			String Node1 = result+"";
			rs1.close();
			
			String Node2_sql = "select count(1) from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where Biller_notified = 'Y' and\r\n"
					+ "ORDER_TRANSACTION_ID in (select ORDER_TRANSACTION_ID from pgprd.nwc_ORDER_TRANSACTION where created_date between \r\n"
					+runFrom_to
					+ "and ORDER_TRN_STATE_ID = '3599' and processed_by = 'Node2' and PAYMENT_PROCESSOR_ID='3564')";
			System.out.println("Running query 6");
			logger.info("Running query 6");
			rs1 = statement.executeQuery(Node2_sql);
			rs1.next();
			result = rs1.getInt(1);
			String Node2 = result+"";
			rs1.close();
			
			String Node3_sql = "select count(1) from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where Biller_notified = 'Y' and\r\n"
					+ "ORDER_TRANSACTION_ID in (select ORDER_TRANSACTION_ID from pgprd.nwc_ORDER_TRANSACTION where created_date between \r\n"
					+runFrom_to
					+ "and ORDER_TRN_STATE_ID = '3599' and processed_by = 'Node3' and PAYMENT_PROCESSOR_ID='3564')";
			System.out.println("Running query 7");
			logger.info("Running query 7");
			rs1 = statement.executeQuery(Node3_sql);
			rs1.next();
			result = rs1.getInt(1);
			String Node3 = result+"";
			rs1.close();
			
			String TotalBillsConfirmationstobeNotifiedtoCCB_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between \r\n"
					+runFrom_to
					+ " and order_trn_state_id = '3599' and PAYMENT_PROCESSOR_ID='3564' and order_transaction_id\r\n"
					+ "in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where Biller_notified = 'N' )";
			System.out.println("Running query 8");
			logger.info("Running query 8");
			rs1 = statement.executeQuery(TotalBillsConfirmationstobeNotifiedtoCCB_sql);
			rs1.next();
			result = rs1.getInt(1);
			String TotalBillsConfirmationstobeNotifiedtoCCB = result+"";
			rs1.close();
			
			String BillsinInitializationStatusTransaction_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION where created_date < "+runTill+"\r\n"
					+ "and PAYMENT_PROCESSOR_ID='3564' and ORDER_TRN_STATE_ID in ('3601')";
			System.out.println("Running query 9");
			logger.info("Running query 9");
			rs1 = statement.executeQuery(BillsinInitializationStatusTransaction_sql);
			rs1.next();
			result = rs1.getInt(1);
			String BillsinInitializationStatusTransaction = result+"";
			rs1.close();
			
			String BillsinUnderconfirmationStatusTransaction_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION where created_date < "+runTill+"\r\n"
					+ "and PAYMENT_PROCESSOR_ID='3564' and ORDER_TRN_STATE_ID in ('3598')";
			System.out.println("Running query 10");
			logger.info("Running query 10");
			rs1 = statement.executeQuery(BillsinUnderconfirmationStatusTransaction_sql);
			rs1.next();
			result = rs1.getInt(1);
			String BillsinUnderconfirmationStatusTransaction = result+"";
			rs1.close();
			
			String BillsinInitializationStatusTransactionItems_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION_item where created_date < "+runTill+"\r\n"
					+ "and ORDER_TRN_item_STATE_ID in ('3601')";
			System.out.println("Running query 11");
			logger.info("Running query 11");
			rs1 = statement.executeQuery(BillsinInitializationStatusTransactionItems_sql);
			rs1.next();
			result = rs1.getInt(1);
			String BillsinInitializationStatusTransactionItems = result+"";
			rs1.close();
			
			String BillsinUnderconfirmationStatusTransactionItems_sql = "select count(1) from pgprd.nwc_ORDER_TRANSACTION_item where created_date < "+runTill+"\r\n"
					+ "and  ORDER_TRN_item_STATE_ID in ('3598')";
			System.out.println("Running query 12");
			logger.info("Running query 12");
			rs1 = statement.executeQuery(BillsinUnderconfirmationStatusTransactionItems_sql);
			rs1.next();
			result = rs1.getInt(1);
			String BillsinUnderconfirmationStatusTransactionItems = result+"";
			rs1.close();
			
			String SADADPayment_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between "+runFrom_to+"\r\n"
					+ "and order_trn_state_id = '3446' and PAYMENT_PROCESSOR_ID='3564'";
			System.out.println("Running query 13");
			logger.info("Running query 13");
			rs1 = statement.executeQuery(SADADPayment_sql);
			rs1.next();
			result = rs1.getInt(1);
			String SADADPayment = result+"";
			rs1.close();
			
			String PAYFORTPayment_sql = "select count(*) from pgprd.nwc_order_transaction where created_date  between "+runFrom_to+"\r\n"
					+ "and order_trn_state_id = '3446' and PAYMENT_PROCESSOR_ID='3563'";
			System.out.println("Running query 14");
			logger.info("Running query 14");
			rs1 = statement.executeQuery(PAYFORTPayment_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PAYFORTPayment = result+"";
			rs1.close();
			
			String OlderPaymentPendingNotification_sql = "select count( distinct (a.order_transaction_id)) from pgprd.nwc_order_transaction_item a, pgprd.NWC_ORDER_TRANSACTION_NOTIFY b\r\n"
					+ "where a.order_transaction_id = b.order_transaction_id and a.order_transaction_id in (select order_transaction_id from\r\n"
					+ "pgprd.nwc_order_transaction where created_date < "+runTill+" and error_code_id is not null and order_trn_state_id = '3446' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where Biller_notified ='N'))\r\n"
					+ "order by a.created_date desc";
			System.out.println("Running query 15");
			logger.info("Running query 15");
			rs1 = statement.executeQuery(OlderPaymentPendingNotification_sql);
			rs1.next();
			result = rs1.getInt(1);
			String OlderPaymentPendingNotification = result+"";
			rs1.close();
			
			String OlderBillsConfirmationstobeNotifiedtoCCB_sql = "select count( distinct (a.order_transaction_id)) from pgprd.nwc_order_transaction_item a, pgprd.NWC_ORDER_TRANSACTION_NOTIFY b where a.order_transaction_id = b.order_transaction_id\r\n"
					+ "and a.order_transaction_id in (select order_transaction_id from pgprd.nwc_order_transaction\r\n"
					+ "where created_date between '2023-03-01 00:00:00' and "+runTill+"and error_code_id is not null and order_trn_state_id = '3599' and order_transaction_id in\r\n"
					+ "(select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where Biller_notified ='N'))\r\n"
					+ "order by a.created_date desc";
			System.out.println("Running query 16");
			logger.info("Running query 16");
			rs1 = statement.executeQuery(OlderBillsConfirmationstobeNotifiedtoCCB_sql);
			rs1.next();
			result = rs1.getInt(1);
			String OlderBillsConfirmationstobeNotifiedtoCCB = result+"";
			rs1.close();
			
			String PendingBillsInNode1_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-12-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3599' and PROCESSED_BY='Node1' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 17");
			logger.info("Running query 17");
			rs1 = statement.executeQuery(PendingBillsInNode1_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingBillsInNode1 = result+"";
			rs1.close();
			
			String PendingBillsInNode2_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-12-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3599' and PROCESSED_BY='Node2' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 18");
			logger.info("Running query 18");
			rs1 = statement.executeQuery(PendingBillsInNode2_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingBillsInNode2 = result+"";
			rs1.close();
			
			String PendingBillsInNode3_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-12-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3599' and PROCESSED_BY='Node3' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 19");
			logger.info("Running query 19");
			rs1 = statement.executeQuery(PendingBillsInNode3_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingBillsInNode3 = result+"";
			rs1.close();
			
			String PendingBillsInNodeNull_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-12-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3599' and PROCESSED_BY is null and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 20");
			logger.info("Running query 20");
			rs1 = statement.executeQuery(PendingBillsInNodeNull_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingBillsInNodeNull = result+"";
			rs1.close();
			
			String PendingPaymentsInNode1_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-01-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3446' and PROCESSED_BY='Node1' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 21");
			logger.info("Running query 21");
			rs1 = statement.executeQuery(PendingPaymentsInNode1_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingPaymentsInNode1 = result+"";
			rs1.close();
			
			String PendingPaymentsInNode2_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-01-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3446' and PROCESSED_BY='Node2' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 22");
			logger.info("Running query 22");
			rs1 = statement.executeQuery(PendingPaymentsInNode2_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingPaymentsInNode2 = result+"";
			rs1.close();
			
			String PendingPaymentsInNode3_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-01-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3446' and PROCESSED_BY='Node3' and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 23");
			logger.info("Running query 23");
			rs1 = statement.executeQuery(PendingPaymentsInNode3_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingPaymentsInNode3 = result+"";
			rs1.close();
			
			String PendingPaymentsInNodeNull_sql = "select count(*) from pgprd.nwc_order_transaction where created_date between '2022-01-01 00:00:00' and "+runTill+" and error_code_id is null and order_trn_state_id = '3446' and PROCESSED_BY is null and\r\n"
					+ "order_transaction_id in (select order_transaction_id from pgprd.NWC_ORDER_TRANSACTION_NOTIFY where BILLER_NOTIFIED = 'N')";
			System.out.println("Running query 24");
			logger.info("Running query 24");
			rs1 = statement.executeQuery(PendingPaymentsInNodeNull_sql);
			rs1.next();
			result = rs1.getInt(1);
			String PendingPaymentsInNodeNull = result+"";
			rs1.close();
			
			output.add(TotalBillsUploaded);
			output.add(TotalBillsAcknowledgedOrAvailable);
			output.add(TotalBillstobeAcknowledgedorUnderConfirmation);
			output.add(TotalBillsFailed);
			output.add(Node1);
			output.add(Node2);
			output.add(Node3);
			output.add(TotalBillsConfirmationstobeNotifiedtoCCB);
			output.add(BillsinInitializationStatusTransaction);
			output.add(BillsinUnderconfirmationStatusTransaction);
			output.add(BillsinInitializationStatusTransactionItems);
			output.add(BillsinUnderconfirmationStatusTransactionItems);
			output.add(OlderBillsConfirmationstobeNotifiedtoCCB);
			output.add(SADADPayment);
			output.add(PAYFORTPayment);
			output.add(OlderPaymentPendingNotification);
			output.add(PendingBillsInNode1);
			output.add(PendingBillsInNode2);
			output.add(PendingBillsInNode3);
			output.add(PendingBillsInNodeNull);
			output.add(PendingPaymentsInNode1);
			output.add(PendingPaymentsInNode2);
			output.add(PendingPaymentsInNode3);
			output.add(PendingPaymentsInNodeNull);
			output.add(from);
			
			

        } catch (SQLException e) {
        	System.out.println("error while reading data from db");
			logger.info("error while reading data from db");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		return output;
	}

	
}
