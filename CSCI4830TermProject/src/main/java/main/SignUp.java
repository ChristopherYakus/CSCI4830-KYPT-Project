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

@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("uname");
		String pass = request.getParameter("pwd");
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        String signupInsert = "INSERT INTO user (username, password) values (?, ?)";
	        String signupCheck = "SELECT * FROM user WHERE username LIKE ?";
	        preparedStatement = connection.prepareStatement(signupCheck);
	        preparedStatement.setString(1, user);
	         
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        //If username and password are both filled in and valid
	        if (!rs.next()) {
	        	request.getSession().setAttribute("newUser", user);
	        	PreparedStatement preparedStmt = connection.prepareStatement(signupInsert);
	        	preparedStmt.setString(1, user);
	        	preparedStmt.setString(2, pass);
	        	preparedStmt.execute();
	        	
	        	//After successfully creating a new user present a form for getting their birthday
	        	response.setContentType("text/html");
	    		PrintWriter out = response.getWriter();
	    		String title = "Enter Birthday";
	    		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	          	"transitional//en\">\n"; //
	    		out.println(docType + //
	          	"<html>\n" + //
	          	"<head><title>" + title + "</title></head>\n" + //
	          	"<body bgcolor=\"#f0f0f0\">\n" + //
	          	"<h1 align=\"center\">" + title + "</h1>\n" +
	    		"<form action=\"InsertBirthday\" method=\"POST\">" +
              		"Day <input type=\"text\" name=\"bday\" required> <br />" +
              		"<label for=\"month\">Month: </label>\r\n"
              	      	    + "		<select name=\"bmonth\" id=\"month\">\r\n"
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
              	      	    + "			</select> <br />" +
              		"Year <input type=\"text\" name=\"byear\" required> <br />" +
              		"<input type=\"submit\" value=\"Submit\" />" +
              	"</form>");
	        } else {
	        	//if user already exists
	        	response.setContentType("text/html");
	        	PrintWriter out = response.getWriter();
	        	String title = "Account creation failed";
	        	String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	        			"transitional//en\">\n"; //
	        	out.println(docType + //
	        			"<html>\n" + //
	        			"<head><title>" + title + "</title></head>\n" + //
	        			"<body bgcolor=\"#f0f0f0\">\n" + //
	        			"<h1 align=\"center\">" + title + "</h1>\n" + 
	        			"<section align=\"center\" margin=\"auto\"> Username already exists.\n" +
	        			"</section>");
	    	    
	        	out.println("<a href=/CSCI4830TermProject/SignUp.html>Return to signup</a> <br>");
	        	out.println("</body></html>");
	        }
	        connection.close();
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
