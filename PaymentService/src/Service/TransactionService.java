package Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

public class TransactionService {

/*****************************************Checking for database connectivity.***************************************/
	
	public Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paymentdb","root", "1234");
			
			//Testing for connectivity
			
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
/********************************Inserting backing transaction details.*************************************/
	
	public String insertBackingTransaction(int conceptID ,int  consumerID ,double pledgeAmount)
	{
		//variable declaration to capture output message and calculated product price
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
            
			// create a prepared statement
			
			String query = " insert into backing(`conceptID`,`consumerID`,`pledgeAmount`)"
			+ " values (?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			
				// binding values
			
			preparedStmt.setInt(1, conceptID);
			preparedStmt.setInt(2, consumerID);
			preparedStmt.setDouble(3, pledgeAmount);
			
			
			//execute the statement
			preparedStmt.execute();
			
			//closing connection object
			con.close();
			
			output = " Backer transaction Inserted successfully";
	}
	catch (Exception e)
	{
		output = "Error while inserting backer transaction";
		System.err.println(e.getMessage());
	}
		
		return output;
	}
	
	
	/*****************************************Method to read relevant backer transactions********************************/
	public String readBackerTransactions()
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
			
				output = "<table border=‘1’><tr><th>ConceptID</th>"
				+"<th>ConsumerID</th>"
				+ "<th>Pledge Amount</th>";
				
				
				String query = "select * from backing";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				
				while (rs.next())
				{
					String conceptID =  Integer.toString(rs.getInt("conceptID"));
					String consumerID =  Integer.toString(rs.getInt("consumerID"));
					double pledgeAmount = rs.getDouble("pledgeAmount");
					
					
					// Add into the html table
					
					output += "<tr><td>" + conceptID + "</td>";
					output += "<td>" + consumerID + "</td>";
					output += "<td>" + pledgeAmount + "</td>";
					
						
					}
				
				con.close();
				
				
				// Complete the html table
					output += "</table>";
		}
		catch (Exception e)
		{
				output = "Error while reading the backer transactions.";
				System.err.println(e.getMessage());
		}
	
		return output;
	}
}
