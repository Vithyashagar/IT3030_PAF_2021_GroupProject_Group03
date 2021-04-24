package resources;

//import statements.
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
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.xml.sax.InputSource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import Service.PaymentService;

/***********API MANAGING THE RESOURCE OF THE SERVICE******************************/

@Path("/Payments") //defining payment resource.
public class PaymentResource {

	PaymentService payObj = new PaymentService(); //PaymentService instantiation.
	
	/*****************Reading all user data representing it as an HTML table.************************/
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return payObj.readPayments();
	}
	
	/*****************Reading specific user data representing it as an HTML table.************************/
	
	@GET
	@Path("/specificUser/{NameOnCard}")
	@Produces(MediaType.TEXT_HTML)
	public String readSpecificUserPayments(@PathParam("NameOnCard") String NameOnCard)
	{
		return payObj.readSpecificUserPayments(NameOnCard);
	}
	
	/*************Handling backer payment insert via a form and producing final output as plain text message.***********************/
	
	@POST
	@Path("/backerinsert/{ConceptName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBackerPayment(@PathParam("ConceptName") String ConceptName,@FormParam("PaymentType") String PaymentType,
	@FormParam("bank") String bank,
	@FormParam("paymentDate") String paymentDate,
	@FormParam("cardNo") String cardNo,@FormParam("NameOnCard") String NameOnCard,
	@FormParam("cvv") String cvv,
	@FormParam("cardExpMonth") String cardExpMonth,
	@FormParam("cardExpYear") String cardExpYear
	)
	{
		//method to obtain relevant concept and consumer by communicating with Campaign service.
		String ConceptID  = getConceptID(ConceptName);
		String ConsumerID = getbackerID(ConceptID);
		
		//Invoke insert method of paymentService class.
		
		String output = payObj.insertBackerPayment(ConsumerID,ConceptID,PaymentType, bank, paymentDate, cardNo,NameOnCard,cvv,cardExpMonth,cardExpYear);
		return output;
	}
	
	
	/*************Handling buyer payment insert via a form and producing final output as plain text message.***********************/
	
	@POST
	@Path("/buyerinsert/test/{ProductName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBuyerPayment(@PathParam("ProductName") String ProductName,@FormParam("PaymentType") String PaymentType,
	@FormParam("bank") String bank,
	@FormParam("paymentDate") String paymentDate,
	@FormParam("cardNo") String cardNo,@FormParam("NameOnCard") String NameOnCard,
	@FormParam("cvv") String cvv,
	@FormParam("cardExpMonth") String cardExpMonth,
	@FormParam("cardExpYear") String cardExpYear
	)
	{
		//method to obtain relevant product and consumer by communicating with Product service.
		
		  String ProductID = getProductID(ProductName);
		  String ConsumerID = getConsumerID(ProductID);
		 
		//Invoke buyer insert method of paymentService class.
		String output = payObj.insertBuyerPayment(ConsumerID,ProductID,PaymentType, bank, paymentDate, cardNo,NameOnCard,cvv,cardExpMonth,cardExpYear);
		return output;
	}
	
	
	/*************************Handling concept status by verifying total pledgeAmount of a concept.************************/
	
	/******************Updating data via a form and representing success as a plain text message. *************************/
	@PUT
	@Path("/updateStatus/{ConceptName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(@PathParam("ConceptName") String ConceptName)
	{
		//Obtain relevant concept to be verified from Concept Service.
		String ConceptID  = getConceptID(ConceptName);
	
		//invoke updateMethod from paymentService.
		String output = payObj.updatePaymentStatus(ConceptID);
		
		return output;
	}
	
	
	/*******************************Delete backer payments related to incomplete projects.******************************/
	/***************************Input is given as an xml format providing a plain text message as output.**************/
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData)
	{
		
		//Convert the input string to an XML document.
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());
		
		//Read the value from the element <status>.
	
			String status = doc.select("status").text();
			
		//invoke deletePayment method from service class.
			String output = payObj.deletePayment(status);
			
			return output;
	}
	
	
	/********************Updating data via JSON format and representing success as a plain text message.****************/
	
	@PUT
	@Path("/paymentDetailUpdate/{paymentCode}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePaymentDetails(@PathParam("paymentCode") String paymentCode , String paymentData)
	{
	
		//Convert the input string to a JSON object.
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		
		//Read the values from the JSON object.
		
		
		//String paymentID = paymentObject.get("paymentID").getAsString();
		//String paymentCode = paymentObject.get("paymentCode").getAsString();
		String paymentType = paymentObject.get("paymentType").getAsString();
		String bank = paymentObject.get("bank").getAsString();
		String cardNo = paymentObject.get("cardNo").getAsString();
		String NameOnCard = paymentObject.get("NameOnCard").getAsString();
		String cvv = paymentObject.get("cvv").getAsString();
		String cardExpMonth = paymentObject.get("cardExpMonth").getAsString();
		String cardExpYear = paymentObject.get("cardExpYear").getAsString();
		
		//invoke deletePaymentMethod from service class.
		
		String output = payObj.updatePaymentDetails(paymentCode, paymentType, bank, cardNo, NameOnCard,cvv,cardExpMonth,cardExpYear);
	
		return output;
	}
	
	
	/*########################################CLIENT PAYMENT - SERVER CONCEPT SERVICE.#####################*/
	
	
		/**************************Client method to recieve concept id from capaign service*********************************************************/
		
		
		//Method to get Concept ID from Campaign service .
			@GET
			@Path("/backer/{ConceptName}")
			@Produces(MediaType.TEXT_HTML)
			public String getConceptID(@PathParam("ConceptName") String ConceptName){
				
				//Path to get the Concept Name.
				String path = "http://localhost:8080/Campaign_Management_Service/ConceptService/Concepts/conceptCheck/";
			       
			    //Create a client r to act as a client for another Server.
		        Client client = Client.create();
		        
		        //Creating the web resource.
		        WebResource target = client.resource(path);
		       
		        //To check the response if working.
		        //ClientResponse response = target.queryParam("ConceptName", ConceptName).accept("application/xml").get(ClientResponse.class);
		       
		        //Get the response String and save to a String(Response is a ConceptID).
		        String response = target.queryParam("ConceptName", ConceptName).accept("application/xml").get(String.class);
		        
		        //According to the conceptName get backer details.	
		    	
		    	return response.toString();
		    	
			}
			
			
			/**************************Client method to recieve consumerID  from campaign service.*********************************************************/
			
			
			//Method to get ConsumerID from Campaign service.
				@GET
				@Path("/consumer/{ConceptID}")
				@Produces(MediaType.TEXT_HTML)
				public String getbackerID(@PathParam("ConceptID") String ConceptID){
					
					//Path to get the Manufacturer Name.
					String path = "http://localhost:8080/Campaign_Management_Service/ConceptService/Concepts/getConsumerID/";
				       
				    //Create a client r to act as a client for another Server.
			        Client client = Client.create();
			        
			        //Creating the web resource.
			        WebResource target = client.resource(path);
			       
			        //To check the response if working.
			        //ClientResponse response = target.queryParam("ConceptID", ConceptID).accept("application/xml").get(ClientResponse.class);
			       
			        //Get the response String and save to a String(Response is a ConsumerID).
			        String response = target.queryParam("ConceptID", ConceptID).accept("application/xml").get(String.class);
			       
			    	
			    	return response.toString();
			    	
				}
				
				
	/*########################################END OF CLIENT PAYMENT - SERVER CONCEPT SERVICE.#####################*/
				
				
	/*########################################CLIENT PAYMENT - SERVER PRODUCT SERVICE.#####################*/			
		
				//Method to get Product ID from Product service.
				@GET
				@Path("/buyer/{ProductName}")
				@Produces(MediaType.TEXT_HTML)
				public String getProductID(@PathParam("ProductName") String ProductName){
					
					//Path to get the Product Name.
					String path = "http://localhost:8080/ProductService/ProductService/Product/productCheck/";
				       
				    //Create a client r to act as a client for another Server.
			        Client client = Client.create();
			        
			        //Creating the web resource.
			        WebResource target = client.resource(path);
			       
			        //To check the response if working.
			        //ClientResponse response = target.queryParam("ProductName", ProductName).accept("application/xml").get(ClientResponse.class);
			       
			        //Get the response String and save to a String(Response is a productID).
			        String response = target.queryParam("ProductName", ProductName).accept("application/xml").get(String.class);
			        	
			    	
			    	return response.toString();
			    	
				}
				
				
				/**************************Client method to recieve consumerID  from product service*********************************************************/
				
				
				//Method to get ConsumerID from Campaign service .
					@GET
					@Path("/buyconsumer/{ProductID}")
					@Produces(MediaType.TEXT_HTML)
					public String getConsumerID(@PathParam("ProductID") String ProductID){
						
						//Path to get the Product Name.
						String path = "http://localhost:8080/ProductService/ProductService/Product/getConsumerID/";
					       
					    //Create a client r to act as a client for another Server.
				        Client client = Client.create();
				        
				        //Creating the web resource.
				        WebResource target = client.resource(path);
				       
				        //To check the response if working.
				        //ClientResponse response = target.queryParam("ProductID", ProductID).accept("application/xml").get(ClientResponse.class);
				       
				        //Get the response String and save to a String(Response is a ConsumerID).
				        String response = target.queryParam("ProductID", ProductID).accept("application/xml").get(String.class);
				        
				    	
				    	return response.toString();
				    	
					}			
				
	
	
}
