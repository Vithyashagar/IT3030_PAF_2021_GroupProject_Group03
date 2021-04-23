package com;

import Model.PatentService;

//For Rest Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//For JSON
import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/Patent")
public class Patent {

	//Creation of Service Object 
	PatentService PS = new PatentService();
			
	//Method to Read the Patent Applications
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPatents() {
		
		return PS.readPatent();
	}
		
	//Method to Insert the Patent Application Info
	@POST
	@Path("/{uName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String insertPatent( @PathParam("uName") String uName,
								@FormParam("Title") String Title,
								@FormParam("appDate") String appDate) {
		
		String output = PS.insertPatent(Title, appDate, uName);
		
		return output;
	}
	
	//Method to Delete the services
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String deletePatent(String patentData) {
		
		//Convert Input String to a JSON Object
		JsonObject MSObj = new JsonParser().parse(patentData).getAsJsonObject();
		
		//Read values from the JSON Object
		String PID = MSObj.get("PID").getAsString();
		
		//Deleting the Service
		String output = PS.deletePatent(PID);
		
		return output;
	}
	
	//Read conceptDescripion from Campaign Management
	@GET
	@Path("/ConceptDesc/{conceptName}")
	public String  getConceptDescription(@PathParam("conceptName") String conceptName){
		//Server path
		String path = "http://localhost:8080/Campaign_Management_Service/ConceptService/Concepts/conceptDescription/";
		
		//create a client
		Client client = Client.create();
		WebResource target = client.resource(path);
		
		//retrieve details from server
		String conceptDesc = target.queryParam("conceptName", conceptName).accept("application/xml").get(String.class);
		
		//return resource
		return conceptDesc;
		
		//return response
		//ClientResponse response = target.queryParam("conceptName", conceptName).accept("application/xml").get(ClientResponse.class);
		 
		// return response;
	}
	
	//Read conceptDescripion from Campaign Management
	@GET
	@Path("/ConceptID/{conceptName}")
	public String  getConceptID(@PathParam("conceptName") String conceptName){
		//Server path
		String path = "http://localhost:8080/Campaign_Management_Service/ConceptService/Concepts/conceptID/";
		
		//create a client
		Client client = Client.create();
		WebResource target = client.resource(path);
		
		//retrieve details from server
		String conceptID = target.queryParam("conceptName", conceptName).accept("application/xml").get(String.class);
		
		//return resource
		return conceptID;
		
		//return response
		//ClientResponse response = target.queryParam("conceptName", conceptName).accept("application/xml").get(ClientResponse.class);
		 
		// return response;
	}
	
	//Get Address by passing name
	@GET
	@Path("/Reasearcher/{Uname}")
	@Produces(MediaType.TEXT_HTML)
	public String getManufacturerAddress(@PathParam("Uname") String Uname){
		
		//Path to get the Manufacturer Name
		String path = "http://localhost:8080/gadget_badget/UserService/Users/address/";
	       
	    //Create a client in Server to act as a client to another Server
        Client client = Client.create();
        
        //Creating the web resource
        WebResource target = client.resource(path);
       
        //To check the response if working
        //ClientResponse response = target.queryParam("ID", Uname).accept("application/xml").get(ClientResponse.class);
       
        //Get the response String and save to a String(Response is a userID)
        String response = target.queryParam("ID", Uname).accept("application/xml").get(String.class);
        
        //According to the userID get Manufacturer details
    	return response.toString();	
	}

	//Method to Read the Patent Applications for a specific user
		@GET
		@Path("/{name}/")
		@Produces(MediaType.TEXT_HTML)
		public String readSPatents(@PathParam("name") String name) {
			
			return PS.readSPatent(name);
		}
	
	
}
