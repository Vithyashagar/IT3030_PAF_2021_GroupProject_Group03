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
	
/*****************Reading backing data representing it as an HTML table************************/
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBacking()
	{
		return trans.readBackerTransactions();
	}
	
	

	/*************Handling backer transaction insert via a form and producing final output as plain text message***********************/
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBackerTransaction(@FormParam("conceptID") int conceptID,
	@FormParam("consumerID") int consumerID,
	@FormParam("pledgeAmount") double pledgeAmount
	)
	{
		/*Invoke backer insert method of transactionService class*/
		String output = trans.insertBackingTransaction(conceptID, consumerID, pledgeAmount);
		return output;
	}
	
	
/*****************Reading buyer transaction  data representing it as an HTML table************************/
	
	@GET
	@Path("/buyer")
	@Produces(MediaType.TEXT_HTML)
	public String readBuying()
	{
		return trans.readBuyerTransactions();
	}
	
/*************Handling buyer transaction insert via a form and producing final output as plain text message***********************/
	
	@POST
	@Path("/buyer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBuyerTransaction(@FormParam("consumerID") int consumerID,
	@FormParam("productID") int productID,
	@FormParam("qty") int qty
	)
	{
		/*Invoke buyer insert method of  transactionService class*/
		String output = trans.insertBuyingTransaction(consumerID, productID, qty);
		return output;
	}
	
}
