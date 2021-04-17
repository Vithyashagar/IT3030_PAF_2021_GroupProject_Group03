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
	
	
	/****************************************** RETRIEVING CONCEPTS AS A RESEARCHER ********************************/
	public String readMyConcepts(String researcherID)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Database Connection failed!!";
			}
			
			//Displaying the read concepts
			output = "<table border='1'><tr><th>Concept Code</th>"
			+"<th>Concept Name</th><th>Concept Description</th>"
			+ "<th>Start Date</th><th>Deadline</th>"
			+ "<th>Pledge Goal</th><th>Reward</th>"
			+ "<th>Pledged Amount</th>"
			+ "<th>Status</th><th>Work Update</th>"
			+ "<th>Update</th><th>Remove</th></tr>";
			
			//SQL query to retrieve a researchers concepts
			String query = "select * from concept where researcherID = "+researcherID;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String conceptID = Integer.toString(rs.getInt("conceptID"));
				String conceptCode = rs.getString("conceptCode");
				String conceptName = rs.getString("conceptName");
				String conceptDesc = rs.getString("conceptDesc");
				String startDate = rs.getString("startDate");
				String deadline = rs.getString("deadline");
				String pledgeGoal = Double.toString(rs.getDouble("pledgeGoal"));
				String reward = rs.getString("reward");
				String status = rs.getString("status");
				String workUpdt = rs.getString("workUpdt");
				
				/*** 
				 * Calling the function to get the total pledged amount ***/
				//Preparing a CallableStatement to call a function
			    CallableStatement cstmt = con.prepareCall("{? = call funcGetAmount(?)}");
			    
			    //Registering the out parameter of the function (return type)
			    cstmt.registerOutParameter(1, Types.DOUBLE);
			    
			    //Setting the input parameters of the function
			    cstmt.setString(2, Integer.toString(Integer.parseInt(conceptID)));
			    
			    //Executing the statement
			    cstmt.execute();
			    
			    //Getting the value returned by function
			    String pledgedAmount = cstmt.getString(1);
			    
			    //If no amounts pledged so far set the value as 0.00
			    if(pledgedAmount == null) {
			    	pledgedAmount = "0.00";
			    }
				
			    
				// Add a row into the html table
				output += "<tr><td>" + conceptCode + "</td>";
				output += "<td>" + conceptName + "</td>";
				output += "<td>" + conceptDesc + "</td>";
				output += "<td>" + startDate + "</td>";
				output += "<td>" + deadline + "</td>";
				output += "<td>" + pledgeGoal + "</td>";
				output += "<td>" + reward + "</td>";
				output += "<td>" + pledgedAmount + "</td>";
				output += "<td>" + status + "</td>";
				output += "<td>" + workUpdt + "</td>";
				
				// buttons
				output += "<td><form method='post' action='updateConcept.jsp'>"
				+ "<input name='btnUpdate' "
				+ " type='submit' value='Update' class='btn btn-secondary'>"
				+ "<input name='conceptID' type='hidden' "
				+ " value=' " + conceptID + "'>"
				+ "<input name='conceptName' type='hidden' "
				+ " value=' " + conceptName + "'>"
				+ "<input name='conceptDesc' type='hidden' "
				+ " value=' " + conceptDesc + "'>"
				+ "<input name='startDate' type='hidden' "
				+ " value=' " + startDate + "'>"
				+ "<input name='deadline' type='hidden' "
				+ " value=' " + deadline + "'>"
				+ "<input name='pledgeGoal' type='hidden' "
				+ " value=' " + pledgeGoal + "'>"
				+ "<input name='reward' type='hidden' "
				+ " value=' " + reward + "'>"
				+ "<input name='pledgedAmount' type='hidden' "
				+ " value=' " + pledgedAmount + "'>"
				+ "<input name='status' type='hidden' "
				+ " value=' " + status + "'>"
				+ "<input name='workUpdt' type='hidden' "
				+ " value=' " + workUpdt + "'>"
				+ "</form></td>"
				+ "<td><form method='post' action='Concept.jsp'>"
				+ "<input name='btnRemove' "
				+ " type='submit' value='Remove' class='btn btn-danger'>"
				+ "<input name='conceptID' type='hidden' "
				+ " value=' " + conceptID + "'>" + "</form></td></tr>";
				}
				con.close();
				
				// Complete the html table
				output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while retrieving your concept details!!";
				System.err.println(e.getMessage());
			}
			return output;
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
			
			output = "Concept Deatils Inserted Successfully";
		}
		catch (Exception e)
		{
			output = "Error while launching the concept!!";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	/****************************************** Updating a concept detail *****************************************/
	public String updateConcept(String conceptID, String conceptName, String conceptDesc, String pledgeGoal, String reward, String workUpdt)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null){
				return "Database Connection failed!!"; 
			}
			
			// create a prepared statement
			String query = "UPDATE concept SET conceptName=?,conceptDesc=?,pledgeGoal=?,reward=?,workUpdt=? WHERE conceptID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, conceptName);
			preparedStmt.setString(2, conceptDesc);
			preparedStmt.setDouble(3, Double.parseDouble(pledgeGoal));
			preparedStmt.setString(4, reward);
			preparedStmt.setString(5, workUpdt);
			preparedStmt.setInt(6, Integer.parseInt(conceptID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Concept Details Updated Successfully!!";
		}
		catch (Exception e)
		{
			output = "Error while updating the concept details!!";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	/************************************* DELETE CONCEPT DETAILS ***********************************************/
	public String deleteConcept(String conceptID)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null) {
				return "Database Connection failed!!"; 
			}
			
			// create a prepared statement
			String query = "delete from concept where conceptID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(conceptID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Concept deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the concept";
			System.err.println(e.getMessage());
		}
		return output;
	}
	

}
