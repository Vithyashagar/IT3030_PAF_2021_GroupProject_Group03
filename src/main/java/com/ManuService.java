package com;

import Model.ManufacturerService;

//For Rest Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

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
	
}