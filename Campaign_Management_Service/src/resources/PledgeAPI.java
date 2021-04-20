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

}
