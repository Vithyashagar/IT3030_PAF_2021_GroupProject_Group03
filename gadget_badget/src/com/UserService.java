package com;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

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
	@Path("/{type}")
	@Produces(MediaType.TEXT_HTML)
	public String readUsers(@PathParam("type")String type)
	 {
	 return  userObj.readUsers(type);
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
	
	

/*	@GET
	@Path("/{username}/{password}/{type}")
	@Produces(MediaType.TEXT_HTML)
	public String userLogin (@PathParam("username")String username,@PathParam("password")String password,@PathParam("type")String type) {
		
		String output = loginObj.userLogin(username,password,type);
		return output;
		
		//user details for selected user
	}*/
	
	 //When log in to the system
    //System will create a cookie on their name 
    @GET
    @Path("/{username}/{password}/{type}")
    @Produces(MediaType.TEXT_HTML)
    public String userCLogin (@PathParam("username")String username,@PathParam("password")String password,@PathParam("type")String type) {
        
        NewCookie cookie = new NewCookie("UName", username );
        Response.ok("Ok").cookie(cookie).build();
        
        return loginObj.userLogin(username,password,type);
    }
    

 


	
	
	/************************************* ISC Communication ********************************************/
    //Get researcher id by passing user name
    @GET
    @Path("/getResearcherID/")
    @Produces(MediaType.APPLICATION_XML)
    public String readResearcherID(@QueryParam("username")String username) {
       
        String output = userObj.readResearcherID(username);
        return output;
       
    }
   
   
    //get manufacturer id by passing manufacturer user name
    @GET
    @Path("/getManufactID/")
    @Produces(MediaType.APPLICATION_XML)
    public String readManufactID(@QueryParam("username")String username) {
       
        String output = userObj.readManufactID(username);
        return output;
       
    }
   
   
    //get consumer id by passing consumer user name
        @GET
        @Path("/getConsumerID/")
        @Produces(MediaType.APPLICATION_XML)
        public String readConsumerID(@QueryParam("username")String username) {
           
            String output = userObj.readConsumerID(username);
            return output;
           
        }
        
        
        
     
        
        //Method to send data  to other services
        @GET
        //@Path("/sUserS/")
        @Produces(MediaType.APPLICATION_XML)
        public String readSpecificUserS(@QueryParam("ID")String ID){
            
            String uID =userObj.readSUser(ID);            
            return uID;

     

        } 
        
        @GET
        @Path("/address/")
        @Produces(MediaType.APPLICATION_XML)
        public String readAddress(@QueryParam("ID")String ID){
            
            String uID =userObj.readAddress(ID);            
            return uID;

     

        }
        
        

    	/************************************* ISC Communication ********************************************/

}
