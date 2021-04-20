package com;

import Model.PatentService;

//For Rest Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

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
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String insertPatent( @FormParam("Title") String Title,
								@FormParam("appDate") String appDate,
								@FormParam("ResearcherID") String ResearcherID,
								@FormParam("ConceptID") String ConceptID) {
		
		String output = PS.insertPatent(Title, appDate, ResearcherID, ConceptID);
		
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
	//create methods for specific requirements	
		
}
