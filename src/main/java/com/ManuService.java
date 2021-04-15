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
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String insertService(@FormParam("ServiceID") String ServiceID,
								@FormParam("Name") String Name,
								@FormParam("Speciality") String Speciality,
								@FormParam("Description") String Description,
								@FormParam("MFRID") String MFRID) {
		
		String output = MS.insertService(ServiceID, Name, Speciality, Description, MFRID);
		
		return output;
	}
	
}