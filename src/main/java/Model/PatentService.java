package Model;

import java.sql.*;

public class PatentService {
	
	public Connection connect(){
		
		Connection con = null;
		
		try{
				
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/patentservices","root", "M@ng@th@9093");

			//For Testing
			System.out.println("Successfully connected");
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertPatent(String PatentID, String Title, String appDate, String ResearcherID, String ConceptID) {
	
		String output = "";
		
		try {
			
			Connection con = connect();
			
			//Checking Connection
			if(con == null) {
				return "Error while connecting to Database";		
			}
			
			//Create Prepared Statement
			String query = "INSERT INTO patentapplication(`PID`,`PatentID`,`Title`,`appDate`,`ResearcherID`, `ConceptID`) VALUES(?,?,?,?,?,?)";
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//Binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PatentID);
			preparedStmt.setString(3, Title);
			preparedStmt.setString(4, appDate);
			preparedStmt.setString(5, ResearcherID);
			preparedStmt.setString(6, ConceptID);
			
			//Execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Patent Form Created Succesfully";
			
			
		}catch(Exception e) {
			output = "Error while Inserting Service";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readPatent()
	{
		String output = "";
		try{
			
			Connection con = connect();
			
			//Checking Connection
			if (con == null){
				return "Error while Connecting to the Database for Reading.";
			}
			
			// Prepare the HTML table to be displayed
			output = "<table border='1'>"
						+ "<tr>"
							+ "<th>Title</th>"
							+ "<th>AppliedDate</th>"
							+ "<th>Concept</th>"
							+ "<th>Update</th>"
							+ "<th>Remove</th>"
						+ "</tr>";
			
			String query = "select * from patentapplication";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
		
				String PID = Integer.toString((rs.getInt("PID")));
				String Title = rs.getString("Title");
				String appDate = rs.getString("appDate");
				String ConceptID = rs.getString("ConceptID");
				String Description = rs.getString("Description");
				String MFRID = rs.getString("MFRID");
				
				// Add a row into the HTML table
				output += "<tr><td>" + Title + "</td>";
				output += "<td>" + appDate + "</td>";
				output += "<td>" + ConceptID + "</td>";
								
				// buttons
				output += "<td>"
							+ "<form method='post' action='updateService.jsp'>"
								+ "<input name='btnUpdate' type='submit' value='Update' class='btn btn-warning'>"
								+ "<input name='SID' type='hidden' value=' " + PID + "'>"
								+ "<input name='ServiceID' type='hidden' value=' " + Title + "'>"
								+ "<input name='Name' type='hidden' value=' " + appDate + "'>"
								+ "<input name='Speciality' type='hidden' value=' " + ConceptID + "'>"
							+ "</form></td>"
						+ "<td>"
							+ "<form method='post' action='Service.jsp'>"
								+ "<input name='btnRemove' type='submit' value='Delete' class='btn btn-danger'>"
								+ "<input name='itemID' type='hidden' value='" + PID + "'>"
							+ "</form>"
						+ "</td></tr>";
				
			}
			
			con.close();
			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e){
			output = "Error while reading the Services.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
}
