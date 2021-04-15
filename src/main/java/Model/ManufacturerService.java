package Model;

import java.sql.*;

public class ManufacturerService {

	
	public Connection connect(){
		
		Connection con = null;
		
		try{
				
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/manufacturerservices","root", "M@ng@th@9093");

			//For Testing
			System.out.println("Successfully connected");
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertService(String ServiceID, String Name, String Speciality, String Description, String MFRID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			//Checking Connection
			if(con == null) {
				return "Error while connecting to Database";		
			}
			
			//Create Prepared Statement
			String query = "INSERT INTO services(`SID`,`ServiceID`,`Name`,`Speciality`,`Description`, `MFRID`) VALUES(?,?,?,?,?,?)" ;
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//Binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ServiceID);
			preparedStmt.setString(3, Name);
			preparedStmt.setString(4, Speciality);
			preparedStmt.setString(5, Description);
			preparedStmt.setString(6, MFRID);
			
			//Execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Service Created Succesfully";
			
			
		}catch(Exception e) {
			output = "Error while Inserting Service";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readServices()
	{
		String output = "";
		try{
			
			Connection con = connect();
			
			//Checking Connection
			if (con == null){
				return "Error while Connecting to the Database for Reading.";
			}
			
			// Prepare the HTML table to be displayed
			output = "<table border='1'>"
						+ "<tr>"
							+ "<th>Name</th>"
							+ "<th>Item Name</th>"
							+ "<th>Speciality</th>"
							+ "<th>Description</th>"
							+ "<th>Manufacturer</th>"
							+ "<th>Update</th>"
							+ "<th>Remove</th>"
						+ "</tr>";
			
			String query = "select * from services";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
		
				String SID = Integer.toString((rs.getInt("SID")));
				String ServiceID = rs.getString("ServiceID");
				String Name = rs.getString("Name");
				String Speciality = rs.getString("Speciality");
				String Description = rs.getString("Description");
				String MFRID = rs.getString("MFRID");
				
				// Add a row into the HTML table
				output += "<tr><td>" + ServiceID + "</td>";
				output += "<td>" + Name + "</td>";
				output += "<td>" + Speciality + "</td>";
				output += "<td>" + Description + "</td>";
				output += "<td>" + MFRID + "</td>";
				
				// buttons
				output += "<td>"
							+ "<form method='post' action='updateService.jsp'>"
								+ "<input name='btnUpdate' type='submit' value='Update' class='btn btn-warning'>"
								+ "<input name='SID' type='hidden' value=' " + SID + "'>"
								+ "<input name='ServiceID' type='hidden' value=' " + ServiceID + "'>"
								+ "<input name='Name' type='hidden' value=' " + Name + "'>"
								+ "<input name='Speciality' type='hidden' value=' " + Speciality + "'>"
								+ "<input name='Description' type='hidden' value=' " + Description + "'>"
								+ "<input name='MFRID' type='hidden' value=' " + MFRID + "'>"
							+ "</form></td>"
						+ "<td>"
							+ "<form method='post' action='Service.jsp'>"
								+ "<input name='btnRemove' type='submit' value='Delete' class='btn btn-danger'>"
								+ "<input name='itemID' type='hidden' value='" + SID + "'>"
							+ "</form>"
						+ "</td></tr>";
				
			}
			
			con.close();
			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e){
			output = "Error while reading the Services.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}

	public String deleteService(String SID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			//Checking Connection
			if(con == null) {
				return "Error while connecting to Database";
			}
			
			//create prepared statement
			String query = "delete from services where SID = ? ";
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.valueOf(SID));
			
			preparedStmt.execute();
			con.close();
			
			output = "Service Deleted";
			
			
		}catch(Exception e) {
			output = "Error while Deleting Service";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}

	public String updateService( String SID, String ServiceID, String Name, String Speciality, String Description )  
	{
		String output = "";
		
		try{
			Connection con = connect();
			
			//Checking Connection
			if (con == null){
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE services SET ServiceID=?,Name=?,Speciality=?,Description=?WHERE SID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, ServiceID);
			preparedStmt.setString(2, Name);
			preparedStmt.setString(3, Speciality);
			preparedStmt.setString(4, Description);
			preparedStmt.setInt(5, Integer.parseInt(SID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}
		
		catch (Exception e){		
			output = "Error while updating the Service";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}