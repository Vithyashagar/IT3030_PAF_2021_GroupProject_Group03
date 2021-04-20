<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
     <%@page import = "util.DBConnection" %>
    <%@page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Connection Tester</title>
</head>
<body>


<% 
DBConnection dbConnect = new DBConnection();

Connection con = dbConnect.connect();

if(con == null){
	out.println("Connection failed");
}

else{
	out.println("Connection successful");
}
%>
</body>
</html>