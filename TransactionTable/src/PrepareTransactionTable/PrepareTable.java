package PrepareTransactionTable;

import java.util.ArrayList;
import java.util.logging.Logger;

import DBOperations.FetchData;

public class PrepareTable {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static String  getTable() {
		
		
		ArrayList<String> dbResult= FetchData.getData();
		
		System.out.println("generating table");
		logger.info("generating table");
		
		String table = "<table>\r\n"
				+ "	<tr>\r\n"
				+ "		<td class=\"right sp\">"+dbResult.get(24)+"</td>\r\n"
				+ "		<td class=\"right\"></td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td></td>\r\n"
				+ "		<td class=\"bol\">SADAD</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td class=\"right bol\">Bills</td>\r\n"
				+ "		<td></td>\r\n"
				+ "		<td class=\"bol\"><pre>    Total    </pre></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Total Bills Uploaded</td>\r\n"
				+ "		<td>"+dbResult.get(0)+"</td>\r\n"
				+ "		<td>"+dbResult.get(0)+"</td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Total Bills Acknowledged/Available</td>\r\n"
				+ "		<td>"+dbResult.get(1)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Total Bills to be Acknowledged/Under-Confirmation</td>\r\n"
				+ "		<td>"+dbResult.get(2)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Total Bills Failed</td>\r\n"
				+ "		<td>"+dbResult.get(3)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td></td>\r\n"
				+ "		<td>Node-1 : "+dbResult.get(4)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td class=\"top\">Total Bill Confirmations Notified to CC&B</td>\r\n"
				+ "		<td>Node-2 : "+dbResult.get(5)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "		<td>Node-3 : "+dbResult.get(6)+"</td>\r\n"
				+ "		<td class=\"top\">"+String.valueOf(Integer.valueOf(dbResult.get(4))+Integer.valueOf(dbResult.get(5))+Integer.valueOf(dbResult.get(6)))+"</td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Total Bills Confirmations to be Notified to CC&B</td>\r\n"
				+ "		<td>"+dbResult.get(7)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Bills in Initialization Status (Transaction)</td>\r\n"
				+ "		<td>"+dbResult.get(8)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Bills in Under confirmation Status (Transaction)</td>\r\n"
				+ "		<td>"+dbResult.get(9)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Bills in Initialization Status (Transaction Items)</td>\r\n"
				+ "		<td>"+dbResult.get(10)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Bills in Under confirmation Status (Transaction Items)</td>\r\n"
				+ "		<td>"+dbResult.get(11)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Older Bills Confirmations to be Notified to CC&B</td>\r\n"
				+ "		<td>"+dbResult.get(12)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td class=\"right sp\">Payments</td>\r\n"
				+ "		<td class=\"right\"></td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>SADAD Payments</td>\r\n"
				+ "		<td>"+dbResult.get(13)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>PAYFORT Payment</td>\r\n"
				+ "		<td>"+dbResult.get(14)+"</td>\r\n"
				+ "		<td class=\"top\">"+String.valueOf(Integer.valueOf(dbResult.get(13))+Integer.valueOf(dbResult.get(14)))+"</td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Older Payment Pending Notification</td>\r\n"
				+ "		<td>"+dbResult.get(15)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				//
				+ "	<tr>\r\n"
				+ "		<td class=\"right sp\">Pending Bills</td>\r\n"
				+ "		<td class=\"right\"></td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Bills in Node 1</td>\r\n"
				+ "		<td>"+dbResult.get(16)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Bills in Node 2</td>\r\n"
				+ "		<td>"+dbResult.get(17)+"</td>\r\n"
				+ "		<td class=\"top\">"+String.valueOf(Integer.valueOf(dbResult.get(16))+Integer.valueOf(dbResult.get(17))+Integer.valueOf(dbResult.get(18))+Integer.valueOf(dbResult.get(19)))+"</td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Bills in Node 3</td>\r\n"
				+ "		<td>"+dbResult.get(18)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Bills Processed By NULL</td>\r\n"
				+ "		<td>"+dbResult.get(19)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				//
				+ "	<tr>\r\n"
				+ "		<td class=\"right sp\" >Pending Payments</td>\r\n"
				+ "		<td class=\"right\"></td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Payments in Node 1</td>\r\n"
				+ "		<td>"+dbResult.get(20)+"</td>\r\n"
				+ "		<td></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Payments in Node 2</td>\r\n"
				+ "		<td>"+dbResult.get(21)+"</td>\r\n"
				+ "		<td class=\"top\">"+String.valueOf(Integer.valueOf(dbResult.get(20))+Integer.valueOf(dbResult.get(21))+Integer.valueOf(dbResult.get(22))+Integer.valueOf(dbResult.get(23)))+"</td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Payments in Node 3</td>\r\n"
				+ "		<td>"+dbResult.get(22)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "	<tr>\r\n"
				+ "		<td>Pending Payments Processed By NULL</td>\r\n"
				+ "		<td>"+dbResult.get(23)+"</td>\r\n"
				+ "		<td class=\"top\"></td>\r\n"
				+ "	</tr>\r\n"
				+ "</table>";
		
		String html = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<style>\r\n"
				+ " table,th,tr,td,tbody{border: 1px solid #000 ;text-align: center; border-collapse: collapse}\r\n"
				+".top{border-top:hidden}"
				+".right{border-right:hidden}"
				+".sp{padding-left: 185px;font-weight: bold;}"
				+".bol{font-weight: bold;}"
				+ "</style>\r\n"
				+ "<body>\r\n"
				+ "\r\n"
				+table
				+ "</body>\r\n"
				+ "</html>\r\n"
				+ "\r\n"
				+ "";
		System.out.println("publishing table to mailer");
		logger.info("publishing table to mailer");
		return html;
	}
}
