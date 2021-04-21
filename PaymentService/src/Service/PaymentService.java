package Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import Security.Hashing;
import model.Payment;
import util.DBConnection;

public class PaymentService {

	/*****************************************Checking for database connectivity.***************************************/
	
	
	DBConnection dbConnect = new DBConnection();
	
	/********************************Inserting backer or buyer details to the database.*************************************/
	
	public String insertPayment(String paymentType, String bank, String paymentDate, String cardNo, 
			String nameOnCard, String cvv  ,int productID ,int consumerID , int conceptID, 
			String cardExpMonth , String cardExpYear 
			)
	{
		//variable declaration to capture output message and calculated product price
		String output = "";
		double totalBuyingAmt = 0.00;
		
		try
		{
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
		
			/********************************Detail validation************************************************************/
			
			/*********Invoke cardNumber validator from model class********************/
			Payment p = new Payment();
			
			int cardNumberCount = p.validateCardNumber(cardNo);
			
			int cvvValidation = p.cvvValidator(cardNo, cvv);
			
			if((paymentType.equals("Debit")) || (paymentType.equals("debit")) || (paymentType.equals("credit")) || (paymentType.equals("Credit"))  && (cardNumberCount == 16) && (cvvValidation == 1) )  {
				
			
			/******************************datahashing process********************************************************************/
			
			Hashing hs = new Hashing();
			
			String hcardNumber = hs.hashPassword(cardNo);
			String hCardHolderName = hs.hashPassword(nameOnCard);
			String hcvvNo = hs.hashPassword(cvv);
			
			
			
			
		/*******************Verify payment as backer or buyer payment.***********************/	
			if( productID == -1) {
				
				productID = -1;
			}
			else if(conceptID == -1) { /****************Logic to handle if its a buyer payment.***************/
				
				conceptID = -1;
				
				//contains implemented business logic to calculate total buying amount of buyer
				 CallableStatement  cs = con.prepareCall("{? = call insertProductAmount(?,?)}");	
				
				//setting parameter to function
				
				 cs.registerOutParameter(1, Types.DOUBLE);
				 cs.setInt(2,productID);
				 cs.setInt(3, consumerID);
			
				
				//call function
				 cs.execute();
				
				//obtained returned value of function(insertProductAmount())
			     totalBuyingAmt = cs.getDouble(1);
				
			    //checking for successful function execution
				 System.out.println("Product function called succesfully");
				
			}
			
		/*******************End of buyer or backer verification.***********************************/
			
			
		/*************Executing logic for Auto-generating payment id******************************************************/	
			
			//Preparing a CallableStatement to call the implemented function
            CallableStatement cstmt = con.prepareCall("{? = call getPaymentID()}");
            
            //Registering the out parameter of the function (return type)
            cstmt.registerOutParameter(1, Types.CHAR);
            
            //Executing the statement
            cstmt.execute();
            
            //obtaining returned value of function(getPaymentID())
            String PaymentCode = cstmt.getString(1);
            
            /*******Define total amount if payment is a backer payment(NOt a buyer payment)************************/
            if(totalBuyingAmt == 0) {
            	totalBuyingAmt = 0;
            }
            
            
			// create a prepared statement
			
			String query = " insert into gb_payments(`PaymentID`,`paymentCode`,`PaymentType`,`bank`,`paymentDate`,`cardNo`,`NameOnCard`,`cvv`,`Buyerpayment`,`ProductID`,`ConsumerID`,`ConceptID`,`cardExpMonth`,`cardExpYear`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			
				// binding values
			
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PaymentCode);
			preparedStmt.setString(3, paymentType);
			preparedStmt.setString(4, bank);
			preparedStmt.setString(5, paymentDate);
			preparedStmt.setString(6, hcardNumber);
			preparedStmt.setString(7, hCardHolderName);
			preparedStmt.setString(8, hcvvNo);
			preparedStmt.setDouble(9,totalBuyingAmt);
			preparedStmt.setInt(10, productID);
			preparedStmt.setInt(11, consumerID);
			preparedStmt.setInt(12, conceptID);
			preparedStmt.setString(13, cardExpMonth);
			preparedStmt.setString(14, cardExpYear);
			
			
			//execute the statement
			preparedStmt.execute();
			
			/***********Table for hash values*******************/
			
			insertcardNumberforkey(cardNo, hcardNumber);
			insertcardholderNameforkey(nameOnCard, hCardHolderName);
			insertCvvForKey(cvv,hcvvNo);
			
			//closing connection object
			con.close();
			
			output = " User payment Inserted successfully";
		}
		else {
		     output = "Please enter valid details";
		}
	}
	catch (Exception e)
	{
		output = "Error while inserting user payment";
		System.err.println(e.getMessage());
	}
		
