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
	
	
	/*****Inserting backer payment********/
	public String insertBackerPayment(String consumerID,String conceptID,String paymentType, String bank, String paymentDate, String cardNo, 
			String nameOnCard, String cvv , 
			String cardExpMonth , String cardExpYear 
			)
	{
		String output = "";
		
		
		try
		{
			Connection con = dbConnect.connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
			
	/*****************************Hashing details*********************************************/		
		
	 Hashing paymentHash = new Hashing();
	 
	 String hcardNo = paymentHash.hashPassword(cardNo);
	 String hCardName = paymentHash.hashPassword(nameOnCard);
	 String hcvv = paymentHash.hashPassword(cvv);
	 
	 
			
			
/********************************Detail validation************************************************************/
			
			/*********Invoke cardNumber validator from model class********************/
			Payment p = new Payment();
			
			int cardNumberCount = p.validateCardNumber(cardNo);
			
			//int cvvValidation = p.cvvValidator(cardNo, cvv);
			
			if((paymentType.equals("Debit")) || (paymentType.equals("debit")) || (paymentType.equals("credit")) || (paymentType.equals("Credit"))  && (cardNumberCount == 16) )  {
			
			
			/*************Executing logic for Auto-generating payment id******************************************************/	
			
			//Preparing a CallableStatement to call the implemented function
            CallableStatement cstmt = con.prepareCall("{? = call getPaymentID()}");
            
            //Registering the out parameter of the function (return type)
           cstmt.registerOutParameter(1, Types.CHAR);
            
            //Executing the statement
            cstmt.execute();
            
            //obtaining returned value of function(getPaymentID())
            String PaymentCode = cstmt.getString(1);
			
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
			preparedStmt.setString(6, hcardNo);
			preparedStmt.setString(7,hCardName);
			preparedStmt.setString(8, hcvv);
			preparedStmt.setDouble(9,0);
			preparedStmt.setString(10, "NA");
			preparedStmt.setString(11, consumerID);
			preparedStmt.setString(12, conceptID);
			preparedStmt.setString(13, cardExpMonth);
			preparedStmt.setString(14, cardExpYear);
			
			
			//execute the statement
			preparedStmt.execute();
			
			
			//Table or hash values
			
			insertcardNumberforkey(cardNo,hcardNo);
			insertcardholderNameforkey(nameOnCard,hCardName);
			insertCvvForKey(cvv,hcvv);
			
			
			con.close();
			
			output = "Backer payment Inserted successfully" + "\n Your payment ID is: " +  PaymentCode;
		}
		
	else
	{
			output = "Please enter valid details";
	}
	}	
	catch (Exception e)
	{
		output = "Error while inserting";
		System.err.println(e.getMessage());
	}
		
		return output;
	}
	
	
	
	/*****Inserting buyer payment********/
	public String insertBuyerPayment(String ConsumerID,String ProductID,String paymentType, String bank, String paymentDate, String cardNo , 
			String NameOnCard, String cvv , 
			String cardExpMonth , String cardExpYear 
			)
	{
		String output = "";
		
		double totalBuyingAmt = 0.00;
		
		try
		{
			Connection con = dbConnect.connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
			/*****************************Hashing details*********************************************/		
			
			 Hashing paymentHash = new Hashing();
			 
			 String hcardNo = paymentHash.hashPassword(cardNo);
			 String hCardName = paymentHash.hashPassword(NameOnCard);
			 String hcvv = paymentHash.hashPassword(cvv);
			
			
			
/********************************Detail validation************************************************************/
			
			/*********Invoke cardNumber validator from model class********************/
			Payment p = new Payment();
			
			int cardNumberCount = p.validateCardNumber(cardNo);
			
			//int cvvValidation = p.cvvValidator(cardNo, cvv);
			
			if((paymentType.equals("Debit")) || (paymentType.equals("debit")) || (paymentType.equals("credit")) || (paymentType.equals("Credit"))  && (cardNumberCount == 16) )  {
				
			
			/*************Executing logic for Auto-generating payment id******************************************************/	
			
			//Preparing a CallableStatement to call the implemented function
            CallableStatement cstmt = con.prepareCall("{? = call getPaymentID()}");
            
            //Registering the out parameter of the function (return type)
           cstmt.registerOutParameter(1, Types.CHAR);
            
            //Executing the statement
            cstmt.execute();
            
            //obtaining returned value of function(getPaymentID())
            String PaymentCode = cstmt.getString(1);
            
            
            
   /******************************BUYER PAYMENT CALCULATION**************************************************************/         
          //contains implemented business logic to calculate total buying amount of buyer
			 CallableStatement  cs = con.prepareCall("{? = call insertProductAmount(?,?)}");	
			
			//setting parameter to function
			
			 cs.registerOutParameter(1, Types.DOUBLE);
			 cs.setString(2,ProductID);
			 cs.setString(3, ConsumerID);
		
			
			//call function
			 cs.execute();
			
			//obtained returned value of function(insertProductAmount())
		     totalBuyingAmt = cs.getDouble(1);
			
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
			preparedStmt.setString(6, hcardNo);
			preparedStmt.setString(7,hCardName);
			preparedStmt.setString(8, hcvv);
			preparedStmt.setDouble(9,totalBuyingAmt);
			preparedStmt.setString(10, ProductID);
			preparedStmt.setString(11, ConsumerID);
			preparedStmt.setString(12, "NA");
			preparedStmt.setString(13, cardExpMonth);
			preparedStmt.setString(14, cardExpYear);
			
			
			//execute the statement
			preparedStmt.execute();
			
				//Table or hash values
			
			insertcardNumberforkey(cardNo,hcardNo);
			insertcardholderNameforkey(NameOnCard,hCardName);
			insertCvvForKey(cvv,hcvv);
			
			con.close();
			
			output = "Buyer payment Inserted successfully" + "\n Your payment ID is: "  + PaymentCode;
			
			
			}
			else {
				 output = "Please enter valid details";
			}
	}
	catch (Exception e)
	{
		output = "Error while inserting";
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
			
				output = "<table border=�1�><tr><th>Payment Type</th>"
				+"<th>Bank Name</th>"
				+ "<th>Payment Date</th>"
				+ "<th>Name on card</th>"
				+ "<th>BuyerPayment</th>"
				+ "<th>ConsumerID</th>"
				+ "<th>ConceptID</th>"
				+ "<th>ProductID</th>";
				
				
				String query = "select p.PaymentType , p.bank , p.paymentDate , hn.nKey AS NameOnCard , p.Buyerpayment , p.ProductID , p.ConsumerID , p.ConceptID from gb_payments p , hcardname hn , hcardno ho , hcvv hv where p.NameOnCard = hn.nvalue AND p.cardNo = ho.nvalue AND p.cvv = hv.nvalue ";
				
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
					String productID =  rs.getString("ProductID");
					String consumerID =  rs.getString("ConsumerID");
					String conceptID =  rs.getString("ConceptID");
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
	
	
	/*****************************************Method to read specific user payment details********************************/
	public String readSpecificUserPayments(String name)
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
			
				output = "<table border=�1�><tr><th>Payment Type</th>"
				+"<th>Bank Name</th>"
				+ "<th>Payment Date</th>"
				+ "<th>Card Number</th>"
				+ "<th>Name On Card</th>"
				+ "<th>CVV</th>"
				+ "<th>BuyerPayment</th>"
				+ "<th>ConsumerID</th>"
				+ "<th>ConceptID</th>"
				+ "<th>ProductID</th>";
				
				
				String query = "select p.PaymentType , p.bank , p.paymentDate , ho.nKey AS cardNo ,  hn.nKey AS NameOnCard , hv.nKey AS cvv, p.Buyerpayment , p.ProductID , p.ConsumerID , p.ConceptID from gb_payments p , hcardname hn , hcardno ho , hcvv hv where p.NameOnCard = hn.nvalue AND p.cardNo = ho.nvalue AND p.cvv = hv.nvalue AND hn.nKey = '"+name+"'";
				
				
				//String query = "select * from gb_payments where  NameOnCard = '"+name+"'";
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				
				while (rs.next())
				{
					//String PaymentID = Integer.toString(rs.getInt("PaymentID"));
					String PaymentType = rs.getString("PaymentType");
					String BankName = rs.getString("bank");
					String paymentDate = rs.getString("paymentDate");
					String cardNumber = rs.getNString("cardNo");
					String CardName= rs.getString("NameOnCard");
					String cvv = rs.getString("cvv");
					double buyerAmt = rs.getDouble("Buyerpayment");
					String productID =  rs.getString("ProductID");
					String consumerID =  rs.getString("ConsumerID");
					String conceptID =  rs.getString("ConceptID");
					//String cardExpMonth =  Integer.toString(rs.getInt("cardExpMonth"));
					//String cardExpYear = Integer.toString(rs.getInt("cardExpYear"));
					System.out.println(cardNumber);
					
					// Add into the html table
					
					output += "<tr><td>" + PaymentType + "</td>";
					output += "<td>" + BankName + "</td>";
					output += "<td>" + paymentDate + "</td>";
					output += "<td>" + cardNumber + "</td>";
					output += "<td>" + CardName + "</td>";
					output += "<td>" + cvv + "</td>";
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
	
	
	
	
	
	
	/**************************Method to handle payment status depending on pledegAmount summation**********************/
	public String updatePaymentStatus(String ConceptID)
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
			cs.setString(1, ConceptID);/*pass this name to postman*/
			
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
	
	public String updatePaymentDetails(String paymentCode,String paymentType,String bank , String cardNo , String NameOnCard ,String cvv, String cardExpMonth ,String cardExpYear)
	{
		String output = "";
		
		try
		{
			
			Connection con = dbConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			
			//Hashing
			Hashing hs = new Hashing();
			
			String hcardNumber = hs.hashPassword(cardNo);
			String hCardHolderName = hs.hashPassword(NameOnCard);
			String hcvvNo = hs.hashPassword(cvv);
			
			// create a prepared statement
			
		String query = "UPDATE gb_payments SET PaymentType=?,bank=?,cardNo=?,NameOnCard=?,cvv=?,cardExpMonth=?,cardExpYear=? WHERE paymentCode=?";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		
		
		// binding values
		
		preparedStmt.setString(1, paymentType);
		preparedStmt.setString(2, bank);
		preparedStmt.setString(3, hcardNumber);
		preparedStmt.setString(4, hCardHolderName);
		preparedStmt.setString(5, hcvvNo);
		preparedStmt.setString(6, cardExpMonth);
		preparedStmt.setString(7, cardExpYear);
		preparedStmt.setString(8, paymentCode);
		
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
		
		String sql = "delete from paymentdb.gb_payments p where p.PaymentID > 0 AND p.ConceptID IN (select c.conceptCode from concept_service.concept c where c.status = '"+status+"')" ;
		
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