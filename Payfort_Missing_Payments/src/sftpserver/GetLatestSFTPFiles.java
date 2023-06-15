package sftpserver;

import java.util.Vector;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class GetLatestSFTPFiles {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static String[] getLatestFileFromSFTPServer() {
		// Create a new JSch session
		JSch jsch = new JSch();
		Session session = null;
		int currentLatestFileTimeStamp;
		int nextFileTimeStamp;
		int currentnextToLatestFileTimeStamp;
		LsEntry nextFile = null;
		LsEntry latestFile = null;
		LsEntry nexttolatestFile=null;

		try {
			// Connect to the remote server
			session = jsch.getSession("domain@hostname", "username", 1111);

            session.setConfig("StrictHostKeyChecking", "no");

            session.setPassword("password");

			System.out.println("connecting to SFTP Server");
			logger.info("connecting to SFTP Server");
			
			session.connect();
			
			System.out.println("successfully connected to SFTP Server");
			logger.info("successfully connected to SFTP Server");

			// Create a new SFTP channel
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd("/u02/reconciliation/ERP/Archived");
			
			System.out.println("Getting latest payfort recon files from SFTP server");
			logger.info("Getting latest payfort recon files from SFTP server");
			
			Vector filelist = sftpChannel.ls("/u02/reconciliation/ERP/Archived/PF*");
			
			// Get latest files
			latestFile = (LsEntry) filelist.firstElement();
			nexttolatestFile = (LsEntry) filelist.elementAt(2);
			currentLatestFileTimeStamp = latestFile.getAttrs().getMTime();
			currentnextToLatestFileTimeStamp = latestFile.getAttrs().getMTime();
			for (Object sftpFile : filelist) {
				nextFile = (ChannelSftp.LsEntry) sftpFile;
				nextFileTimeStamp = nextFile.getAttrs().getMTime();
				if (nextFileTimeStamp > currentLatestFileTimeStamp) {
					nexttolatestFile = latestFile;
					latestFile = nextFile;
				} else if (nextFileTimeStamp > currentnextToLatestFileTimeStamp
						&& nextFileTimeStamp < currentLatestFileTimeStamp) {
					nexttolatestFile = nextFile;
				}

			}

			sftpChannel.get(latestFile.getFilename(),
					"E:\\Rishav\\Jars\\Payfort_missing_payments_automation\\Payfort_recon_files\\"+latestFile.getFilename());
			logger.info("Downloaded " + latestFile.getFilename());
			System.out.println("Downloaded " + latestFile.getFilename());

			sftpChannel.get(nexttolatestFile.getFilename(),
					"E:\\Rishav\\Jars\\Payfort_missing_payments_automation\\Payfort_recon_files\\"+nexttolatestFile.getFilename());
			logger.info("Downloaded " + nexttolatestFile.getFilename());
			System.out.println("Downloaded " + nexttolatestFile.getFilename());

			sftpChannel.exit();
			session.disconnect();
		} catch (JSchException e) {
			// Handle exception
			logger.info("cannot connect to SFTP Server");
			e.printStackTrace();
			System.err.println("Stopping PF Missing Payments");
			logger.severe("Stopping PF Missing Payments");
			System.exit(1);
		} catch (SftpException e) {
			logger.info("cannot connect to SFTP Server");
			e.printStackTrace();
			System.err.println("Stopping PF Missing Payments");
			logger.severe("Stopping PF Missing Payments");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the SSH session
			if (session != null) {
				session.disconnect();
			}
		}
		return new String[] {latestFile.getFilename(),nexttolatestFile.getFilename()};
	}
}
