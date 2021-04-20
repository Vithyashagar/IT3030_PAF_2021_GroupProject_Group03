package service;

//For API
import resources.PledgeAPI;

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
@Path("/Pledges")
public class PledgeService {
	
	
	PledgeAPI pledgeObj = new PledgeAPI();
	
	
	/** Inserting Pledge Amount to Backs table **/
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertConcept(@FormParam("conceptID") String conceptID,
							 @FormParam("backerID") String backerID,
							 @FormParam("pledgedAmnt") String pledgedAmnt)
	{
		String output = pledgeObj.insertPledge(conceptID,backerID,pledgedAmnt);
		return output;
	}

}
