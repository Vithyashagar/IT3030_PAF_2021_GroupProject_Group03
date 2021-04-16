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
	
	
	
}
