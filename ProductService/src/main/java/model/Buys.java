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
	public String insertProductConsumer(int consumerID,int productID, int qty ){
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
		 	preparedStmt.setInt(1, consumerID);
		 	preparedStmt.setInt(2, productID);
		 	preparedStmt.setInt(3, qty);
		 	
		 	// execute the statement
		 	preparedStmt.execute();
		 	con.close();
		 	output = "Inserted successfully";
		 	 
	 }catch (Exception e){
		 
		 	 output = "Error while inserting the Product bought by customer";
		 	 System.err.println(e.getMessage());
		 	}
		 	
	 	return output;
	} 
	

	
	

}
