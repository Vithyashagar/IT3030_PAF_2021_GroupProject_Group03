package com;

import Model.ManufacturerService;

//For Rest Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;



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
	public String insertService(@FormParam("ServiceID") String ServiceID,
								@FormParam("Name") String Name,
								@FormParam("Speciality") String Speciality,
								@FormParam("Description") String Description,
								@FormParam("MFRID") String MFRID) {
		
		String output = MS.insertService(ServiceID, Name, Speciality, Description, MFRID);
		
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
	

	
}