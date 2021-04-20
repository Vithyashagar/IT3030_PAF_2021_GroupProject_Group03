package model;

import java.sql.*;

public class User {
	
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user_service?useSSL=false", "root", "1234");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	public String readUsers(String type)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 
	 if (type.equals("consumer") || type.equals("Consumer")) {
	 
	 output = "<table border='1'><tr><th>Consumer ID</th><th>Consumer Code</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th>" +"<th>update</th>" + "<th>Delete</th></tr>";

	 String query = "select * from consumer";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String consumerID = Integer.toString(rs.getInt("userID"));
	 String consumerCode = rs.getNString("userCode");
	 String userName = rs.getString("userName");
	 String password = rs.getString("password");
	 String email = rs.getString("email");
	 String address = rs.getString("address");
	 String dob = rs.getString("dob");
	 String phone = rs.getString("phone");
	 
	 // Add into the html table
	 output += "<tr><td>" + consumerID + "</td>";
	 output += "<td>" + consumerCode + "</td>";
	 output += "<td>" + userName + "</td>";
	 output += "<td>" + password + "</td>";
	 output += "<td>" + email + "</td>";
	 output += "<td>" + address + "</td>";
	 output += "<td>" + dob + "</td>";
	 output += "<td>" + phone + "</td>";
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + consumerID
	 + "'>" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		 output = "<table border='1'><tr><th>Manufacture ID</th><th>Manufacture Code</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th>" +"<th>Description</th>"+"<th>update</th>" + "<th>Delete</th></tr>";

		 String query = "select * from manufacturer";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String manufacturerID = Integer.toString(rs.getInt("manufacturerID"));
		 String manufacturerCode = rs.getString("manufacturerCode");
		 String userName = rs.getString("userName");
		 String password = rs.getString("password");
		 String email = rs.getString("email");
		 String address = rs.getString("address");
		 String dob = rs.getString("dob");
		 String phone = rs.getString("phone");
		 String desc = rs.getString("desc");
		 
		 // Add into the html table
		 output += "<tr><td>" + manufacturerID + "</td>";
		 output += "<td>" + manufacturerCode + "</td>";
		 output += "<td>" + userName + "</td>";
		 output += "<td>" + password + "</td>";
		 output += "<td>" + email + "</td>";
		 output += "<td>" + address + "</td>";
		 output += "<td>" + dob + "</td>";
		 output += "<td>" + phone + "</td>";
		 output += "<td>" + desc + "</td>";
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + manufacturerID
		 + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
	  
	 
 if (type.equals("researcher") || type.equals("Researcher")) {
		 
		 output = "<table border='1'><tr><th>Researcher ID</th><th>Researcher Code</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th>" +"<th>profileInfo</th>"+"<th>update</th>" + "<th>Delete</th></tr>";

		 String query = "select * from researcher";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String researcherID = Integer.toString(rs.getInt("researcherID"));
		 String researcherCode = rs.getString("researcherCode");
		 String userName = rs.getString("userName");
		 String password = rs.getString("password");
		 String email = rs.getString("email");
		 String address = rs.getString("address");
		 String dob = rs.getString("dob");
		 String phone = rs.getString("phone");
		 String profile = rs.getString("profileInfo");
		 
		 // Add into the html table
		 output += "<tr><td>" + researcherID + "</td>";
		 output += "<td>" + researcherCode + "</td>";
		 output += "<td>" + userName + "</td>";
		 output += "<td>" + password + "</td>";
		 output += "<td>" + email + "</td>";
		 output += "<td>" + address + "</td>";
		 output += "<td>" + dob + "</td>";
		 output += "<td>" + phone + "</td>";
		 output += "<td>" + profile + "</td>";
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
		 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + researcherID
		 + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
	  
	 
	 
	 
	 
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the users.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	/*method to inert the user*/
	public String insertUser(String usercode ,String username, String password, String email, String address,String dob ,String phone, String type,String desc , String profileInfo)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 
	 // create a prepared statement
	 if (type.equals("consumer") || type.equals("Consumer")) {
		 
		 //Preparing a CallableStatement to call a function
		 CallableStatement cstmt = con.prepareCall("{? = call getConsumerID()}");
		 
		 //Registering the out parameter of the function (return type)
		 cstmt.registerOutParameter(1, Types.CHAR);
		
		 //Executing the statement
		 cstmt.execute();
		 String conceptCode = cstmt.getString(1);
		 
		 
		 
		 
	 String query = " insert into user_service.consumer(`userID`,`userCode`,`userName`,`password`,`email`,`address`,`dob`,`phone`)"
	 + " values (?, ?, ?, ?, ?, ?, ?, ?)";
	 
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, conceptCode);
	 preparedStmt.setString(3, username);
	 preparedStmt.setString(4, password);
	 preparedStmt.setString(5, email);
	 preparedStmt.setString(6, address);
	 preparedStmt.setString(7, dob);
	 preparedStmt.setString(8, phone);
	// execute the statement
	
	 preparedStmt.execute();
	 con.close();
	 output = " User Inserted successfully";
	
	 }
	 
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		 
		//Preparing a CallableStatement to call a function
		 CallableStatement cstmt = con.prepareCall("{? = call getManufacturerID()}");
		 
		 //Registering the out parameter of the function (return type)
		 cstmt.registerOutParameter(1, Types.CHAR);
		
		 //Executing the statement
		 cstmt.execute();
		 String conceptCode = cstmt.getString(1);
		 
		 String query = " insert into user_service.manufacturer(`manufacturerID`,`manufacturerCode`,`userName`,`password`,`email`,`address`,`dob`,`phone`,`desc`)"
				 + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				 
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 // binding values
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, conceptCode);
				 preparedStmt.setString(3, username);
				 preparedStmt.setString(4, password);
				 preparedStmt.setString(5, email);
				 preparedStmt.setString(6, address);
				 preparedStmt.setString(7, dob);
				 preparedStmt.setString(8, phone);
				 preparedStmt.setString(9, desc);
				// execute the statement
				
