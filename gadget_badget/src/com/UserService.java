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
	
	userLogin loginObj = new userLogin();
	
	/*@GET
	@Path("/UsersAuthentication/{username}/{phoneNo}")
	@Produces(MediaType.TEXT_HTML)
	public String userLogin (@PathParam("username")String username,@PathParam("phoneNo")String phoneNo) {
		return loginObj.userLogin(username,phoneNo);
		//appointments details for selected user
	}*/
	
	
	User userObj = new User();
	
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
	public String insertUser(@FormParam("userName") String userName,
	 @FormParam("password") String password,
	 @FormParam("email") String email,
	 @FormParam("address") String address,
	 @FormParam("dob") String dob,
	 @FormParam("PHONE") String phone
	 )
	{
	 String output = userObj.insertUser(userName, password, email, address,dob,phone);
	return output;
	}

}
