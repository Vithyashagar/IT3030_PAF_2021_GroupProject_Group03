package model;

import java.sql.*;

import security.Hashing;

public class User {
	
	
	 /******************************Establish Connection method****************************************/
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user_service?useSSL=false&allowPublicKeyRetrieval=true", "root", "1234");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	 /*********************end of method***********************/
	
	
	

	 /******************************Read all users according to user type****************************************/
	public String readUsers(String type)
	 {
		String output = "";

	 try
	 {
	
		 Connection con = connect();
	
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; }
	
	 
	 /*********************Prepare the html table for the consumer to be displayed***********************/
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
	 
	// final String col = "<td>";  // Compliant



	 
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
	 
	 /*********************Prepare the html table for the manufacturer to be displayed***********************/
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		// Prepare the html table to be displayed
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
	  
	 
	 /*********************Prepare the html table for the researcher to be displayed***********************/
       if (type.equals("researcher") || type.equals("Researcher")) {
		 
	 	// Prepare the html table to be displayed
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
	
	 /*********************end of read users method***********************/
	
	
	
	
	
	 /*********************Method to insert users***********************/
	public String insertUser(String usercode ,String username, String password, String email, String address,String dob ,String phone, String type,String desc , String profileInfo)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 
	 /*********************insert consumer details***********************/
	 if (type.equals("consumer") || type.equals("Consumer")  ) {
		 
		 
		 /******************************datahashing process********************************************************************/
			
			Hashing hs = new Hashing();
			
			String UserName = hs.hashPassword(username);
			String h_password = hs.hashPassword(password);
			
		 
		 
		 
		    if(username != "" && password != "") {
		 
		    //Preparing a CallableStatement to call a function
		    CallableStatement cstmt = con.prepareCall("{? = call getConsumerID()}");
		 
		    //Registering the out parameter of the function (return type)
		    cstmt.registerOutParameter(1, Types.CHAR);
		
		    //Executing the statement
		    cstmt.execute();
		    String conceptCode = cstmt.getString(1);
		 
		 
		    //call email validation method
		    boolean emailValidate = validateEmail(email);
		 
		 
		 
		    if(emailValidate ) {
		    	String query = " insert into user_service.consumer(`userID`,`userCode`,`userName`,`password`,`email`,`address`,`dob`,`phone`)"
		    	+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
	 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, conceptCode);
			 preparedStmt.setString(3, UserName);
			 preparedStmt.setString(4, h_password);
			 preparedStmt.setString(5, email);
			 preparedStmt.setString(6, address);
			 preparedStmt.setString(7, dob);
			 preparedStmt.setString(8, phone);
			// execute the statement
	
			 preparedStmt.execute();
			 
			 insertuserNameforkey(username, UserName);
			 insertPasswordforkey(password, h_password);
	 
	 
			 con.close();
			 output = " User Inserted successfully";
			
	 } else {
		 output = " invalid email";}
    }
		 
    else {
			 
			 output = " User Name or password cannot be null ";
		 }
}
	 
 /***************************insert manufacturer details********************************/
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		 
		 /******************************data hashing process********************************************************************/
			
			Hashing hs = new Hashing();
			
			String h_userName = hs.hashPassword(username);
			String h_password = hs.hashPassword(password);
		 
				if(username != "" && password != "") {
		 
					//Preparing a CallableStatement to call a function
					CallableStatement cstmt = con.prepareCall("{? = call getManufacturerID()}");
		 
					//Registering the out parameter of the function (return type)
					cstmt.registerOutParameter(1, Types.CHAR);
		
					//Executing the statement
					cstmt.execute();
					String conceptCode = cstmt.getString(1);
		 
					boolean emailValidate = validateEmail(email);

		 
					if(emailValidate ) {
		 
						String query = " insert into user_service.manufacturer(`manufacturerID`,`manufacturerCode`,`userName`,`password`,`email`,`address`,`dob`,`phone`,`desc`)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				 
							PreparedStatement preparedStmt = con.prepareStatement(query);
							// binding values
							 preparedStmt.setInt(1, 0);
							 preparedStmt.setString(2, conceptCode);
							 preparedStmt.setString(3, h_userName);
							 preparedStmt.setString(4, h_password);
							 preparedStmt.setString(5, email);
							 preparedStmt.setString(6, address);
							 preparedStmt.setString(7, dob);
							 preparedStmt.setString(8, phone);
							 preparedStmt.setString(9, desc);
							// execute the statement
							
							 preparedStmt.execute();
							 
							 insertuserNameforkey(username, h_userName);
							 insertPasswordforkey(password, h_password);
							 
							 con.close();
							 output = "User Inserted successfully";}
		 
					else {
			 
						output = " invalid email";
					}
				} else {
			 
					output = " User Name or password cannot be null ";
				}
		 
	 }
	 
