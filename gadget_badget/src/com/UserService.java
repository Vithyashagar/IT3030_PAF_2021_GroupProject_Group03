package com;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.User;
import authentication.userLogin;



@Path("/Users")
public class UserService {
	
	
	User userObj = new User();
	userLogin loginObj = new userLogin();
	String output;
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUsers()
	 {
	 return  userObj.readConsumers();
	 } 
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUser(@FormParam("type") String type,
	 @FormParam("userCode") String userCode,
	 @FormParam("userName") String userName,
	 @FormParam("password") String password,
	 @FormParam("email") String email,
	 @FormParam("address") String address,
	 @FormParam("dob") String dob,
	 @FormParam("phone") String phone,
	 @FormParam("desc") String desc,
	 @FormParam("profileInfo") String profileInfo
	 )
	{
		
		  output = userObj.insertUser(userCode,userName, password, email, address,dob,phone,type,desc,profileInfo);
		  return output;
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String userData)
	{
	//Convert the input string to a JSON object
	 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
	
	 //Read the values from the JSON object
	 String type = userObject.get("type").getAsString();
	 String userID = userObject.get("userID").getAsString();
	 String userName = userObject.get("userName").getAsString();
	 String password = userObject.get("password").getAsString();
	 String email = userObject.get("email").getAsString();
	 String address =userObject.get("address").getAsString();
	 String dob = userObject.get("dob").getAsString();
	 String phone = userObject.get("phone").getAsString();
	 String desc = userObject.get("desc").getAsString();
	 String profileInfo = userObject.get("profileInfo").getAsString();
	
	 String output = userObj.updateUser(type,userID, userName, password, email,address,dob,phone,desc,profileInfo);
	return output;
	}
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(String userData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(userData, "", Parser.xmlParser());
     String type = doc.select("type").text();
	//Read the value from the element <userID>
	 String userID = doc.select("userID").text();
	 String output = userObj.deleteUser(type,userID);
	return output;
	}
	
	

	@GET
	@Path("/{username}/{password}/{type}")
	@Produces(MediaType.TEXT_HTML)
	public String userLogin (@PathParam("username")String username,@PathParam("password")String password,@PathParam("type")String type) {
		
		String output = loginObj.userLoginview(username,password,type);
		return output;
		
		//user details for selected user
	}


}