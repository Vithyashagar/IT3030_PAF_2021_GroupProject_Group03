package com;

import model.Buys;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

//For JSON
import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Buying") 
public class BuysService {

	Buys BuysObj = new Buys();
	
	//GET method of ProductService	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProductConsumer()
	 {
	 return BuysObj.readProductConsumer();
	 }
	
	
	
	/** Inserting Pledge Amount to Backs table **/
	@POST
	@Path("/insertConsumerProductBuys/{username}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPledgeTransact(@PathParam("username") String username,
			@FormParam("productCode") String productCode,
			@FormParam("qty") String qty)
	{
		
		//Call the method to get consumer Code from user service
		String consumerCode = getConsumerID(username);
		
		
		//Call insert method
		String output = BuysObj.insertProductConsumer(consumerCode,productCode,qty);
		return output;
	}


	
	
	/************************************ INTER SERVICE COMMUNICATION  ***********************************/
	//Method to get consumer ID from user service
	@GET
	@Path("/getConsumerCode/{username}")
	@Produces(MediaType.TEXT_HTML)
	public String getConsumerID(@PathParam("username") String username){

		
		//Path to get the Consumer Code
		String path = "http://localhost:8001/gadget_badget/UserService/Users/getConsumerCode/";
		

	    //Create a client in Server to act as a client to another Server
        Client client = Client.create();
        
        //Creating the web resource
        WebResource target = client.resource(path);
       
       
        //Get the response String and save to a String(Response is a username)
        String response = target.queryParam("username", username).accept("application/xml").get(String.class);

      
        return response.toString();	
	}
	
	

	
	
	
}
