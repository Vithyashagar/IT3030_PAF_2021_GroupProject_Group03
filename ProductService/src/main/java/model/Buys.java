package model;

import java.sql.*; 

public class Buys {
	
	
	
	/**********************DB Connect*****************************/
	//A common method to connect to the DB
	@SuppressWarnings("unused")
	private Connection connect()
	 {
		Connection con = null;
	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/productservice", "root", "");
	 
	 }
	 catch (Exception e)
	 {
		 	e.printStackTrace();
		 	}
	 
	 	return con;
	 } 
	
	
	/**********************Insert Buying*****************************/
	public String insertProductConsumer(String consumerID,String productID, String qty ){
		String output = "";
		
	 try{
		 Connection con = connect();
	
		 if (con == null)
		 {
			 return "Error while connecting to the database for inserting."; 
		 }
	 
		 	// create a prepared statement
		 	String query = "insert into buying (`consumerID`,`productID`,`qty`)"
		 			+ " values (?, ?, ?)";
	
		 	PreparedStatement preparedStmt = con.prepareStatement(query);
		
		 	// binding values
		 	preparedStmt.setString(1, consumerID);
		 	preparedStmt.setString(2, productID);
			preparedStmt.setInt(3, Integer.parseInt(qty));
		 	
		 	// execute the statement
		 	preparedStmt.execute();
		 	con.close();
		 	output = "Purchase Recoreded Successfully";
		 	 
	 }catch (Exception e){
		 
		 	 output = "Error while inserting the Product bought by customer";
		 	 System.err.println(e.getMessage());
		 	}
		 	
	 	return output;
	} 
	

	
/**********************Retrieve Buying*****************************/
	
	public String readProductConsumer()
	 {
			String output = "";
	 
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for reading."; 
				}
				
				
				// Prepare the html table to be displayed
				
				output = "<table border='1'><tr><th>Consumer ID</th>"+ 
						"<th>Product ID</th>" +
						"<th>Quantity</th></tr>";

					 String query = "select * from buying";
					 
					 Statement stmt = con.createStatement();
					 
					 ResultSet rs = stmt.executeQuery(query);
					 
					 // iterate through the rows in the result set
					 while (rs.next())
					 {
						 String consumerID = rs.getString("consumerID");
						 String productID = rs.getString("productID");
						 String qty = Integer.toString(rs.getInt("qty"));

						 
						 // Add into the html table
						 output += "<tr><td>" + consumerID + "</td>";
						 output += "<td>" + productID + "</td>";
						 output += "<td>" + qty + "</td></tr>";
						
					 }
					 con.close();
					
					// Complete the html table
					output += "</table>";
	 
			}catch (Exception e){
				
				output = "Error while reading the buying table";
				System.err.println(e.getMessage());
				
			}
			
			return output;
	 }

}
