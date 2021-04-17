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
	

	
	
	
	
/**********************Retrieve Product*****************************/
	
	public String readProduct()
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
				
				output = "<table border='1'><tr><th>Product Code</th>"+ 
						"<th>Product Name</th>" +
						"<th>Product Price</th>" +
						"<th>Product Description</th>" +
						"<th>Product Category</th>" +
						"<th>Update</th><th>Remove</th></tr>";

					 String query = "select * from product";
					 
					 Statement stmt = con.createStatement();
					 
					 ResultSet rs = stmt.executeQuery(query);
					 
					 // iterate through the rows in the result set
					 while (rs.next())
					 {
						 String productId = Integer.toString(rs.getInt("productId"));
						 String productCode = rs.getString("productCode");
						 String productName = rs.getString("productName");
						 String productPrice = Double.toString(rs.getDouble("productPrice"));
						 String productDesc = rs.getString("productDesc");
						 String productCat = rs.getString("productCat");
						 
						 // Add into the html table
						 output += "<tr><td>" + productCode + "</td>";
						 output += "<td>" + productName + "</td>";
						 output += "<td>" + productPrice + "</td>";
						 output += "<td>" + productDesc + "</td>";
						 output += "<td>" + productCat + "</td>";
						 
						 // buttons
						 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						 + "<td><form method='post' action='product.jsp'>"
						 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='itemID' type='hidden' value='" + productId
						 + "'>" + "</form></td></tr>";
						
					 }
					 con.close();
					
					// Complete the html table
					output += "</table>";
	 
			}catch (Exception e){
				
				output = "Error while reading the products";
				System.err.println(e.getMessage());
				
			}
			
			return output;
	 }

	
	
	/**********************Update Product*****************************/
	public String updateProduct(String productId, String productCode, String productName, String productPrice, String productDesc,String productCat)
	{
		 String output = "";
		 
		 try
		 {
			 Connection con = connect();
		 
			 if (con == null)
			 {
				 return "Error while connecting to the database for updating."; 
			 }
			 
			 	// create a prepared statement
			 	String query = "UPDATE product SET productCode=?,productName=?,productPrice=?,productDesc=?,productCat=? WHERE productId=?";
			 	PreparedStatement preparedStmt = con.prepareStatement(query);
		 
			 	// binding values
				 preparedStmt.setString(1, productCode);
				 preparedStmt.setString(2, productName);
				 preparedStmt.setDouble(3, Double.parseDouble(productPrice));
				 preparedStmt.setString(4, productDesc);
				 preparedStmt.setString(5, productCat);
				 preparedStmt.setInt(6, Integer.parseInt(productId));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 output = "Updated successfully";
		 
		 }catch (Exception e){
			 
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
		 
		 }
		 	return output;
		 } 

	
	
	
	/**********************Delete Product*****************************/
	public String deleteProduct(String productId){
		
		String output = "";
	
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting."; 
			}
			
			// create a prepared statement
			String query = "delete from product where productId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(productId));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 output = "Deleted successfully";
		
		}catch (Exception e){
			
			output = "Error while deleting the product.";
			System.err.println(e.getMessage());
			
		}
		
			return output;
	 }
	

	

}
