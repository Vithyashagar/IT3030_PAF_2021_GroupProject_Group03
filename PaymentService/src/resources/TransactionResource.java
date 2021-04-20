package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Service.TransactionService;

@Path("/Transaction") 
public class TransactionResource {

	TransactionService trans = new TransactionService();
	
/*****************Reading user data representing it as an HTML table************************/
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return trans.readBackerTransactions();
	}
	
	

	/*************Handling backer transaction insert via a form and producing final output as plain text message***********************/
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("conceptID") int conceptID,
	@FormParam("consumerID") int consumerID,
	@FormParam("pledgeAmount") double pledgeAmount
	)
	{
		/*Invoke insert method of transactionService class*/
		String output = trans.insertBackingTransaction(conceptID, consumerID, pledgeAmount);
		return output;
	}
	
}
