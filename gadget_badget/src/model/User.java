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
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gatget_badget_userservice?useSSL=false", "root", "1234");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	public String insertUser(String username, String password, String email, String address,String dob ,String phone)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 // create a prepared statement
	 String query = " insert into gatget_badget_userservice.consumer(`userID`,`userName`,`password`,`email`,`address`,`dob`,`phone`)"
	 + " values (?, ?, ?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, username);
	 preparedStmt.setString(3, password);
	 preparedStmt.setString(4, email);
	 preparedStmt.setString(5, address);
	 preparedStmt.setString(6, dob);
	 preparedStmt.setString(7, phone);
	// execute the statement
	
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while inserting the user.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	public String readUsers()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Consumer ID</th><th>User Name</th>" +"<th>Password</th>" +"<th> Gmail</th>" +"<th>Address</th>"+"<th>DOB</th>" + "<th>phone</th>" +"<th>update</th>" + "<th>Delete</th></tr>";

	 String query = "select * from consumer";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String consumerID = Integer.toString(rs.getInt("userID"));
	 String userName = rs.getString("userName");
	 String password = rs.getString("password");
	 String email = rs.getString("email");
	 String address = rs.getString("address");
	 String dob = rs.getString("dob");
	 String phone = rs.getString("phone");
	 
	 // Add into the html table
	 output += "<tr><td>" + consumerID + "</td>";
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
	 catch (Exception e)
	 {
	 output = "Error while reading the users.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	
	public String updateItem(String ID, String code, String name, String price, String desc)
	
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 String query = "UPDATE items SET itemCode=?,itemName=?,itemPrice=?,itemDesc=?WHERE itemID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, code);
	 preparedStmt.setString(2, name);
	 preparedStmt.setDouble(3, Double.parseDouble(price));
	 preparedStmt.setString(4, desc);
	 preparedStmt.setInt(5, Integer.parseInt(ID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	public String deleteItem(String itemID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from items where itemID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(itemID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 } 
	
	

}
