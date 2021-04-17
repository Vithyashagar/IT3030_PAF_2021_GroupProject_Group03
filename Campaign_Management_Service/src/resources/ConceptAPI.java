package resources;

import java.sql.*;


public class ConceptAPI {
	
	
	//Connecting to the database
	public Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/campaign_service","root", "Shahimaria@123");
					
			//For testing
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	
	
	
	/******************************************* INSERT CONCEPT ****************************************************/
	public String insertConcept(String conceptName, String conceptDesc, String startDate, String deadline, String pledgeGoal, String reward,String workUpdt, String researcherID, String manufactID){
		
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Database Connection failed!!";
			}
			
			
			//Preparing a CallableStatement to call a function
		    CallableStatement cstmt = con.prepareCall("{? = call getCustomID()}");
		    //Registering the out parameter of the function (return type)
		    cstmt.registerOutParameter(1, Types.CHAR);
		    //Executing the statement
		    cstmt.execute();
		    String conceptCode = cstmt.getString(1);
			
			// create a prepared statement
			String query = " insert into concept(`conceptID`,`conceptCode`,`conceptName`,`conceptDesc`,`startDate`,`deadline`,`pledgeGoal`,`reward`,`workUpdt`,`researcherID`,`manufactID`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, conceptCode);
			preparedStmt.setString(3, conceptName);
			preparedStmt.setString(4, conceptDesc);
			preparedStmt.setString(5, startDate);
			preparedStmt.setString(6, deadline);
			preparedStmt.setDouble(7, Double.parseDouble(pledgeGoal));
			preparedStmt.setString(8, reward);
			preparedStmt.setString(9, workUpdt);
			preparedStmt.setString(10, researcherID);
			preparedStmt.setString(11, manufactID);
			
			//execute the statement
			preparedStmt.execute();
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
	

}
