package com;

import model.Buys;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Buying") 
public class BuysService {

	Buys BuysObj = new Buys();
	
	//GET method of ProductService	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProductConsumer()
	 {
	 return BuysObj.readProductConsumer();
	 }
	
	
	
}
