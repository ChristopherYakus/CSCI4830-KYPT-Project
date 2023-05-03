package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*;

import util.DBConnection;
import util.DBController;
import util.DBResults;

@WebServlet("/SearchEvent")
public class SearchEvent extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public SearchEvent() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		DBController DB = new DBController(getServletContext());
		ArrayList<DBResults> rs;
		  System.out.println(request.getParameter("searchTitle"));
		  String loggedInUser = "";
		  loggedInUser += request.getSession().getAttribute("user");
		  // If no user is logged in, the accessible events are holidays.
		  if (loggedInUser.equals("null"))
		  {
			  rs = DB.get("(title LIKE \"" + request.getParameter("searchTitle") +
					  "%\") AND (user = 'holidays')");
		  }
		  // If a user is logged in, the accessible events are events that they have created AND holidays.
		  else
		  {
			  // Get regular events.
			  rs = DB.get("((title LIKE \"" + request.getParameter("searchTitle") +
					  "%\") AND (user = '" + loggedInUser + "') OR " +
					  "(title LIKE \"" + request.getParameter("searchTitle") +
					  "%\") AND (user = 'holidays'))");
					 
			  //rs = DB.get("((title LIKE '%" + request.getParameter("searchTitle") + "') OR " +
				//	  "(user = 'holidays'))");
		  }
		System.out.println(request.getSession().getAttribute("user"));
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String title = "Events that match " + request.getParameter("searchTitle");
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		docType += 
				"<html>\n"
				  + "<head>\r\n"
	              + "		<style>\r\n"
	              + "			header \r\n"
	              + "			{\r\n"
	              + "			    background-color:black;\r\n"
	              + "			    color:white;\r\n"
	              + "			    text-align:center;\r\n"
	              + "			    padding:5px;	 \r\n"
	              + "			}\r\n"
	              + "			nav \r\n"
	              + "			{\r\n"
	              + "			    line-height:30px;\r\n"
	              + "			    background-color:#eeeeee;\r\n"
	              + "			    height:300px;\r\n"
	              + "			    width:100px;\r\n"
	              + "			    float:left;\r\n"
	              + "			    padding:5px;	      \r\n"
	              + "			}\r\n"
	              + "			section \r\n"
	              + "			{\r\n"
	              + "			    width:500px;\r\n"
	              + "			    float:left;\r\n"
	              + "			    padding:10px;	 	 \r\n"
	              + "			}\r\n"
	              + "			footer \r\n"
	              + "			{\r\n"
	              + "			    background-color:black;\r\n"
	              + "			    color:white;\r\n"
	              + "			    clear:both;\r\n"
	              + "			    text-align:center;\r\n"
	              + "			    padding:5px;	 	 \r\n"
	              + "			}\r\n"
	              + "		</style>\r\n"
	              + "		<title>" + title + "</title>"
	              + "	</head>"
	              + "<body>\n"
	              + "	<header>\r\n"
	              + "		<h1> " + title + " </h1>\r\n"
	              + "	</header>\r\n"
	              + "	<nav>\r\n"
	              + "		<a href=\"/CSCI4830TermProject/HomePage.html\">Home</a> <br>\r\n"
	              + "		<a href=\"/CSCI4830TermProject/AddEvent.html\">Add Event</a> <br>\r\n"
	              + "		<form action=\"SearchEvent\" method=\"POST\">\r\n"
	              + "	      	<label for=\"search\">Search event:</label>\r\n"
	              + "	      	<input type=\"text\" id=\"searchTitle\" name=\"searchTitle\" size=\"10\" placeholder=\"Event Title\"><br>\r\n"
	              + "	      	<input type=\"text\" id=\"searchYear\" name=\"searchYear\" size=\"2\" placeholder=\"Year\">\r\n"
	              + "	      	<input type=\"submit\" value=\"Submit\" />	\r\n"
	              + "      	</form>\r\n"
	              + "		<a href=\"/CSCI4830TermProject/LogOut\">Log Out</a> <br>\r\n"
	              + "	</nav>"
	              + "	<section>\r\n";
		
		for (DBResults res : rs)
		  {
	             System.out.println("Title: " + res.getTitle());
	             System.out.println("User: " + res.getUser());
	             System.out.println("Month: " + res.getMonth());
	             System.out.println("Day: " + res.getDay());
	             System.out.println("Year: " + res.getYear() + "\n");
		  }
		
		for (DBResults res : rs)
		  {
					docType += (res.getTitle() + "<br>" + 
		  			      "\"" + res.getMessage() + "\"<br>");
		  	  // If event is not all day, aka 0, state when the event starts (HH:MM)
		  	  if (res.getAllDay() == 0)
		  	  {
		  		  docType += ("This event starts at " + res.getHour() + ":" + res.getMinute() + ".<br><br>");
		  	  }
		  	  else 
		  	  {
		  		  docType += ("This event is an all day occurrence.<br><br>");
		  	  }
		  }
	
	     out.println(docType
	    		  + "	</section>\r\n"
	              + "	<footer>\r\n"
	              + "		Copyright\r\n"
	              + "	</footer>");
	
	     out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}