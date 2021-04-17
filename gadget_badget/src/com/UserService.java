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



@Path("/Users")
public class UserService {
	
	
	User userObj = new User();
	String output;
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUsers()
	 {
	 return  userObj.readUsers();
	 } 
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUser(@FormParam("type") String type,
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
		
		  output = userObj.insertUser(userName, password, email, address,dob,phone,type,desc,profileInfo);
		  return output;
	}
	

}
