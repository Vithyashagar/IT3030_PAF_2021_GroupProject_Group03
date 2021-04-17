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
	
	
	
	//PUT method of ProductService	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProduct(String productData)
	{
	//Convert the input string to a JSON object
	 JsonObject ProductObject = new JsonParser().parse(productData).getAsJsonObject();
	//Read the values from the JSON object
	 String productId = ProductObject.get("productId").getAsString();
	 String productCode = ProductObject.get("productCode").getAsString();
	 String productName = ProductObject.get("productName").getAsString();
	 String productPrice = ProductObject.get("productPrice").getAsString();
	 String productDesc = ProductObject.get("productDesc").getAsString();
	 String productCat = ProductObject.get("productCat").getAsString();
	 String output = ProductObj.updateProduct(productId, productCode, productName, productPrice, productDesc,productCat);
	return output;
	}
	
	

}
