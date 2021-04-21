package resources;

import java.sql.*;

import security.ConceptHashing;
import util.DBConnection;


public class ConceptAPI {
	
	//DBConnection object to connect to database
	DBConnection dbConnect = new DBConnection();
	
	// ---Method to retrieve concept details of a researcher---
	public String readMyConcepts(String researcherID)
	{
		String output = "";
		try
		{
			Connection con = dbConnect.connect();
			if (con == null)
			{
				return "Database Connection failed!!";
			}
			
			// Displaying the read concepts
			output = "<table border='1'><tr><th>Concept Code</th>"
			+"<th>Concept Name</th><th>Concept Description</th>"
			+ "<th>Start Date</th><th>Deadline</th>"
			+ "<th>Pledge Goal</th><th>Reward</th>"
			+ "<th>Pledged Amount</th>"
			+ "<th>Status</th><th>Work Update</th>"
			+ "<th>Update</th><th>Remove</th></tr>";
			
			// Retrieving the concepts launched by a particular researcher
			String query = "select c.conceptID, c.conceptCode, hn.nKey as conceptName, hd.nKey as conceptDesc, c.startDate, c.deadline, c.pledgeGoal, c.reward, c.status, c.workUpdt from concept c, hconceptname hn, hconceptdesc hd where c.conceptName = hn.Value and c.conceptDesc = hd.Value and c.researcherID = "+researcherID;
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
				
				// -- Calling a stored function in the database to retrieve the total pledged amount --
				//Preparing a CallableStatement to call a function
			    CallableStatement cstmt = con.prepareCall("{? = call funcGetAmount(?)}");
			    
			    //Registering the out parameter of the function (return type)
			    cstmt.registerOutParameter(1, Types.DOUBLE);
			    
			    //Setting the input parameters of the function
			    cstmt.setString(2, Integer.toString(Integer.parseInt(conceptID)));
			    
			    //Executing the statement
			    cstmt.execute();
			    
			    //Set the value returned by the function to a variable
			    String pledgedAmount = cstmt.getString(1);
			    
			    //If no amounts pledged so far set the value as 0.00
			    if(pledgedAmount == null) {
			    	pledgedAmount = "0.00";
			    }
				
				// Add a row into the HTML table
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
				
				// buttons for update and delete
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
				
				// Completion of the HTML table
				output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while retrieving your concept details!!";
				System.err.println(e.getMessage());
			}
			return output;
	}
	
	
	
	
	// ---Method to insert the concept details---
	public String insertConcept(String conceptName, String conceptDesc, String startDate, String deadline, String pledgeGoal, String reward,String workUpdt, String researcherID, String manufactID){
		
		String output = "";
		try
		{
			Connection con = dbConnect.connect();
			if (con == null)
			{
				return "Database Connection failed!!";
			}
			
			ConceptHashing conceptHash = new ConceptHashing();
			String hName = conceptHash.hashPassword(conceptName);
			String hDesc = conceptHash.hashPassword(conceptDesc);
			
			// -- Calling a function in the database to auto generate a sequential concept ID --
			//Preparing a CallableStatement to call a function
		    CallableStatement cstmt = con.prepareCall("{? = call getCustomID()}");
		    
		    //Registering the out parameter of the function (return type)
		    cstmt.registerOutParameter(1, Types.CHAR);
		    
		    //Executing the statement
		    cstmt.execute();
		    
		    //Get the value returned by function and set it to a variable
		    String conceptCode = cstmt.getString(1);
			
			// create a prepared statement
			String query = " insert into concept(`conceptID`,`conceptCode`,`conceptName`,`conceptDesc`,`startDate`,`deadline`,`pledgeGoal`,`reward`,`workUpdt`,`researcherID`,`manufactID`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, conceptCode);
			preparedStmt.setString(3, hName);
			preparedStmt.setString(4, hDesc);
			preparedStmt.setString(5, startDate);
			preparedStmt.setString(6, deadline);
			preparedStmt.setDouble(7, Double.parseDouble(pledgeGoal));
			preparedStmt.setString(8, reward);
			preparedStmt.setString(9, workUpdt);
			preparedStmt.setInt(10, Integer.parseInt(researcherID));
			preparedStmt.setInt(11, Integer.parseInt(manufactID));
			
			//execute the statement
			preparedStmt.execute();
			
			//Table for Hash values 
			insertNameForKey(conceptName, hName);
			insertDescForKey(conceptDesc, hDesc);
			
			con.close();
			
			output = "Concept Details Inserted Successfully";
		}
		catch (Exception e)
		{
			output = "Error while launching the concept!!";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//  ---Method to update a concept details---
	public String updateConcept(String conceptID, String conceptName, String conceptDesc, String pledgeGoal, String reward, String workUpdt)
	{
		String output = "";
		try
		{
			Connection con = dbConnect.connect();
			if (con == null){
				return "Database Connection failed!!"; 
			}
			
			ConceptHashing conceptHash = new ConceptHashing();
			String hName = conceptHash.hashPassword(conceptName);
			String hDesc = conceptHash.hashPassword(conceptDesc);
			
			// create a prepared statement
			String query = "UPDATE concept SET conceptName=?,conceptDesc=?,pledgeGoal=?,reward=?,workUpdt=? WHERE conceptID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, hName);
			preparedStmt.setString(2, hDesc);
			preparedStmt.setDouble(3, Double.parseDouble(pledgeGoal));
			preparedStmt.setString(4, reward);
			preparedStmt.setString(5, workUpdt);
			preparedStmt.setInt(6, Integer.parseInt(conceptID));
			
			// execute the statement
			preparedStmt.execute();
			
			//Table for Hash values 
			insertNameForKey(conceptName, hName);
			insertDescForKey(conceptDesc, hDesc);
			
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
	
	
	// ---Method to delete a concept detail---
	public String deleteConcept(String conceptID)
	{
		String output = "";
		try
		{
			Connection con = dbConnect.connect();
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
	
	
	// ---Method to read all concepts as a consumer---
	public String readAllConcepts()
	{
		String output = "";
		try
		{
			Connection con = dbConnect.connect();
			if (con == null)
			{
				return "Database Connection failed!!";
			}
			
			// Displaying the read concepts
			output = "<table border='1'><tr><th>Concept Code</th>"
			+"<th>Researcher ID</th><th>Concept Name</th><th>Concept Description</th>"
			+ "<th>Start Date</th><th>Deadline</th>"
			+ "<th>Pledge Goal</th><th>Reward</th>"
			+ "<th>Pledged Amount</th>"
			+ "<th>Status</th><th>Work Update</th>"
			+ "<th>Support</th></tr>";
			
			// retrieving all the concept details
			String query = "select c.conceptID, c.conceptCode, hn.nKey as conceptName, hd.nKey as conceptDesc, c.startDate, c.deadline, c.pledgeGoal, c.reward, c.status, c.workUpdt, c.researcherID from concept c, hconceptname hn, hconceptdesc hd where c.conceptName = hn.Value and c.conceptDesc = hd.Value ";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// Iterate through the rows in the result set
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
				String researcherID = Integer.toString(rs.getInt("researcherID"));
				
				// -- Calling the stored function to retrieve the total pledged amount for concept --
				//Preparing a CallableStatement to call a function
			    CallableStatement cstmt = con.prepareCall("{? = call funcGetAmount(?)}");
			    
			    //Registering the out parameter of the function (return type)
			    cstmt.registerOutParameter(1, Types.DOUBLE);
			    
			    //Setting the input parameters of the function
			    cstmt.setString(2, Integer.toString(Integer.parseInt(conceptID)));
			    
			    //Executing the statement
			    cstmt.execute();
			    
			    //Set the value returned by function to a variable
			    String pledgedAmount = cstmt.getString(1); 
			    
			    //If no values returned set the total as 0.00
			    if(pledgedAmount == null) {
			    	pledgedAmount = "0.00";
			    }
				
			    
				// Add a row into the HTML table
				output += "<tr><td>" + conceptCode + "</td>";
				output += "<td>" + researcherID + "</td>";
				output += "<td>" + conceptName + "</td>";
				output += "<td>" + conceptDesc + "</td>";
				output += "<td>" + startDate + "</td>";
				output += "<td>" + deadline + "</td>";
				output += "<td>" + pledgeGoal + "</td>";
				output += "<td>" + reward + "</td>";
				output += "<td>" + pledgedAmount + "</td>";
				output += "<td>" + status + "</td>";
				output += "<td>" + workUpdt + "</td>";
				
				// button for backing a concept
				output += "<td><form method='post' action=''>"
				+ "<input name='btnBacks' "
				+ " type='submit' value='Back the project' class='btn btn-secondary'>"
				+ "<input name='conceptID' type='hidden' "
				+ " value=' " + conceptID + "'>"
				+ "</form></td></tr>";
				
				}
				con.close();
				
				// Completion of the HTML table
				output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while retrieving the concepts!";
				System.err.println(e.getMessage());
			}
			return output;
	}
	
	
	
	public int insertDescForKey(String conceptDesc, String hDesc)throws SQLException {
			
			Connection con = dbConnect.connect();
			
			//Making Key Value pairs
			//Name
			String query2 = "INSERT INTO hConceptDesc(`id`, `nKey`, `Value`) VALUES(?,?,?)" ;
			PreparedStatement nameT  = con.prepareStatement(query2);
			//Binding values
			nameT.setInt(1, 0);
			nameT.setString(2, conceptDesc);
			nameT.setString(3, hDesc);
			
			//Execute the statement
			nameT.execute();
			
			return 0;
		}
	
	
	public int insertNameForKey(String conceptName, String hName) throws SQLException {
		
		Connection con = dbConnect.connect();
		
		//Making Key Value pairs
		//Name
		String query2 = "INSERT INTO hConceptName(`id`, `nKey`, `Value`) VALUES(?,?,?)" ;
		PreparedStatement nameT  = con.prepareStatement(query2);
		//Binding values
		nameT.setInt(1, 0);
		nameT.setString(2, conceptName);
		nameT.setString(3, hName);
		
		//Execute the statement
		nameT.execute();
		
		return 0;
	}
	

}
