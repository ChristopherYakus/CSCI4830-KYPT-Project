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
		docType += "<html>\n" + "<header><title>" + title + "</title></header>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h2 align=\"center\">" + title + "</h2>\n";
		
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
	
	     out.println(docType);
	
	     out.println("<a href=/CSCI4830TermProject/AddEvent.html>Add Another</a> <br>");
	     out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}