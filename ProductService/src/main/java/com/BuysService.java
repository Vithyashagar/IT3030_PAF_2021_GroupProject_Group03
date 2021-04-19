package com;

import model.Buys;
import model.Product;

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

Product ProductObj = new Product();
	
	//GET method of ProductService	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProduct()
	 {
	 return ProductObj.readProduct();
	 }
	
	
	
}
