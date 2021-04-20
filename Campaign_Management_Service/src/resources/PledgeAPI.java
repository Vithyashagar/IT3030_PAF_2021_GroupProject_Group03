package resources;

import java.sql.*;
import util.DBConnection;

public class PledgeAPI {
	
	//DBConnection object to establish connection
	DBConnection dbConnect = new DBConnection();
	
	// -- Method to insert pledge --
	public String insertPledge(String conceptID, String backerID, String pledgedAmnt){
		
		String output = "";
		try
		{
			Connection con = dbConnect.connect();
			if (con == null)
			{
				return "Database Connection failed!!";
			}
			
			
			// create a prepared statement
			String query = " insert into backs(`conceptID`,`backerID`,`pledgedAmnt`)"
			+ " values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(conceptID));
			preparedStmt.setInt(2, Integer.parseInt(backerID));
			preparedStmt.setDouble(3, Double.parseDouble(pledgedAmnt));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "You pledged the project successfully!!";
		}
		catch (Exception e)
		{
			output = "Failed to pledge!!";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
	// --- Method to view all pledges ---
		public String readPledges()
		{
			String output = "";
			try
			{
				Connection con = dbConnect.connect();
				if (con == null)
				{
					return "Database Connection failed!!";
				}
				
				// Displaying the read pledges
				output = "<table border='1'><tr><th>Concept ID</th>"
				+"<th>BackerID</th><th>Pledged Amount</th></tr>";
				
				// Retrieving all the pledge details
				String query = "select * from backs group by conceptID";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next())
				{
					String conceptID = Integer.toString(rs.getInt("conceptID"));
					String backerID = Integer.toString(rs.getInt("backerID"));
					String pledgedAmnt = Double.toString(rs.getDouble("pledgedAmnt"));
					
					// Add a row into the HTML table
					output += "<tr><td>" + conceptID + "</td>";
					output += "<td>" + backerID + "</td>";
					output += "<td>" + pledgedAmnt + "</td>";
					
				}
				con.close();
					
				// Completion of the HTML table
				output += "</table>";
				}
				catch (Exception e)
				{
					output = "Error while retrieving the pledged details!!";
					System.err.println(e.getMessage());
				}
				return output;
		}
		
		
		//-- Method to read the consumer pledges
		public String readConsumerPledges(String backerID)
		{
			String output = "";
			try
			{
				Connection con = dbConnect.connect();
				if (con == null)
				{
					return "Database Connection failed!!";
				}
				
				// Displaying the read pledges
				output = "<table border='1'><tr><th>Concept ID</th>"
				+"<th>Pledged Amount</th></tr>";
				
				// Retrieving all the pledge details
				String query = "select * from backs where backerID = "+backerID;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next())
				{
					String conceptID = Integer.toString(rs.getInt("conceptID"));
					String pledgedAmnt = Double.toString(rs.getDouble("pledgedAmnt"));
					
					// Add a row into the HTML table
					output += "<tr><td>" + conceptID + "</td>";
					output += "<td>" + pledgedAmnt + "</td>";
					
				}
				con.close();
					
				// Completion of the HTML table
				output += "</table>";
				}
				catch (Exception e)
				{
					output = "Error while retrieving the pledged details!!";
					System.err.println(e.getMessage());
				}
				return output;
		}
		
		
		

}
