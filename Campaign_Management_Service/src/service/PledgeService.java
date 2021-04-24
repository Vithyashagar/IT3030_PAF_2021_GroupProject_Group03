package service;

//For API
import resources.PledgeAPI;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

//Start of service
@Path("/Pledges")
public class PledgeService {
	
	
	PledgeAPI pledgeObj = new PledgeAPI();
	
	/** Inserting Pledge Amount to Backs table **/
	@POST
	@Path("/insertPledge/{consumername}/{conceptName}/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPledgeTransact(@PathParam("consumername") String consumername,
									   @PathParam("conceptName") String conceptName,
									   String pledgeData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(pledgeData, "", Parser.xmlParser());
				
		//Read the value from the element <pledged amount>
		String pledgedAmnt = doc.select("pledgedAmnt").text();
		//String output = conceptObj.deleteConcept(conceptID);
		
		//Call the method to get consumer ID from user service
		String backerID = getConsumerID(consumername);
		
		//Get the concept ID
		String conceptID = pledgeObj.getConceptID(conceptName);
		
		//Call insert method
		String output = pledgeObj.insertPledge(conceptID,backerID,pledgedAmnt);
		return output;
	}

	
	/** Viewing all pledged details **/
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPledges()
	{
		return pledgeObj.readPledges();
	}
	
	
	/** Viewing the pledges of a particular customer **/
	@GET
	@Path("/{backerID}")
	@Produces(MediaType.TEXT_HTML)
	public String helloName(@PathParam("backerID") String backerID)
	{
		return pledgeObj.readConsumerPledges(backerID);
	}
	
	
	
	/************************************ INTER SERVICE COMMUNICATION  ***********************************/
	//Method to get consumer ID from user service
	@GET
	@Path("/getConsumerDetails/{username}")
	@Produces(MediaType.TEXT_HTML)
	public String getConsumerID(@PathParam("username") String username){
		
		//Path to get the Consumer ID
		String path = "http://localhost:8083/gadget_badget/UserService/Users/getConsumerID/";
	       
	    //Create a client in Server to act as a client to another Server
        Client client = Client.create();
        
        //Creating the web resource
        WebResource target = client.resource(path);
       
        /*************** Testing the inter-service communication *******************************/
        //ClientResponse response = target.queryParam("username", username).accept("application/xml").get(ClientResponse.class);
        
        //Get the response String and save to a String(Response is a userID)
        String response = target.queryParam("username", username).accept("application/xml").get(String.class);
        
        //return readConsumerID(response.toString());
        return response.toString();	
	}
	
	
	/******************* CAMPAIGN SERVICE AS A SERVER FOR INTER SERVICE COMMUNICATION *****************************/
	//method to send researcherID to paymentService
    @GET
    @Path("/getConsumerID/")
    @Produces(MediaType.APPLICATION_XML)
    public String readSpecificConceptResearcher(@QueryParam("ConceptID")String ConceptID){
       
       return pledgeObj.readSpecificConsumerForConcept(ConceptID);           
      
    }

}