	 /*********************insert researcher detials***********************/
	 if (type.equals("researcher") || type.equals("Researcher")) {
	
		 /******************************data hashing process********************************************************************/
	
		 	Hashing hs = new Hashing();
	
		 	String h_userName = hs.hashPassword(username);
		 	String h_password = hs.hashPassword(password);
		 
		 	if(username != "" && password != "") {
	
		 		//Preparing a CallableStatement to call a function
		 		CallableStatement cstmt = con.prepareCall("{? = call getResearcherID()}");
	 
		 		//Registering the out parameter of the function (return type)
		 		cstmt.registerOutParameter(1, Types.CHAR);
	
		 		//Executing the statement
		 		cstmt.execute();
		 		String conceptCode = cstmt.getString(1);
	 
		 		boolean emailValidate = validateEmail(email);
		 
		 		if(emailValidate) {
		 			String query = " insert into user_service.researcher(`researcherID`,`researcherCode` ,`userName`,`password`,`email`,`address`,`dob`,`phone`,`profileInfo`)"
		 			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				 
		 			PreparedStatement preparedStmt = con.prepareStatement(query);
			 			// binding values
			 		 preparedStmt.setInt(1, 0);
			 		 preparedStmt.setString(2, conceptCode);
					 preparedStmt.setString(3, h_userName);
					 preparedStmt.setString(4, h_password);
					 preparedStmt.setString(5, email);
					 preparedStmt.setString(6, address);
					 preparedStmt.setString(7, dob);
					 preparedStmt.setString(8, phone);
					 preparedStmt.setString(9, profileInfo);
					// execute the statement
					
					 preparedStmt.execute();
					 
					 insertuserNameforkey(username, h_userName);
					 insertPasswordforkey(password, h_password);
					 
					 con.close();
					 output = "User Inserted successfully";
	} else {
		 
		 output = " Invalid email";
	 }
				
}

	else {
		 
		 output = " User Name or password cannot be null ";
	 }

}
	 
	 
	 
	 }
	 	catch (Exception e)
	 	{
	 		output = "Error while inserting the user.";
	 		System.err.println(e.getMessage());
	
	     }
	
	 return output;

	 }
	
	 /*********************end of insert user method***********************/
	
	
	
	
	

	 /*********************update user method ***********************/
	public String updateUser(String type , String userID, String userName, String password, String email, String address,String dob,String phone,String desc,String profileInfo)
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	

	 /*********************update consumer  ***********************/
	 if (type.equals("consumer") || type.equals("Consumer")) {
		 
		 /******************************datahashing process********************************************************************/
			
			Hashing hs = new Hashing();
			
			String h_userName = hs.hashPassword(userName);
			String h_password = hs.hashPassword(password);
			
		 
		 
				 if(userName != "" && password != ""  ) {
				 String query = "UPDATE user_service.consumer SET userName=?,password=?,email=?,address=?,dob=?,phone=?where userID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				 preparedStmt.setString(1, h_userName); 
				 preparedStmt.setString(2, h_password); 
				 preparedStmt.setString(3, email); 
				 preparedStmt.setString(4, address); 
				 preparedStmt.setString(5, dob); 
				 preparedStmt.setString(6, phone); 
				 preparedStmt.setInt(7, Integer.parseInt(userID));
				 // execute the statement
				 preparedStmt.execute(); 
				 
				 insertuserNameforkey(userName, h_userName);
				 insertPasswordforkey(password, h_password);
				 
				 con.close(); 
				 output = "Updated successfully"; 
				 }else {
		 
				 output = "Error !! field should not be empty"; 
				 }
	 		} 
	 
	 /*********************update manufacturer ***********************/
	 if (type.equals("manufacturer") || type.equals("Manufacturer")) {
		 
		 /******************************datahashing process********************************************************************/
			
			Hashing hs = new Hashing();
			
			String h_userName = hs.hashPassword(userName);
			String h_password = hs.hashPassword(password);
		 
					 if(userName != "" && password != "" &&  desc != ""  ) {
					 
					 String query = "UPDATE manufacturer SET userName=?,password=?,email=?,address=?,dob=?,phone=?,manufacturer.desc=? WHERE manufacturerID=?"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					
					 preparedStmt.setString(1, h_userName); 
					 preparedStmt.setString(2, h_password); 
					 preparedStmt.setString(3, email); 
					 preparedStmt.setString(4, address); 
					 preparedStmt.setString(5, dob); 
					 preparedStmt.setString(6, phone); 
					 preparedStmt.setString(7, desc);
					 preparedStmt.setInt(8, Integer.parseInt(userID));
					 // execute the statement
					 preparedStmt.execute(); 
					 
					 insertuserNameforkey(userName, h_userName);
					 insertPasswordforkey(password, h_password);
					 
					 con.close(); 
					 output = "Updated successfully"; 
			 }else {
					 
					 output = "Error !! field should not be empty"; 
				 }
		 
	       }
	 
	 
	 /*********************update researcher ***********************/
	if (type.equals("researcher") || type.equals("Researcher")) {
		
		 /******************************datahashing process********************************************************************/
		
		Hashing hs = new Hashing();
		
		String h_userName = hs.hashPassword(userName);
		String h_password = hs.hashPassword(password);
		 
					if(userName != "" && password != "" &&  profileInfo != "" ) {
					 String query = "UPDATE user_service.researcher SET userName=?,password=?,email=?,address=?,dob=?,phone=?,profileInfo=?where researcherID=?"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					
					 preparedStmt.setString(1, h_userName); 
					 preparedStmt.setString(2, h_password); 
					 preparedStmt.setString(3, email); 
					 preparedStmt.setString(4, address); 
					 preparedStmt.setString(5, dob); 
					 preparedStmt.setString(6, phone); 
					 preparedStmt.setString(7, profileInfo);
					 preparedStmt.setInt(8, Integer.parseInt(userID));
					 // execute the statement
					 preparedStmt.execute(); 
					 
					 insertuserNameforkey(userName, h_userName);
					 insertPasswordforkey(password, h_password);
					 
					 con.close(); 
					 output = "Updated successfully"; 
				}else {
					 
					 output = "Error !! field should not be empty"; 
				 }
		 
	       }
	 
	 
	 }
	 catch (Exception e) 
	 { 
	 output = "Error while updating the user."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	 /*********************update user method ***********************/
	
	
	
	
	
	 /*********************delete user method ***********************/
	public String deleteUser(String type,String userID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 
	 
	 /*********************delete consumer  ***********************/
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
	 /*********************delete manufacturer  ***********************/
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
	 /*********************delete researcher  ***********************/
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
	 output = "Error while deleting the user.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
/*********************delete user method ***********************/	
	
/*******************************methods to manage hashing tables**********************************************************/
	
	
    public int insertuserNameforkey(String userName, String h_userName) throws SQLException {
		
		Connection con = connect();
		
		//Making Key Value pairs
		//Name
		String query1 = "INSERT INTO h_username(`id`, `nKey`, `nvalue`) VALUES(?,?,?)" ;
		PreparedStatement preparedStmt  = con.prepareStatement(query1);
		//Binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, userName);
		preparedStmt.setString(3, h_userName);
		
		//Execute the statement
		preparedStmt.execute();
		
		return 0;
	}  
    
    public int insertPasswordforkey(String pass, String h_password) throws SQLException {
		
		Connection con = connect();
		
		//Making Key Value pairs
		//Name
		String query1 = "INSERT INTO h_password(`id`, `nKey`, `nvalue`) VALUES(?,?,?)" ;
		PreparedStatement preparedStmt  = con.prepareStatement(query1);
		//Binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, pass);
		preparedStmt.setString(3, h_password);
		
		//Execute the statement
		preparedStmt.execute();
		
		return 0;
	}
    
   
   
   public boolean validateEmail(String email) {
	   
	   
	      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      return email.matches(regex);
   }
   
   /********************* end of methods ***********************/
   
   
   /****************************** ISC Communication ***********************************/
   //Method to read researcher ID
   public String readResearcherID(String username) {
       //String output = "";
       String researcherID = "";
      
       try
       {
           Connection con = connect();
           if (con == null)
           {
               return "Database Connection failed!!";
           }
          
           String query = "select * from researcher where userName = '"+username+"' ";
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery(query);
          
           // iterate through the rows in the result set
           while (rs.next())
           {
               //researcherID = Integer.toString(rs.getInt("researcherID"));
               researcherID = rs.getString("researcherCode");
               //System.out.println(researcherID);
           }
               con.close();
               //output += researcherID;
              
           }
           catch (Exception e)
           {
               System.err.println(e.getMessage());
           }
           return researcherID;
   }
  
  
   //Method to read manufacturer ID
   public String readManufactID(String username) {
      
       String manufactID = "";
      
       try
       {
           Connection con = connect();
           if (con == null)
           {
               return "Database Connection failed!!";
           }
          
           String query = "select * from manufacturer where userName = '"+username+"' ";
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery(query);
          
           // iterate through the rows in the result set
           while (rs.next())
           {
               manufactID = rs.getString("manufacturerCode");
              
           }
               con.close();
              
           }
           catch (Exception e)
           {
               System.err.println(e.getMessage());
           }
           return manufactID;
   }
  
  
   //Method to read consumer ID
   public String readConsumerID(String username) {
          
           String consumerID = "";
          
           try
           {
               Connection con = connect();
               if (con == null)
               {
                   return "Database Connection failed!!";
               }
              
               String query = "select * from consumer where userName = '"+username+"' ";
               Statement stmt = con.createStatement();
               ResultSet rs = stmt.executeQuery(query);
              
               // iterate through the rows in the result set
               while (rs.next())
               {
                   consumerID = rs.getString("userCode");
                  
               }
                   con.close();
                  
               }
               catch (Exception e)
               {
                   System.err.println(e.getMessage());
               }
               return consumerID;
       }
	
   
   
 
   
   
   
   //read userID by userName
   public String readSUser(String uCode) {

   String output = "";

   try {
   Connection con = connect();

   if (con == null){
   return "Error while connecting to the database for reading.";
   }

   String query = "select * from manufacturer where userName = '"+uCode+"' ";
   Statement stmt = con.createStatement();

   ResultSet rs = stmt.executeQuery(query);
   String ID = null;




   while (rs.next()){

   ID = rs.getString("manufacturerCode");

   }



   con.close();

   output += ID;

   } catch (SQLException e) {

   e.printStackTrace();
   }



   return output;

   }



   //read Address by userName
   public String readAddress(String iD) {
   String output = "";

   try {
   Connection con = connect();

   if (con == null){
   return "Error while connecting to the database for reading.";
   }

   String query = "select * from researcher where userName = '"+iD+"' ";
   Statement stmt = con.createStatement();

   ResultSet rs = stmt.executeQuery(query);
  


   String address = null;

   while (rs.next()){

   address = rs.getString("address");

   }



   con.close();

   output += address;

   } catch (SQLException e) {

   e.printStackTrace();
   }



   return output;



   }
   
   
 //Method to read consumerCode for BuyerService Inter Service Communication
   public String readConsumerCode(String username) {
          
	   String output = "";
          
           try
           {
               Connection con = connect();
               if (con == null)
               {
                   return "Database Connection failed!!";
               }
              
               String query = "select * from consumer where userName = '"+username+"' ";
               Statement stmt = con.createStatement();
               ResultSet rs = stmt.executeQuery(query);
              
               // iterate through the rows in the result set
              String userCode = null;
               while (rs.next())
               {
            	   userCode = rs.getString("userCode");
                  
               }
                   con.close();
                  output += userCode; 
               }
               catch (Exception e)
               {
                   System.err.println(e.getMessage());
               }
               return output;
       }
   
   
	
}
	 


