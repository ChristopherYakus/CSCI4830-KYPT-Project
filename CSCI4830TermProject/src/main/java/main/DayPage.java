package main;
/**
 * @file DayPage.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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

@WebServlet("/DayPage")
public class DayPage extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public DayPage() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String queryString = request.getQueryString();
	  StringBuilder url = new StringBuilder();
	   
	  // IMPORTANT!!!! Currently, what happens if the user enters zero URL parameters? You get a whole loada
	  // bologna, that's what. I could either throw a "Please select a date first" or a default January 1st, 2000.
	  if (queryString != null) {
		  url.append("?").append(queryString);
	  }
	    
	  System.out.println(queryString); //when this is blank, it prints out "null". use this to your advantage!
	  System.out.println(request.getParameter("month"));
	  
	  DBController DB = new util.DBController(getServletContext());
	  ArrayList<DBResults> rs;
	  
	  String loggedInUser = "";
	  loggedInUser += request.getSession().getAttribute("user");
	  if (loggedInUser.equals("null"))
	  {
		  rs = DB.get("(month = '" + request.getParameter("month") +
				  "') AND (day = '" + request.getParameter("day") +
				  "') AND (year = '" + request.getParameter("year") + "')");
	  }
	  else
	  {
		  // Get regular events.
		  rs = DB.get("(month = '" + request.getParameter("month") +
				  "') AND (day = '" + request.getParameter("day") +
				  "') AND (year = '" + request.getParameter("year") +
				  "') AND (user = '" + loggedInUser + "')");
		  // Then account for birthdays, which occur every year *after* the specified year.
		  // ...unfinished, though. I get around to it tomorrow.
		  rs = DB.get("(month = '" + request.getParameter("month") +
				  "') AND (day = '" + request.getParameter("day") +
				  "') AND (year = '" + request.getParameter("year") +
				  "') AND (user = '" + loggedInUser + "')");
	  }
	  System.out.println(request.getSession().getAttribute("user"));

	
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         connection.close();        
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      
      String title = "Events for ";

      String monthGenerate = "" + Month.of(Integer.parseInt(request.getParameter("month")));
      monthGenerate = monthGenerate.substring(0, 1).toUpperCase() + monthGenerate.substring(1).toLowerCase();
      title += monthGenerate + " " + Integer.parseInt(request.getParameter("day")) + ", " +
    		  						 Integer.parseInt(request.getParameter("year"));
      
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      docType += "<html>\n" + "<header><title>" + title + "</title></header>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h2 align=\"center\">" + title + "</h2>\n";
      /*docType += "<html>" +
"<head>" +
"<style>" +
"header {" +
    "background-color:black;" +
    "color:white;" +
    "text-align:center;" +
    "padding:5px;" +	 
"}" +
"nav {" +
    "line-height:30px;" +
    "background-color:#eeeeee;" +
    "height:300px;" +
    "width:100px;" +
    "float:left;" +
    "padding:5px;" +	      
"}" +
"section {" +
    "width:350px;" +
    "float:left;" +
    "padding:10px;" +	 	 
"}" +
"footer {" +
    "background-color:black;" +
    "color:white;" +
    "clear:both;" +
    "text-align:center;" +
    "padding:5px;" +	 	 
"}" +
"</style>" +
"</head>";*/
      //docType += "\n" + "<header><title>" + title + "</title></header>\n" + "<h2 align=\"center\">" + title + "</h2>\n";
      
      //!!! IMPORTANT: Do we really need "user" to be printed? It's a bit redundant...
      for (DBResults res : rs)
	  {
    	  docType += ("Title: " + res.getTitle() + "<br>" + 
    			      "User: " + res.getUser() + "<br>" +
    			      "\"" + res.getMessage() + "\"<br>");
    	  // If event is not all day, aka 0, state when the event starts (HH:MM)
    	  if (res.getAllDay() == 0)
    	  {
    		  docType += ("This event starts at " + res.getHour() + ":" + res.getMinute() + ".<br><br>");
    	  }
    	  else 
    	  {
    		  docType += ("This event is an all day occurence.<br><br>");
    	  }
	  }

      out.println(docType);

      //out.println("<a href=/webproject-ex-0214-Perkins/insert_perkins.html>Insert Data</a> <br>");
      
      //!!!!Note: Data validation for inputting year has not been implemented in yet.
      //Entering a character that is not a number will throw an error!
      out.println("<section>"
      		+ "	Go to:"
      		+ "	<form action=\"DayPage\" method=\"POST\">"
      		+ "		<label for=\"month\">Month: </label>\r\n"
      	    + "		<select name=\"goToMonth\" id=\"month\">\r\n"
      	    + "  		<option value=\"1\" selected>January</option>\r\n"
      	    + "  		<option value=\"2\">February</option>\r\n"
      	    + "  		<option value=\"3\">March</option>\r\n"
      	    + "  		<option value=\"4\">April</option>\r\n"
	      	+ "  		<option value=\"5\">May</option>\r\n"
	      	+ "  		<option value=\"6\">June</option>\r\n"
	      	+ "  		<option value=\"7\">July</option>\r\n"
	      	+ "  		<option value=\"8\">August</option>\r\n"
	      	+ "  		<option value=\"9\">September</option>\r\n"
	      	+ "  		<option value=\"10\">October</option>\r\n"
	      	+ "  		<option value=\"11\">November</option>\r\n"
	      	+ "  		<option value=\"12\">December</option>\r\n"
      	    + "			</select> <br />"
      		+ "		Year: <input type=\"text\" name=\"goToYear\"> <br />"
      		+ "		"
      		+ "		<input type=\"submit\" value=\"Submit\" />"
      		+ "	</form>"
      		+ ""
      		+ "</section>");
      out.println(//"<footer>\r\n"
      		//+ "Copyright Nathan Perkins\r\n"
      		//+ "</footer>\r\n"
      		"</body>\r\n"
      		+ "</html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
