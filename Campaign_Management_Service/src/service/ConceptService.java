package service;

//For API
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


//Start of service
@Path("/Concepts")
public class ConceptService {
	
	//concept API type object
	ConceptAPI conceptObj = new ConceptAPI();
	
	
	/*** Viewing Concept details as a researcher(HTTP Verb : GET) by accepting researcher ID as input and produces an HTML Table ***/
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String readMyConcepts(String conceptData)
	{
		//Conversion of the input string to a JSON object
		JsonObject conceptObject = new JsonParser().parse(conceptData).getAsJsonObject();
		
		//Reading the values from the JSON object
		String reseracherID = conceptObject.get("researcherID").getAsString();
		
		//Read the concept details and return them
		return conceptObj.readMyConcepts(reseracherID);
		
	}
	
	
	/*** Insert Function(HTTP Verb : POST) by getting the input values through forms and produces a plain text as output ***/
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
	
	
	
	/*** Update Function(HTTP Verb : PUT) Using JSON and produces a plain text as output result***/
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateConcept(String conceptData)
	{
		//Convert the input string to a JSON object
		JsonObject conceptObject = new JsonParser().parse(conceptData).getAsJsonObject();
		
		//Read the values from the JSON object
		String conceptID = conceptObject.get("conceptID").getAsString();
		String conceptName = conceptObject.get("conceptName").getAsString();
		String conceptDesc = conceptObject.get("conceptDesc").getAsString();
		String pledgeGoal = conceptObject.get("pledgeGoal").getAsString();
		String reward = conceptObject.get("reward").getAsString();
		String workUpdt = conceptObject.get("workUpdt").getAsString();
		
		String output = conceptObj.updateConcept(conceptID, conceptName, conceptDesc, pledgeGoal, reward, workUpdt);
		
		return output;
	}
	
	
	/*** DELETE FUNCTION (HTTP Verb : DELETE) using XML and produces plain text as output results ***/
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteConcept(String conceptData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(conceptData, "", Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String conceptID = doc.select("conceptID").text();
		String output = conceptObj.deleteConcept(conceptID);
		
		return output;
	}
	
	
	
	/*** Viewing Concept details as a consumer(HTTP Verb : GET) by an HTML table ***/
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAllConcepts()
	{
		return conceptObj.readAllConcepts();
	}
	

}
