package Service;

import java.sql.Connection;
import java.sql.DriverManager;

public class PaymentService {

	public Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paymentdb","root", "1234");
			
			//For testing
			
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
}
