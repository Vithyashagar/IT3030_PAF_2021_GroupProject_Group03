package util;

import java.sql.*;

public class DBConnection {
	
	public Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/campaign_service","root", "Shahimaria@123");
					
			//Testing the connection
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}

}
