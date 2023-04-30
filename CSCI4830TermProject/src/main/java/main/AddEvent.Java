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
		PrintWriter out = response.getWriter();
		
		String date = request.getParameter("date"); 					// assuming date is in the format yyyy-mm-dd
		String time = request.getParameter("time"); 	//can be null  	// assuming time is in the format hh:mm:ss
		String allday = request.getParameter("allday"); //can be null
		
		int day = Integer.parseInt(dateStr.substring(8));
		int month = Integer.parseInt(dateStr.substring(5, 7));
		int year = Integer.parseInt(date.substring(0, 4));
		int hour = 0;
		int minute = 0;
		int allDay = 0;
		String user = "who (first baseman)"; //TODO to be changed to the logged in user
		String title = request.getParameter("title");
		String message = request.getParameter("message");
		
		if (time != null && allDay == null) //if user has input a time and the allday box is not checked
		{
			hour = Integer.parseInt(timeStr.substring(0, 2));
			minute = Integer.parseInt(timeStr.substring(3, 5));
		}
		else //otherwise assume it is meant to be all day regardless of field
		{
			allDay = 1;
		}
		
		DBController db = new DBController();
		
		db.add(month, day, year, hour, minute, allDay, user, title, message);
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType + //
              "<html>\n" + //
              "<head><title>" + Add success + "</title></head>\n" + //
              "<body bgcolor=\"#f0f0f0\">\n" + //
              "<h2 align=\"center\">" + Add Success + "</h2>\n" + //
              "<ul>\n" + //

              " <li> Title: " + title + "\n" + //
              " <li> Message: <br>" + Message + "\n" + //

              "</ul>\n");

         out.println("<a href=/CSCI4830TermProject/AddEvent.html>Add Another</a> <br>");
         out.println("</body></html>");
		
//		Connection connection = null;
//	      PreparedStatement preparedStatement = null;
//	      try {
//	         DBConnection.getDBConnection(getServletContext());
//	         connection = DBConnection.connection;
//	         
//	         //select all instances of entered username & password, since only one of each username there should just be 1 or 0 
//	         String selectSQL = "SELECT * FROM user WHERE username LIKE ? AND password LIKE ?";
//	         preparedStatement = connection.prepareStatement(selectSQL);
//	         preparedStatement.setString(1, user);
//	         preparedStatement.setString(2, pass);
//	         
//	         ResultSet rs = preparedStatement.executeQuery();
//	         
//	         if (rs.next()) {	//if rs.next() is true it means that there is a matches with the database table
//	        	 //if match, set user variable and redirect to home page
//	        	 request.getSession().setAttribute("user", user);	//set the user name as a session variable so other servlets can use
//	        	 //String user = (String)request.getSession().getAttribute("user"); How to get the session variable
//	        	 response.sendRedirect("HomePage.html");	//go to home page upon successful sign in
//	        	 
//	         }
//	         
//	         //Display a page indicating username or password is incorrect with a link back to login page
//	         String title = "Login Failed";
//	         String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
//	         out.println(docType + //
//	               "<html>\n" + //
//	               "<head><title>" + title + "</title></head>\n" + //
//	               "<body bgcolor=\"#f0f0f0\">\n" + //
//	               "<h2 align=\"center\">" + title + "</h2>\n" + //
//	               "<ul>\n" + //
//
//	               " <li> Username or Password incorrect. Try Again.\n" + //
//
//	               "</ul>\n");
//
//	          out.println("<a href=/CSCI4830TermProject/LogIn.html>Return to login</a> <br>");
//	          out.println("</body></html>");
//	         
//	         connection.close();        
//	      } catch (Exception e) {
//	         e.printStackTrace();
//	      }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