				 preparedStmt.execute();
				 con.close();
				 output = "User Inserted successfully";
	 }
	 
if (type.equals("researcher") || type.equals("Researcher")) {
	
	
	//Preparing a CallableStatement to call a function
	 CallableStatement cstmt = con.prepareCall("{? = call getResearcherID()}");
	 
	 //Registering the out parameter of the function (return type)
	 cstmt.registerOutParameter(1, Types.CHAR);
	
	 //Executing the statement
	 cstmt.execute();
	 String conceptCode = cstmt.getString(1);
		 
		 String query = " insert into user_service.researcher(`researcherID`,`researcherCode` ,`userName`,`password`,`email`,`address`,`dob`,`phone`,`profileInfo`)"
				 + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				 
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 // binding values
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, conceptCode);
				 preparedStmt.setString(3, username);
				 preparedStmt.setString(4, password);
				 preparedStmt.setString(5, email);
				 preparedStmt.setString(6, address);
				 preparedStmt.setString(7, dob);
				 preparedStmt.setString(8, phone);
				 preparedStmt.setString(9, profileInfo);
				// execute the statement
				
				 preparedStmt.execute();
				 con.close();
				 output = "User Inserted successfully";
	 }
	 
	
	 }
	
	 catch (Exception e)
	 {
	 output = "Error while inserting the user.";
	 System.err.println(e.getMessage());
	 }
	
	 return output;
	 
	 }
	

    /*method to update the user*/
	public String updateUser(String type , String userID, String userName, String password, String email, String address,String dob,String phone,String desc,String profileInfo)
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	
	 // create a prepared statement
	 
	 if (type.equals("consumer") || type.equals("Consumer")) {
	 String query = "UPDATE user_service.consumer SET userName=?,password=?,email=?,address=?,dob=?,phone=?where userID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	
	 preparedStmt.setString(1, userName); 
	 preparedStmt.setString(2, password); 
	 preparedStmt.setString(3, email); 
	 preparedStmt.setString(4, address); 
	 preparedStmt.setString(5, dob); 
	 preparedStmt.setString(6, phone); 
	 preparedStmt.setInt(7, Integer.parseInt(userID));
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Updated successfully"; 
	 } 
	 
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		 String query = "UPDATE manufacturer SET userName=?,password=?,email=?,address=?,dob=?,phone=?,manufacturer.desc=? WHERE manufacturerID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		
		 preparedStmt.setString(1, userName); 
		 preparedStmt.setString(2, password); 
		 preparedStmt.setString(3, email); 
		 preparedStmt.setString(4, address); 
		 preparedStmt.setString(5, dob); 
		 preparedStmt.setString(6, phone); 
		 preparedStmt.setString(7, desc);
		 preparedStmt.setInt(8, Integer.parseInt(userID));
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Updated successfully"; 
		 
	}
	if (type.equals("researcher") || type.equals("Researcher")) {
		 
		 String query = "UPDATE user_service.researcher SET userName=?,password=?,email=?,address=?,dob=?,phone=?,profileInfo=?where researcherID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		
		 preparedStmt.setString(1, userName); 
		 preparedStmt.setString(2, password); 
		 preparedStmt.setString(3, email); 
		 preparedStmt.setString(4, address); 
		 preparedStmt.setString(5, dob); 
		 preparedStmt.setString(6, phone); 
		 preparedStmt.setString(7, profileInfo);
		 preparedStmt.setInt(8, Integer.parseInt(userID));
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Updated successfully"; 
		 
	 }
	 
	 
	 
	 
	 }
	 catch (Exception e) 
	 { 
	 output = "Error while updating the user."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	
	/*method to delete the user*/
	public String deleteUser(String type,String userID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 
	 if (type.equals("consumer") || type.equals("Consumer")) {
		 
		 // create a prepared statement
		 String query = "delete from consumer where userID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(userID));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Consumer deleted successfully";
	 }
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		 // create a prepared statement
		 String query = "delete from manufacturer where manufacturerID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(userID));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Manufacturer deleted successfully";
	 }
	 if (type.equals("researcher") || type.equals("Researcher")) {
		 
		 // create a prepared statement
		 String query = "delete from researcher where researcherID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(userID));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Researcher deleted successfully";
	 }
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	 

}
