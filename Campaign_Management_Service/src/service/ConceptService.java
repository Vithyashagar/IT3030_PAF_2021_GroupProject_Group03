package service;

import resources.ConceptAPI;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;


//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Concepts")
public class ConceptService {
	
	//concept API type object
	ConceptAPI conceptObj = new ConceptAPI();
	
	//Insert Function
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertConcept(@FormParam("conceptName") String conceptName,
							 @FormParam("conceptDesc") String conceptDesc,
						     @FormParam("startDate") String startDate,
						     @FormParam("deadline") String deadline,
						     @FormParam("pledgeGoal") String pledgeGoal,
						     @FormParam("reward") String reward,
						     @FormParam("workUpdt") String workUpdt,
						     @FormParam("researcherID") String researcherID,
						     @FormParam("manufactID") String manufactID)
	{
		String output = conceptObj.insertConcept(conceptName, conceptDesc, startDate, deadline, pledgeGoal, reward, workUpdt, researcherID,manufactID );
		return output;
	}

}
