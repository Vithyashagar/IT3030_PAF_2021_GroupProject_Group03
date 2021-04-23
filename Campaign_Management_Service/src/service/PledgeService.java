package service;

//For API
import resources.PledgeAPI;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;
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
	@Path("/insert/{consumername}/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPledge(@PathParam("consumername") String consumername,
							 @FormParam("conceptID") String conceptID,
							 @FormParam("pledgedAmnt") String pledgedAmnt)
	{
		String backerID = getConsumerID(consumername);
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
	
	
	
	/************************* GETTING THE CONSUMER ID FROM USER SERVICE *****************************/
	@GET
	@Path("/getConsumerDetails/{username}")
	@Produces(MediaType.TEXT_HTML)
	public String getConsumerID(@PathParam("username") String username){
		
		//Path to get the Researcher Name
		String path = "http://localhost:8083/gadget_badget/UserService/Users/getConsumerID/";
	       
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

}
