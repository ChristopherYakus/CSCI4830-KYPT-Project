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
	   
	  // IMPORTANT!!!! Currently, what happens if the user enters zero URL parameters? You get a whole loada
	  // bologna, that's what. I could either throw a "Please select a date first" or a default January 1st, 2000.
	    
	  System.out.println(queryString); //when this is blank, it prints out "null". use this to your advantage!
	  System.out.println(request.getParameter("month"));
	  
	  DBController DB = new util.DBController(getServletContext());
	  ArrayList<DBResults> rs;
	  
	  String loggedInUser = "";
	  loggedInUser += request.getSession().getAttribute("user");
	  // If no user is logged in, the accessible events are holidays.
	  if (loggedInUser.equals("null"))
	  {
		  rs = DB.get("(month = '" + request.getParameter("month") +
				  "') AND (day = '" + request.getParameter("day") +
				  "') AND (year = '" + request.getParameter("year") +
				  "') AND (user = 'holidays')");
	  }
	  // If a user is logged in, the accessible events are events that they have created AND holidays.
	  else
	  {
		  // Get regular events.
		  rs = DB.get("((month = '" + request.getParameter("month") +
				  "') AND (day = '" + request.getParameter("day") +
				  "') AND (year = '" + request.getParameter("year") +
				  "') AND (user = '" + loggedInUser + "') OR " +
				  "(month = '" + request.getParameter("month") +
				  "') AND (day = '" + request.getParameter("day") +
				  "') AND (year = '" + request.getParameter("year") +
				  "') AND (user = 'holidays'))");
	  }
	  System.out.println(request.getSession().getAttribute("user"));

	
      Connection connection = null;
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

      out.println(docType);

      //Should there be something here?
      
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
