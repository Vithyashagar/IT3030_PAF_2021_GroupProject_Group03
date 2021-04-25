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
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user_service", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String userLogin(String username, String password,String type) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
		
			if (type.equals("consumer") || type.equals("Consumer")) {
				
				String query ="select c.userID,c.userCode,u.nKey as userName,p.nKey as password,email,address,dob,phone from consumer c,h_username u, h_password p where c.userName = u.nvalue and c.password = p.nvalue and u.nKey = '"+username+"'AND p.nKey = '"+password+"' ";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				ResultSet rs = ((java.sql.Statement) preparedStmt).executeQuery(query);
				
				
				  while (rs.next()) {
					    String userID = Integer.toString(rs.getInt("userID"));
					    String UserCode = rs.getString("userCode");
				        String UserName = rs.getString("userName");
				        String Password = rs.getString("password");
				        String Email = rs.getString("email");
				        String Address = rs.getString("address");
				        String Dob =rs.getString("dob");
				        String phone =rs.getString("phone");
				  
				        if((username.equals(UserName)) && (password.equals(Password))) {
				        	
				        	output ="     Login Successful  !!           You're logged as "   +username;
				        	output += "<br><br><table border='1'><tr><th>User ID</th><th>User Code</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th></tr>";
				        	output += "<tr><td>" + userID + "</td>";
				        	output += "<td>" + UserCode + "</td>";
						   	output += "<td>" + UserName + "</td>";
						   	output += "<td>" + password + "</td>";
						   	output += "<td>" + Email + "</td>";
						   	output += "<td>" + Address + "</td>";
							output += "<td>" + Dob + "</td>";
						   	output += "<td>" + phone + "</td>";	
				        
				        }
			              else {
			                output ="      Login Failed...!!";
			              	} 
				  	}
				
				con.close();
				
			}
			if (type.equals("manufacturer") || type.equals("Manufacturer")) {
				
				String query ="select manufacturerID,manufacturerCode,userName,password,email,address,dob,phone,manufacturer.desc from manufacturer where userName= '"+username+"'AND password= '"+password+"' ";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				ResultSet rs = ((java.sql.Statement) preparedStmt).executeQuery(query);
				
				
				  while (rs.next()) {
					    String userID = Integer.toString(rs.getInt("manufacturerID"));
					    String UserCode = rs.getString("manufacturerCode");
				        String UserName = rs.getString("userName");
				        String Password = rs.getString("password");
				        String Email = rs.getString("email");
				        String Address = rs.getString("address");
				        String Dob =rs.getString("dob");
				        String phone =rs.getString("phone");
				        String desc = rs.getString("desc");
				        
				  
				        if((username.equals(UserName)) && (password.equals(Password))) {
				        	
				        	output ="     Login Successful  !!           You're logged as "   +username;
				        	output += "<br><br><table border='1'><tr><th>Manufacturer ID</th><th>Manufacture Code</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th>" + "<th>description</th></tr>";
				        	output += "<tr><td>" + userID + "</td>";
				        	output += "<td>" + UserCode + "</td>";
						   	output += "<td>" + UserName + "</td>";
						   	output += "<td>" + password + "</td>";
						   	output += "<td>" + Email + "</td>";
						   	output += "<td>" + Address + "</td>";
							output += "<td>" + Dob + "</td>";
						   	output += "<td>" + phone + "</td>";	
							output += "<td>" + desc + "</td>";	
				        	}
			              else {
			                output ="      Login Failed...!!";
			            	 //output ="     Login Successful  !!           You're logged as"   +username;
			              	} 
				  	}
				
				con.close();
			}
			if (type.equals("researcher") || type.equals("Researcher")) {
				
				String query ="select researcherID,researcherCode,userName,password,email,address,dob,phone,profileInfo from researcher where userName= '"+username+"'AND password= '"+password+"' ";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				ResultSet rs = ((java.sql.Statement) preparedStmt).executeQuery(query);
				
				
				  while (rs.next()) {
					    String userID = Integer.toString(rs.getInt("researcherID"));
					    String UserCode = rs.getString("researcherCode");
				        String UserName = rs.getString("userName");
				        String Password = rs.getString("password");
				        String Email = rs.getString("email");
				        String Address = rs.getString("address");
				        String Dob =rs.getString("dob");
				        String phone =rs.getString("phone");
				        String profileInfo = rs.getString("profileInfo");
				        
				  
				        if((username.equals(UserName)) && (password.equals(Password))) {
				        	
				        	output ="     Login Successful  !!           You're logged as "   +username;
				        	output += "<br><br><table border='1'><tr><th>Researcher ID</th><th>Researcher Code</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th>" + "<th>profile Information</th></tr>";
				        	output += "<tr><td>" + userID + "</td>";
				          	output += "<td>" + UserCode + "</td>";
						   	output += "<td>" + UserName + "</td>";
						   	output += "<td>" + password + "</td>";
						   	output += "<td>" + Email + "</td>";
						   	output += "<td>" + Address + "</td>";
							output += "<td>" + Dob + "</td>";
						   	output += "<td>" + phone + "</td>";	
							output += "<td>" + profileInfo + "</td>";	
				        	}
			              else {
			                output ="      Login Failed...!!";
			            	 
			              	} 
				  	}
				
				con.close();
			}
			
		}catch (Exception e) {
			output = "Error while Logging.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
}