package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

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
	@FormParam("cvv") String cvv,
	@FormParam("ProductID") int ProductID,@FormParam("ConsumerID") int ConsumerID,
	@FormParam("ConceptID") int ConceptID,@FormParam("cardExpMonth") String cardExpMonth,
	@FormParam("cardExpYear") String cardExpYear
	)
	{
		
		String output = payObj.insertPayment(PaymentType, bank, paymentDate, cardNo,NameOnCard,cvv,ProductID,ConsumerID,ConceptID,cardExpMonth,cardExpYear);
		return output;
	}
	
	
	/*Handling concept status by verifying total pledgeAmount of a concept*/
	
	/*Updating data via a form and representing success as a plain text message */
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(@FormParam("ConceptID") int ConceptID)
	{
	
	String output = payObj.updatePaymentStatus(ConceptID);
	return output;
	}
	
	
	/*Delete backer payments related to incomplete projects*/
	/*Input is given as an xml format providing a plain text message as output*/
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String itemData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
	
	//Read the value from the element <status>
	
	String status = doc.select("status").text();
	String output = payObj.deletePayment(status);
	return output;
	}
}
