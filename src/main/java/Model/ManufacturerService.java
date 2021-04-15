package Model;

import java.sql.*;

public class ManufacturerService {

	
	public Connection connect(){
		
		Connection con = null;
		
		try{
				
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturerservices","root", "M@ng@th@9093");

			//For Testing
			System.out.println("Successfully connected");
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
public String insertService(String ServiceID, String Name, String Speciality, String Description, String MFRID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			//Checking Connection
			if(con == null) {
				return "Error while connecting to Database";		
			}
			
			//Create Prepared Statement
			String query = "INSERT INTO services(`SID`,`ServiceID`,`Name`,`Speciality`,`Description`, `MFRID`) VALUES(?,?,?,?,?,?)" ;
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//Binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ServiceID);
			preparedStmt.setString(3, Name);
			preparedStmt.setString(4, Speciality);
			preparedStmt.setString(5, Description);
			preparedStmt.setString(6, MFRID);
			
			//Execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Service Created Succesfully";
			
			
		}catch(Exception e) {
			output = "Error while Inserting Service";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
}