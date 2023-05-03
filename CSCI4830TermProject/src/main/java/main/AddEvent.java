package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;
import util.DBController;

@WebServlet("/AddEvent")
public class AddEvent extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public AddEvent() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		if (request.getSession().getAttribute("user") == null)
		{
			response.sendRedirect("LogIn.html");
		}
		else
		{
			PrintWriter out = response.getWriter();
			
			String date = request.getParameter("date"); 					// assuming date is in the format yyyy-mm-dd
			String time = request.getParameter("time"); 	//can be null  	// assuming time is in the format hh:mm:ss
			String allday = request.getParameter("allday"); //can be null
			
			int day = Integer.parseInt(date.substring(8));
			int month = Integer.parseInt(date.substring(5, 7));
			int year = Integer.parseInt(date.substring(0, 4));
			int hour = 0;
			int minute = 0;
			int allDay = 0;
			String user = "" + request.getSession().getAttribute("user");
			String title = request.getParameter("title");
			String message = request.getParameter("message");
			System.out.println(time);
			
			if (time != "" && allday == null) //if user has input a time and the allday box is not checked
			{
				hour = Integer.parseInt(time.substring(0, 2));
				minute = Integer.parseInt(time.substring(3, 5));
			}
			else //otherwise assume it is meant to be all day regardless of field
			{
				allDay = 1;
			}
			
			DBController db = new DBController(getServletContext());
			
			db.add(month, day, year, hour, minute, allDay, user, title, message);
			
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	        out.println(docType
	              + "<html>\n"
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
	              + "		<title>\" + \"Add success\" + \"</title>"
	              + "	</head>"
	              + "<body>\n"
	              + "	<header>\r\n"
	              + "		<h1> Add Success </h1>\r\n"
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
	              + "	<section>\r\n"
	              + "		<ul>\n"
	              + " 			<li> Title: " + title + "\n" 
	              + " 			<li> Message: <br>" + message + "\n" 
	              + "		</ul>\n"
	              + "		<a href=/CSCI4830TermProject/AddEvent.html>Add Another</a> <br>"
	              + "	</section>\r\n"
	              + "	<footer>\r\n"
	              + "		Copyright\r\n"
	              + "	</footer>");
	
	         out.println("</body>\r\n</html>");
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
