package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Service.PaymentService;

@Path("/Payments") /*defining payment resource*/
public class PaymentResource {

	PaymentService payObj = new PaymentService(); //PaymentService instantiation
	
	/*Reading user data representing it as an HTML table*/
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return payObj.readPayments();
	}
	
	
	/*Handling user insert via a form and producing final output as plain text message*/
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("PaymentType") String PaymentType,
	@FormParam("bank") String bank,
	@FormParam("paymentDate") String paymentDate,
	@FormParam("cardNo") String cardNo,@FormParam("NameOnCard") String NameOnCard,
	@FormParam("cvv") String cvv,@FormParam("Buyerpayment") double Buyerpayment,
	@FormParam("ProductID") int ProductID,@FormParam("ConsumerID") int ConsumerID,
	@FormParam("ConceptID") int ConceptID,@FormParam("cardExpMonth") String cardExpMonth,
	@FormParam("cardExpYear") String cardExpYear
	)
	{
		
		String output = payObj.insertPayment(PaymentType, bank, paymentDate, cardNo,NameOnCard,cvv,Buyerpayment,ProductID,ConsumerID,ConceptID,cardExpMonth,cardExpYear);
		return output;
	}
}
