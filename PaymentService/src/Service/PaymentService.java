package Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

public class PaymentService {

	/*Checking for database connectivity*/
	
	public Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paymentdb","root", "1234");
			
			//For testing
			
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
	/*Inserting backer or buyer details to the database*/
	
	public String insertPayment(String paymentType, String bank, String paymentDate, String cardNo , 
			String nameOnCard, String cvv  ,int productID ,int consumerID , int conceptID, 
			String cardExpMonth , String cardExpYear 
			)
	{
		String output = "";
		double totalBuyingAmt = 0.00;
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
			
		/*Validate payment as backer or buyer payment*/	
			if( productID == -1) {
				productID = -1;
			}
			else if(conceptID == -1) { /*Logic to handle if its a buyer payment */
				conceptID = -1;
				
				//contains implemented business logic
				CallableStatement  cs = con.prepareCall("{? = call insertProductAmount(?,?)}");	
				
				//setting parameter to function
				
				 cs.registerOutParameter(1, Types.DOUBLE);
				 cs.setInt(2,productID);
				 cs.setInt(3, consumerID);
			
				
				//call function
				cs.execute();
				
				//obtained returned value of function(insertProductAmount())
			    totalBuyingAmt = cs.getDouble(1);
				
			    //checking for function execution
				System.out.println("Product function called succesfully");
				
			}
			
			//Preparing a CallableStatement to call a function
            CallableStatement cstmt = con.prepareCall("{? = call getPaymentID()}");
            
            //Registering the out parameter of the function (return type)
            cstmt.registerOutParameter(1, Types.CHAR);
            
            //Executing the statement
            cstmt.execute();
            
            //obtaining returned value of function(getPaymentID())
            String PaymentCode = cstmt.getString(1);
            
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
			preparedStmt.setString(6, cardNo);
			preparedStmt.setString(7, nameOnCard);
			preparedStmt.setString(8, cvv);
			preparedStmt.setDouble(9,totalBuyingAmt);
			preparedStmt.setInt(10, productID);
			preparedStmt.setInt(11, consumerID);
			preparedStmt.setInt(12, conceptID);
			preparedStmt.setString(13, cardExpMonth);
			preparedStmt.setString(14, cardExpYear);
			
			
			//execute the statement
			preparedStmt.execute();
			
			//closing connection object
			con.close();
			
			output = "Inserted successfully";
	}
	catch (Exception e)
	{
		output = "Error while inserting";
		System.err.println(e.getMessage());
	}
		
		return output;
	}
	
	
	/*Method to read relevant payment details*/
	public String readPayments()
	{
		String output = "";
		
	try
		{
			Connection con = connect();
			
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
				+ "<th>ProductID</th>"
				+ "<th>Update</th>"
				+ "<th>Remove</th></tr>";
				
				
				String query = "select * from gb_payments";
				
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
					
					output += "<tr><td>" + PaymentType + "</td>";
					output += "<td>" + BankName + "</td>";
					output += "<td>" + paymentDate + "</td>";
					output += "<td>" + CardName + "</td>";
					output += "<td>" + buyerAmt + "</td>";
					output += "<td>" + consumerID + "</td>";
					output += "<td>" + conceptID + "</td>";
					output += "<td>" + productID + "</td>";
					
					output += "<td><form method='post' action='updatePayment.jsp'>"
							+ "<input name='btnUpdate' "
							+ " type='submit' value='Update' >"
							+ "<input name='PaymentID' type='hidden' "
							+ " value=' " + PaymentID + "'>"
							+ "<input name='ConceptID' type='hidden' "
							+ " value=' " + conceptID + "'>"
							+ "</form></td>"
							+ "<td><form method='post' action='insertpay.jsp'>"
							+ "<input name='btnRemove' "
							+ " type='submit' value='Delete' class='btn btn-danger'>"
							+ "<input name='PaymentID' type='hidden' "
							+ " value='" + PaymentID + "'>" + "</form></td></tr>";
						
					}
				
				con.close();
				
				
				// Complete the html table
					output += "</table>";
		}
		catch (Exception e)
		{
				output = "Error while reading the items.";
				System.err.println(e.getMessage());
		}
		return output;
	}
	
	/*Method to handle payment status depending on pledegAmount summation*/
	public String updatePaymentStatus(int ConceptID)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating."; 
			}
			
			//Preparing a CallableStatement to call a stored procedure
			
			//contains implemented business logic
			CallableStatement  cs = con.prepareCall("{call updateStatus(?)}");	
			
			//setting parameter to procedure
			cs.setInt(1, ConceptID);/*pass this name to postman*/
			
			//call procedure
			cs.execute();
			
			cs.close();
			
			//verify execution success
			System.out.println("Stored procedure called successfully!");
		
			output = "Concept status Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the campaign status.";
			System.err.println(e.getMessage());
		}
		
		return output;
		}
	
	
	/*Method to delete incomplete project backed funds*/
	public String deletePayment(String status) {
		
		String output = "";
		 
		
		Connection con = connect();
		
		String sql = "delete from gb_payments p where p.PaymentID > 0 AND p.ConceptID IN (select c.conceptID from concept c where c.status = '"+status+"')" ;
		
		try{
			
			PreparedStatement preparedStmt = con.prepareStatement(sql);

			preparedStmt.executeUpdate();
			
			output = "Payment Deleted Successfully!!";
		}
		catch (Exception e) {
			output = "Error while deleting";
			e.printStackTrace();
		}
		
		return output;
	}
	
}
