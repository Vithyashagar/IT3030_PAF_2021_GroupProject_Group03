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
	public String insertPatent(@FormParam("PatentID") String PatentID,
								@FormParam("Title") String Title,
								@FormParam("appDate") String appDate,
								@FormParam("ResearcherID") String ResearcherID,
								@FormParam("ConceptID") String ConceptID) {
		
		String output = PS.insertPatent(PatentID, Title, appDate, ResearcherID, ConceptID);
		
		return output;
	}
	
	
		
		
}
