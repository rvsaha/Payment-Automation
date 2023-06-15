package sftpserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ReconFilesReader {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static ArrayList<ArrayList<String>> getAccountIds() {
		// Download Latest File From SFTP Server
		String filenames[] = GetLatestSFTPFiles.getLatestFileFromSFTPServer();
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		String filename1 = "E:\\Rishav\\Jars\\Payfort_missing_payments_automation\\Payfort_recon_files\\"+filenames[0];
		String filename2 = "E:\\Rishav\\Jars\\Payfort_missing_payments_automation\\Payfort_recon_files\\"+filenames[1];
		
		
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		String line = "";
		String Id = "";
		String Amount = "";
		String TransactionNo = "";
		String PaymentDt = "";
		String date = LocalDate.now().minusDays(3).toString();
		//date = "2022-11-29";
		
		System.out.println("Reading Data from recon files for Payment date " + date);
		logger.info("Reading Data from recon files for Payment date " + date);
		ArrayList<String> accountIds = new ArrayList<String>();
		ArrayList<String> Amounts = new ArrayList<String>();
		ArrayList<String> TransactionNos = new ArrayList<String>();
		ArrayList<String> PaymentDts = new ArrayList<String>();
		try {
			System.out.println("Reading Data from latest PF recon file");
			logger.info("Reading Data from latest PF recon file");
			br1 = new BufferedReader(new FileReader(filename1));
			br1.readLine();
			while ((line = br1.readLine()) != null) {
				String arr[] = line.split(" ; ");
				Id = arr[0];
				Amount = arr[1];
				TransactionNo = arr[2];
				PaymentDt = arr[3];
				if (PaymentDt.equals(date)) {
					accountIds.add(Id);
					Amounts.add(Amount);
					TransactionNos.add(TransactionNo);
					PaymentDts.add(PaymentDt);
				}
			}
			System.out.println("Reading Data from next to latest PF recon file");
			logger.info("Reading Data from next to latest PF recon file");
			br2 = new BufferedReader(new FileReader(filename2));
			br2.readLine();
			while ((line = br2.readLine()) != null) {
				String arr2[] = line.split(" ; ");
				Id = arr2[0];
				Amount = arr2[1];
				TransactionNo = arr2[2];
				PaymentDt = arr2[3];
				if (PaymentDt.equals(date)) {
					accountIds.add(Id);
					Amounts.add(Amount);
					TransactionNos.add(TransactionNo);
					PaymentDts.add(PaymentDt);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Please check for the presence of downloaded sftp files in the path specified.");
			logger.severe("Please check for the presence of downloaded sftp files in the path specified.");
			e.printStackTrace();
			System.err.println("Stopping Pf Missing Payments");
			logger.severe("Stopping Pf Missing Payments");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to read the downloaded sftp files.");
			logger.severe("Unable to read the downloaded sftp files.");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("check downloaded sftp files info.");
			logger.severe("check downloaded sftp files info.");
			e.printStackTrace();

		} finally {
			try {
				br1.close();
				br2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		result.add(accountIds);
		result.add(Amounts);
		result.add(TransactionNos);
		result.add(PaymentDts);
		return result;
	}
}