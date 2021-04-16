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
	
	
	
}
