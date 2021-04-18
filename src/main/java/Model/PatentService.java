package Model;

import java.sql.*;

import Security.Sample;

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
			
			Sample hs = new Sample();
			
			String hTitle = hs.hashPassword(Title);
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//Binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, PatentID);
			preparedStmt.setString(3, hTitle);
			preparedStmt.setString(4, appDate);
			preparedStmt.setString(5, ResearcherID);
			preparedStmt.setString(6, ConceptID);
			
			//Execute the statement
			preparedStmt.execute();
			
			//Values for Hash Table
			insertTitleforkey(Title, hTitle);
						
			con.close();
			
			output = "Patent Form Created Succesfully";
			
			
		}catch(Exception e) {
			output = "Error while Inserting Patent";
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
							+ "<th>Remove</th>"
						+ "</tr>";
			
			String query = "SELECT p.PID, h.Key as Title, p.appDate, p.ConceptID FROM hpatent h, patentapplication p WHERE h.Value = p.Title";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
		
				String PID = Integer.toString((rs.getInt("PID")));
				String Title = rs.getString("Title");
				String appDate = rs.getString("appDate");
				String ConceptID = rs.getString("ConceptID");
				
				// Add a row into the HTML table
				output += "<tr><td>" + Title + "</td>";
				output += "<td>" + appDate + "</td>";
				output += "<td>" + ConceptID + "</td>";
								
				// buttons
				output +=  "<td>"
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

	public String deletePatent(String PID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			//Checking Connection
			if(con == null) {
				return "Error while connecting to Database";
			}
			
			//create prepared statement
			String query = "delete from patentapplication where PID = ? ";
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.valueOf(PID));
			
			preparedStmt.execute();
			con.close();
			
			output = "Patent Application Deleted";
			
			
		}catch(Exception e) {
			output = "Error while Deleting Patent";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}

	public int insertTitleforkey(String Title, String hTitle) throws SQLException {
		
		Connection con = connect();
		
		//Making Key Value pairs
		//Name
		String query2 = "INSERT INTO hpatent(`id`, `Key`, `Value`) VALUES(?,?,?)" ;
		PreparedStatement nameT  = con.prepareStatement(query2);
		//Binding values
		nameT.setInt(1, 0);
		nameT.setString(2, Title);
		nameT.setString(3, hTitle);
		
		//Execute the statement
		nameT.execute();
		
		return 0;
	}
	
}
