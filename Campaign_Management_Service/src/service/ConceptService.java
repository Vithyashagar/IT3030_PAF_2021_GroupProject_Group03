package service;

//For API
import resources.ConceptAPI;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;
import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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
	@Path("/{researcherID}")
	@Produces(MediaType.TEXT_HTML)
	public String helloName(@PathParam("researcherID") String researcherID)
	{
		return conceptObj.readMyConcepts(researcherID);
	}
	
	
	/*** Insert Function(HTTP Verb : POST) by getting the input values through forms and produces a plain text as output ***/
	@POST
	@Path("/insert/{researcherName}/{manufactName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertConcept(@PathParam("researcherName") String researcherName,
							 @PathParam("manufactName") String manufactName,
							 @FormParam("conceptName") String conceptName,
							 @FormParam("conceptDesc") String conceptDesc,
						     @FormParam("startDate") String startDate,
						     @FormParam("deadline") String deadline,
						     @FormParam("pledgeGoal") String pledgeGoal,
						     @FormParam("reward") String reward,
						     @FormParam("workUpdt") String workUpdt)
	{
		//Getting the researcher ID by calling the client method
		String researcherID = getResearcherID(researcherName);
		
		//Getting manufacturer ID by calling the client method
		String manufactID = getManufactID(manufactName);
		
		//Calling the insert method
		String output = conceptObj.insertConcept(conceptName, conceptDesc, startDate, deadline, pledgeGoal, reward, workUpdt, researcherID,manufactID );
		return output;
	}
	
	
	/*** Update Function(HTTP Verb : PUT) Using JSON and produces a plain text as output result***/
	@PUT
	@Path("/update/{conceptCode}/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateConceptDetails(@PathParam ("conceptCode") String conceptCode,
			String conceptData)
	{
		//Convert the input string to a JSON object
		JsonObject conceptObject = new JsonParser().parse(conceptData).getAsJsonObject();
		
		//Read the values from the JSON object
		//String conceptID = conceptObject.get("conceptID").getAsString();
		String conceptName = conceptObject.get("conceptName").getAsString();
		String conceptDesc = conceptObject.get("conceptDesc").getAsString();
		String pledgeGoal = conceptObject.get("pledgeGoal").getAsString();
		String reward = conceptObject.get("reward").getAsString();
		String workUpdt = conceptObject.get("workUpdt").getAsString();
		
		//calling the update method
		String output = conceptObj.updateConcept(conceptCode, conceptName, conceptDesc, pledgeGoal, reward, workUpdt);
		
		return output;
	}
	
	
	
	/*** DELETE FUNCTION (HTTP Verb : DELETE) using XML and produces plain text as output results ***/
	@DELETE
	@Path("/delete/{conceptCode}/")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteConceptDetails(@PathParam ("conceptCode") String conceptCode)
	{
		//Calling the delete method
		String output = conceptObj.deleteConcept(conceptCode);
		
		return output;
	}
	
	
	
	/*** Viewing All Concept details as a consumer/Researcher(HTTP Verb : GET) by an HTML table ***/
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAllConcepts()
	{
		return conceptObj.readAllConcepts();
	}
	
	
   /************************************** INTER SERVICE COMMUNICATION ********************************************/
	//Method to get the researcher ID from user service
	@GET
	@Path("/getResearcherDetails/{username}")
	@Produces(MediaType.TEXT_HTML)
	public String getResearcherID(@PathParam("username") String username){
		
		//Path to get the Researcher ID
		String path = "http://localhost:8083/gadget_badget/UserService/Users/getResearcherID/";
	       
	    //Create a client in Server to act as a client to another Server
        Client client = Client.create();
        
        //Creating the web resource
        WebResource target = client.resource(path);
       
        /*************** Testing the inter-service communication *******************************/
        //ClientResponse response = target.queryParam("username", username).accept("application/xml").get(ClientResponse.class);
        
        //Get the response String and save to a String(Response is a userID)
        String response = target.queryParam("username", username).accept("application/xml").get(String.class);
        
        //return readResearcherID(response.toString());
        return response.toString();	
	}
	

	//Method to get the manufacturer ID from user service
	@GET
	@Path("/getManufactDetails/{username}")
	@Produces(MediaType.TEXT_HTML)
	public String getManufactID(@PathParam("username") String username){
		
		//Path to get the Manufacturer ID
		String path = "http://localhost:8083/gadget_badget/UserService/Users/getManufactID/";
	       
	    //Create a client in Server to act as a client to another Server
        Client client = Client.create();
        
        //Creating the web resource
        WebResource target = client.resource(path);
       
        /*************** Testing the inter-service communication *******************************/
        //ClientResponse response = target.queryParam("username", username).accept("application/xml").get(ClientResponse.class);
        
        //Get the response String and save to a String(Response is a userID)
        String response = target.queryParam("username", username).accept("application/xml").get(String.class);
        
        //return readManufacturerID(response.toString());
        return response.toString();	
	}
	
	
	/***************************** CAMPAIGN SERVICE AS SERVER FOR INTER SERVICE COMMUNICATION **********************************/
	
	//Method to send conceptID  to paymentService
    @GET 
    @Path("/conceptCheck/")
    @Produces(MediaType.APPLICATION_XML)
    public String readSpecificBackerPayment(@QueryParam("ConceptName")String ConceptName){
       
       return conceptObj.readSpecificConceptIDForPayment(ConceptName);           
      
    }
   

}
