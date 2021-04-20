package Model;

import java.sql.*;

import Security.Hashing;

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
	
	public String insertService(String Name, String Speciality, String Description, String MFRID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			//Checking Connection
			if(con == null) {
				return "Error while connecting to Database";		
			}
			
			//Hashing
			Hashing mh = new Hashing();
			
			String hName = mh.hashPassword(Name);
			String hSpeciality = mh.hashPassword(Speciality);
			
			//Preparing a CallableStatement to call a function
            CallableStatement cstmt = con.prepareCall("{? = call getManuID()}");
           
            //Registering the out parameter of the function (return type)
            cstmt.registerOutParameter(1, Types.CHAR);
           
            //Executing the statement
            cstmt.execute();
           
            //obtaining returned value of function(getPaymentID())
            String SeviceCode = cstmt.getString(1);
			
			
			//Create Prepared Statement
			String query = "INSERT INTO services(`SID`,`ServiceID`,`Name`,`Speciality`,`Description`, `MFRID`) VALUES(?,?,?,?,?,?)" ;
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//Binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, SeviceCode);
			preparedStmt.setString(3, hName);
			preparedStmt.setString(4, hSpeciality);
			preparedStmt.setString(5, Description);
			preparedStmt.setString(6, MFRID);
			
			//Execute the statement
			preparedStmt.execute();			
			
			//Table for Hash values 
			insertNameforkey(Name, hName);
			insertSpecforkey(Speciality, hSpeciality);
			
			//close connection
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
			
			String query = "select s.SID, s.ServiceID, t.nKey as Name, p.sKey as Speciality, s.Description, s.MFRID from services s, sname t, sspec p where s.Name = t.value and s.Speciality = p.Value";
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
			String query = "UPDATE services SET ServiceID=?,Name=?,Speciality=?,Description=? WHERE SID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//Hashing
			Hashing mh = new Hashing();
			
			String hName = mh.hashPassword(Name);
			String hSpeciality = mh.hashPassword(Speciality);
			
			// binding values
			preparedStmt.setString(1, ServiceID);
			preparedStmt.setString(2, hName);
			preparedStmt.setString(3, hSpeciality);
			preparedStmt.setString(4, Description);
			preparedStmt.setInt(5, Integer.parseInt(SID));
			
			////Table for Hash values 
			insertNameforkey(Name,  hName);
			insertSpecforkey(Speciality, hSpeciality);
			
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

	public int insertNameforkey(String Name, String hName) throws SQLException {
		
		Connection con = connect();
		
		//Making Key Value pairs
		//Name
		String query2 = "INSERT INTO sname(`id`, `nKey`, `Value`) VALUES(?,?,?)" ;
		PreparedStatement nameT  = con.prepareStatement(query2);
		//Binding values
		nameT.setInt(1, 0);
		nameT.setString(2, Name);
		nameT.setString(3, hName);
		
		//Execute the statement
		nameT.execute();
		
		return 0;
	}

	public int insertSpecforkey(String Speciality, String hSpeciality) throws SQLException {
		
		Connection con = connect();
		
		//Specialty
		String query3 = "INSERT INTO sspec(`id`, `sKey`, `Value`) VALUES(?,?,?)" ;
		PreparedStatement specT  = con.prepareStatement(query3);
		//Binding values
		specT.setInt(1, 0);
		specT.setString(2, Speciality);
		specT.setString(3, hSpeciality);
		
		//Execute the statement
		specT.execute();
		
		return 0;
	}

	public String readSpecificServices(String uID)
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
			
			String query = "SELECT s.SID, s.ServiceID, t.nKey as Name, p.sKey as Speciality, s.Description, s.MFRID "
						 + "FROM services s, sname t, sspec p "
						 + "WHERE s.Name = t.value and "
						 	  + " s.Speciality = p.Value and "
						 	  + " s.MFRID = '"+uID+"' "  ;
			
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
	
}