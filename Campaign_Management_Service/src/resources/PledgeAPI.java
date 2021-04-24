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
			preparedStmt.setString(1, conceptID);
			preparedStmt.setString(2, backerID);
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
				String query = "select * from backs";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next())
				{
					String conceptID = rs.getString("conceptID");
					String backerID = rs.getString("backerID");
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
				String query = "select * from backs where backerID = '"+backerID+"' ";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next())
				{
					String conceptID = rs.getString("conceptID");
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
		
		
		
		//-- Method to read the consumer pledges
				public String getConceptID(String conceptName)
				{
					String conceptID = "";
					try
					{
						Connection con = dbConnect.connect();
						if (con == null)
						{
							return "Database Connection failed!!";
						}
						
						// Retrieving all the pledge details
						String query = "select c.conceptCode from concept c, hconceptname n where c.conceptName = n.Value and n.nKey = '"+conceptName+"' ";
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						
						// iterate through the rows in the result set
						while (rs.next())
						{
							 conceptID = rs.getString("conceptCode");	
						}
						con.close();
						
						}
						catch (Exception e)
						{
							System.err.println(e.getMessage());
						}
						return conceptID;
				}
				
				
				
	/******************* METHDODS AS A SERVER FOR INTER SERVICE COMMUNICATION *******************************/
     //Retrieve Consumer ID for payment
	public String readSpecificConsumerForConcept(String ConceptID ) {
		
	    String output = "";
			        
		 try { 
			 Connection con =dbConnect.connect();
			             
             if (con == null){
			     return "Error while connecting to the database for reading."; 
			 }
			        
			 String query = "select * from backs  where conceptID = '"+ConceptID+"'";
			 Statement stmt = con.createStatement();
			         
			 ResultSet rs = stmt.executeQuery(query);
			 String ConsumerID = null;
			            
			 while (rs.next()){
			       ConsumerID = rs.getString("backerID");
			 }

			 con.close();
			 output += ConsumerID;
			            
			 } catch (SQLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			 }

			 return output;
		}
		
		

}
