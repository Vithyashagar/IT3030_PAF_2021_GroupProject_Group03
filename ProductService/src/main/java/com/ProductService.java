package com;

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


@Path("/Product") 
public class ProductService {
	
	Product ProductObj = new Product();
	
	//GET method of ProductService	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProduct()
	 {
	 return ProductObj.readProduct();
	 }
	
	
	
	//POST method of ProductService	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProduct(@FormParam("productCode") String productCode,
	 @FormParam("productName") String productName,
	 @FormParam("productPrice") String productPrice,
	 @FormParam("productDesc") String productDesc,
	 @FormParam("productCat") String productCat)
	{
	 String output = ProductObj.insertProduct(productCode, productName, productPrice, productDesc,productCat);
	 
	 	return output;
	}
	
	
	
	
	

}
