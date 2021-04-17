package model;

import java.sql.*; 

public class Product {
	
	
	
	/**********************DB Connect*****************************/
	//A common method to connect to the DB
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

	
	
	
	
	/**********************Insert Product*****************************/
	public String insertProduct(String productCode, String productName, String productPrice, String productDesc,String productCat){
		String output = "";
		
	 try{
		 Connection con = connect();
	
		 if (con == null)
		 {
			 return "Error while connecting to the database for inserting."; 
		 }
	 
		 	// create a prepared statement
		 	String query = " insert into product (`productId`,`productCode`,`productName`,`productPrice`,`productDesc`,`productCat`)"
		 			+ " values (?, ?, ?, ?, ?, ?)";
	
		 	PreparedStatement preparedStmt = con.prepareStatement(query);
		
		 	// binding values
		 	preparedStmt.setInt(1, 0);
		 	preparedStmt.setString(2, productCode);
		 	preparedStmt.setString(3, productName);
		 	preparedStmt.setDouble(4, Double.parseDouble(productPrice));
		 	preparedStmt.setString(5, productDesc);
		 	preparedStmt.setString(6, productCat);
		 	
		 	// execute the statement
		 	preparedStmt.execute();
		 	con.close();
		 	output = "Inserted successfully";
		 	 
	 }catch (Exception e){
		 
		 	 output = "Error while inserting the item.";
		 	 System.err.println(e.getMessage());
		 	}
		 	
	 	return output;
	} 
	

	
	
	

}
