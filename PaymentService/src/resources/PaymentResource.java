package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Service.PaymentService;

@Path("/Payments") /*defining payment resource*/
public class PaymentResource {

	PaymentService payObj = new PaymentService(); //PaymentService instantiation
	
	/*****************Reading user data representing it as an HTML table************************/
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return payObj.readPayments();
	}
	
	/*@GET
	@Path("/concept/{name}")
	@Produces(MediaType.APPLICATION_XML)
	public String readItems(@PathParam("name") String name)
	{
		return payObj.readPaymentsPath(name);
	}*/
	
	
	
	
	/*************Handling user insert via a form and producing final output as plain text message***********************/
	
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
		/*Invoke insert method of paymentService class*/
		String output = payObj.insertPayment(PaymentType, bank, paymentDate, cardNo,NameOnCard,cvv,ProductID,ConsumerID,ConceptID,cardExpMonth,cardExpYear);
		return output;
	}
	
	
	
	
	/*************************Handling concept status by verifying total pledgeAmount of a concept************************/
	
	/******************Updating data via a form and representing success as a plain text message *************************/
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(@FormParam("ConceptID") int ConceptID)
	{
	
		//invoke updateMethod from paymentService
		String output = payObj.updatePaymentStatus(ConceptID);
		
		return output;
	}
	
	
	/*******************************Delete backer payments related to incomplete projects******************************/
	/***************************Input is given as an xml format providing a plain text message as output**************/
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData)
	{
		
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());
		
		//Read the value from the element <status>
	
			String status = doc.select("status").text();
			
		//invoke deletePayment method from service class.
			String output = payObj.deletePayment(status);
			
			return output;
	}
	
	
	/********************Updating data via JSON format and representing success as a plain text message****************/
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePaymentDetails(String paymentData)
	{
	
		//Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		
		//Read the values from the JSON object
		String paymentID = paymentObject.get("paymentID").getAsString();
		String paymentType = paymentObject.get("paymentType").getAsString();
		String bank = paymentObject.get("bank").getAsString();
		String cardNo = paymentObject.get("cardNo").getAsString();
		String NameOnCard = paymentObject.get("NameOnCard").getAsString();
		String cvv = paymentObject.get("cvv").getAsString();
		String cardExpMonth = paymentObject.get("cardExpMonth").getAsString();
		String cardExpYear = paymentObject.get("cardExpYear").getAsString();
		
		//invoke deletePaymentMethod from service class
		
		String output = payObj.updatePaymentDetails(paymentID, paymentType, bank, cardNo, NameOnCard,cvv,cardExpMonth,cardExpYear);
	
		return output;
	}
}
