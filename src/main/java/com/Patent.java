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
			
	//Method to Read the services
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readServices() {
		
		return PS.readPatent();
	}
		
	
	
}