		return output;
	}
	
	
	/*****************************************Method to read relevant payment details********************************/
	public String readPayments()
	{
		String output = "";
		
	try
		{
		Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			
				output = "<table border=‘1’><tr><th>Payment Type</th>"
				+"<th>Bank Name</th>"
				+ "<th>Payment Date</th>"
				+ "<th>Name on card</th>"
				+ "<th>BuyerPayment</th>"
				+ "<th>ConsumerID</th>"
				+ "<th>ConceptID</th>"
				+ "<th>ProductID</th>";
				
				
				String query = "select g.PaymentType, g.bank , g.paymentDate , hcn.nKey as NameOnCard, g.Buyerpayment, g.ConsumerID,g.ConceptID,g.ProductID from gb_payments g , hcardname hcn ,hcardno hno ,hcvv hv where g.NameOnCard = hcn.nValue AND g.cardNo =hno.nValue AND g.cvv=hv.nValue ";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				
				while (rs.next())
				{
					//String PaymentID = Integer.toString(rs.getInt("PaymentID"));
					String PaymentType = rs.getString("PaymentType");
					String BankName = rs.getString("bank");
					String paymentDate = rs.getString("paymentDate");
					//String cardNumber = rs.getNString("cardNo");
					String CardName= rs.getString("NameOnCard");
					//String cvv = rs.getString("cvv");
					double buyerAmt = rs.getDouble("Buyerpayment");
					String productID =  Integer.toString(rs.getInt("ProductID"));
					String consumerID =  Integer.toString(rs.getInt("ConsumerID"));
					String conceptID =  Integer.toString(rs.getInt("ConceptID"));
					//String cardExpMonth =  Integer.toString(rs.getInt("cardExpMonth"));
					//String cardExpYear = Integer.toString(rs.getInt("cardExpYear"));
					
					
					// Add into the html table
					
					output += "<tr><td>" + PaymentType + "</td>";
					output += "<td>" + BankName + "</td>";
					output += "<td>" + paymentDate + "</td>";
					output += "<td>" + CardName + "</td>";
					output += "<td>" + buyerAmt + "</td>";
					output += "<td>" + consumerID + "</td>";
					output += "<td>" + conceptID + "</td>";
					output += "<td>" + productID + "</td>";
					
					
						
					}
				
				con.close();
				
				
				// Complete the html table
					output += "</table>";
		}
		catch (Exception e)
		{
				output = "Error while reading the payments.";
				System.err.println(e.getMessage());
		}
	
		return output;
	}
	
	
	/*public String readPaymentsPath(String name)
	{
		String output = "";
		
	try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			
				
				String query = "select * from gb_payments where NameOnCard = '"+name+"'";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				
				while (rs.next())
				{
					String PaymentID = Integer.toString(rs.getInt("PaymentID"));
					String PaymentType = rs.getString("PaymentType");
					String BankName = rs.getString("bank");
					String paymentDate = rs.getString("paymentDate");
					String cardNumber = rs.getNString("cardNo");
					String CardName= rs.getString("NameOnCard");
					String cvv = rs.getString("cvv");
					double buyerAmt = rs.getDouble("Buyerpayment");
					String productID =  Integer.toString(rs.getInt("ProductID"));
					String consumerID =  Integer.toString(rs.getInt("ConsumerID"));
					String conceptID =  Integer.toString(rs.getInt("ConceptID"));
					String cardExpMonth =  Integer.toString(rs.getInt("cardExpMonth"));
					String cardExpYear = Integer.toString(rs.getInt("cardExpYear"));
					
					
					// Add into the html table
					
					output += "<tr><td>" + PaymentType + "</td>"+
					 "<td>" + BankName + "</td>"+
					"<td>" + paymentDate + "</td>"+
					 "<td>" + CardName + "</td>"+
					 "<td>" + buyerAmt + "</td>"+
					 "<td>" + consumerID + "</td>"+
					 "<td>" + conceptID + "</td>"+
					 "<td>" + productID + "</td>";
					
					
					output = "<Payment><concept>"+conceptID+"</concept></Payment>";
					
					}
				
				
				
				con.close();
				
				
				// Complete the html table
					
		}
		catch (Exception e)
		{
				output = "Error while reading the payments.";
				System.err.println(e.getMessage());
		}
	
		return output;
	}*/
	
	/**************************Method to handle payment status depending on pledegAmount summation**********************/
	public String updatePaymentStatus(int ConceptID)
	{
		String output = "";
		
		try
		{
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating."; 
			}
			
			//Preparing a CallableStatement to call a stored procedure containing business logic
			
			//contains  business logic implemented to update concept status as pledgeAmount sums up
			CallableStatement  cs = con.prepareCall("{call updateStatus(?)}");	
			
			//setting parameter to procedure
			cs.setInt(1, ConceptID);/*pass this name to postman*/
			
			//call procedure
			cs.execute();
			
			cs.close();
			
			//verify procedure execution success
			System.out.println("Stored procedure called successfully!");
		
			output = "Concept payment status Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the concept payment status.";
			System.err.println(e.getMessage());
		}
		
		return output;
		
		}
	
	/***************************Method to update user payment details****************************/
	
	public String updatePaymentDetails(String paymentID,String paymentType,String bank , String cardNo , String NameOnCard ,String cvv, String cardExpMonth ,String cardExpYear)
	{
		String output = "";
		
		try
		{
			
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			
			
			// create a prepared statement
			
		String query = "UPDATE gb_payments SET PaymentType=?,bank=?,cardNo=?,NameOnCard=?,cvv=?,cardExpMonth=?,cardExpYear=? WHERE PaymentID=?";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		//Hashing
		Hashing hs = new Hashing();
		
		String hcardNumber = hs.hashPassword(cardNo);
		String hCardHolderName = hs.hashPassword(NameOnCard);
		String hcvvNo = hs.hashPassword(cvv);
		
		// binding values
		
		preparedStmt.setString(1, paymentType);
		preparedStmt.setString(2, bank);
		preparedStmt.setString(3, hcardNumber);
		preparedStmt.setString(4, hCardHolderName);
		preparedStmt.setString(5, hcvvNo);
		preparedStmt.setString(6, cardExpMonth);
		preparedStmt.setString(7, cardExpYear);
		preparedStmt.setString(8, paymentID);
		
		/***********Table for hash values*******************/
		
		insertcardNumberforkey(cardNo, hcardNumber);
		insertcardholderNameforkey(NameOnCard, hCardHolderName);
		insertCvvForKey(cvv,hcvvNo);
		
		// execute the statement
		preparedStmt.execute();
		
		
		con.close();
		
		output = "Payment details Updated successfully";
		
		}
		catch (Exception e)
		{
			output = "Error while Updating the payment details.";
		
			System.err.println(e.getMessage());
		}
		
			return output;
		}
		
	
	
	
	/*Method to delete incomplete project backed funds*/
	public String deletePayment(String status) {
		
		String output = "";
		 
		
		Connection con = dbConnect.connect();
		
		String sql = "delete from gb_payments p where p.PaymentID > 0 AND p.ConceptID IN (select c.conceptID from concept c where c.status = '"+status+"')" ;
		
		try{
			
			PreparedStatement preparedStmt = con.prepareStatement(sql);

			preparedStmt.executeUpdate();
			
			output = "Payment Deleted Successfully!!";
		}
		catch (Exception e) {
			
			output = "Error while deleting payment";
			
			e.printStackTrace();
		}
		
		return output;
	}
	
	/********************methods to manage hashing tables**********************************************************/
	
	
    public int insertcardNumberforkey(String cardNo, String hcardNumber) throws SQLException {
		
		Connection con = dbConnect.connect();
		
		//Making Key Value pairs
		//Name
		String query1 = "INSERT INTO hCardNo(`id`, `nKey`, `nvalue`) VALUES(?,?,?)" ;
		PreparedStatement preparedStmt  = con.prepareStatement(query1);
		//Binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, cardNo);
		preparedStmt.setString(3, hcardNumber);
		
		//Execute the statement
		preparedStmt.execute();
		
		return 0;
	}  
    
    public int insertcardholderNameforkey(String nameOnCard, String hCardHolderName) throws SQLException {
		
		Connection con = dbConnect.connect();
		
		//Making Key Value pairs
		//Name
		String query1 = "INSERT INTO hCardName(`id`, `nKey`, `nvalue`) VALUES(?,?,?)" ;
		PreparedStatement preparedStmt  = con.prepareStatement(query1);
		//Binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, nameOnCard);
		preparedStmt.setString(3, hCardHolderName);
		
		//Execute the statement
		preparedStmt.execute();
		
		return 0;
	}
    
   public int insertCvvForKey(String cvv,String hcvvNo) throws SQLException {
		
		Connection con = dbConnect.connect();
		
		//Making Key Value pairs
		//Name
		String query1 = "INSERT INTO hCVV(`id`, `nKey`, `nvalue`) VALUES(?,?,?)" ;
		PreparedStatement preparedStmt  = con.prepareStatement(query1);
		//Binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, cvv);
		preparedStmt.setString(3, hcvvNo);
		
		//Execute the statement
		preparedStmt.execute();
		
		return 0;
	}
	
	
}
