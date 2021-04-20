package com;

import Model.ManufacturerService;

import java.util.Map;

//For Rest Services
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Path("/Service")
public class ManuService {
	
	//Creation of Service Object 
	ManufacturerService MS = new ManufacturerService();
		
	//Method to Read the services
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readServices() {
		
		return MS.readServices();
	}
	
	//Method to Insert the services
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String insertService(@FormParam("Name") String Name,
								@FormParam("Speciality") String Speciality,
								@FormParam("Description") String Description,
								@FormParam("MFRID") String MFRID) {
		
		String output = MS.insertService(Name, Speciality, Description, MFRID);
		
		return output;
	}
	
	//Method to update the services
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateService(String serviceData){
		
		//Convert Input String to a JSON Object
		JsonObject MSObj = new JsonParser().parse(serviceData).getAsJsonObject();
		
		//Read values from the JSON Object
		String SID = MSObj.get("SID").getAsString();
		String ServiceID = MSObj.get("ServiceID").getAsString();
		String Name = MSObj.get("Name").getAsString();
		String Speciality = MSObj.get("Speciality").getAsString();
		String Description = MSObj.get("Description").getAsString();
		
		//Updating the Service
		String output = MS.updateService(SID, ServiceID, Name, Speciality, Description);
		
		return output;
	}
	
	//Method to Delete the services
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String deleteService(String serviceData) {
		
		//Convert Input String to a JSON Object
		JsonObject MSObj = new JsonParser().parse(serviceData).getAsJsonObject();
		
		//Read values from the JSON Object
		String SID = MSObj.get("SID").getAsString();
		
		//Deleting the Service
		String output = MS.deleteService(SID);
		
		return output;
	}
	
	//Method to get a specific Manufacturer service
	@GET
	@Path("/Manufacturer/{ID}")
	@Produces(MediaType.TEXT_HTML)
	public String readSpecificManufacturerServices(@PathParam("ID") String ID) {
		
		String output= MS.readSpecificServices(ID);
		
		return output;
	}
	
	
	//Method to get details from other service 
	@GET
	@Path("/getDetails/{ID}")
	@Produces(MediaType.TEXT_HTML)
	public String getUserName(@PathParam("ID") String ID){
		
		//Path to get the Manufacturer Name
		String path = "http://localhost:8080/gadget_badget/UserService/Users/";
	       
	    //Create a client in Server to act as a client to another Server
        Client client = Client.create();
        //Creating the web resource
        WebResource target = client.resource(path);
       
        //To check the response if working
        // ClientResponse response = target.queryParam("ID", ID).accept("application/xml").get(ClientResponse.class);
       
        //Get the response String and save to a String
        String response = target.queryParam("ID", ID).accept("application/xml").get(String.class);
        
        //According to the user name get Manufacturer details
    	return readSpecificManufacturerServices(response.toString());
	
	}
	
	
	//returns cookie UName's value
	@GET
	@Path("/cookie")
	@Produces(MediaType.TEXT_PLAIN)
	public String readCookie1(@CookieParam("UName") String UName) {
				
		return  UName;
		
	}
	
	
}