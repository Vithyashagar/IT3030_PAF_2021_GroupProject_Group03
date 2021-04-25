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
	 @FormParam("productCat") String productCat,
	 @FormParam("productQty") String productQty)
	
	{
		String output = ProductObj.insertProduct(productName, productPrice, productDesc,productCat,productQty);
	 
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
	 String productQty = ProductObject.get("productQty").getAsString();
	 String output = ProductObj.updateProduct(productId, productCode, productName, productPrice, productDesc,productCat,productQty);
	return output;
	}
	
	//Delete method product Service
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProduct(String productData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(productData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String productId = doc.select("productId").text();
	 String output = ProductObj.deleteProduct(productId);
	return output;
	}
	
	/************************ISC method***************************************/
    //Method to send productID  to paymentService
    @GET /*Add a path for original concepts get method else therel be CONFUUUUUUUSSSSIIIOOOOONNNNN*/
    @Path("/productCheck/")
    @Produces(MediaType.APPLICATION_XML)
    public String readSpecificBuyerPayment(@QueryParam("ProductName")String ProductName){
       
       return ProductObj.readSpecificProductIDForPayment(ProductName);           
      
    }
   
    //method to send consumerID to paymentService
    @GET /*Add a path for original concepts get method else therel be CONFUUUUUUUSSSSIIIOOOOONNNNN*/
    @Path("/getConsumerID/")
    @Produces(MediaType.APPLICATION_XML)
    public String readSpecificProductConsumer(@QueryParam("ProductID")String ProductID){
       
       return ProductObj.readSpecificBuyerForProduct(ProductID);           
      
    }

}
