package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userLogin {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf?useTimezone=true&serverTimezone=UTC","root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String userLogin(String username, String password) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			String query ="select username,phoneNo from users where username="+username+" AND phoneNo="+password;
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = ((java.sql.Statement) preparedStmt).executeQuery(query);
			
			
			  while (rs.next()) {
			        String UserName = rs.getString("username");
			        String Password = rs.getString("phoneNo");
			        
			  
			        if((username.equalsIgnoreCase(UserName)) && (password.equalsIgnoreCase(Password))) {
			        	//output ="     Login Failed  !!";
			        	output ="     Login Successful  !!           You're logged as"   +username;
			        	}
		              else {
		                output ="      Login Failed...!!";
		            	 //output ="     Login Successful  !!           You're logged as"   +username;
		              	} 
			  	}
			
			con.close();
			
		}catch (Exception e) {
			output = "Error while Logging.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
}