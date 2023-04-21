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

/**
 * Servlet implementation class LogIn
 */
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("uname");
		String pass = request.getParameter("pwd");
		PrintWriter out = response.getWriter();
		
		Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         
	         //select all instances of entered username & password, since only one of each username there should just be 1 or 0 
	         String selectSQL = "SELECT * FROM user WHERE username LIKE ? AND password LIKE ?";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         preparedStatement.setString(1, user);
	         preparedStatement.setString(2, pass);
	         
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         if (rs.next()) {	//if rs.next() is true it means that there is a matches with the database table
	        	 //if match, set user variable and redirect to home page
	        	 request.getSession().setAttribute("user", user);	//set the user name as a session variable so other servlets can use
	        	 //String user = (String)request.getSession().getAttribute("user"); How to get the session variable
	        	 response.sendRedirect("HomePage.html");	//go to home page upon successful sign in
	        	 
	         }
	         
	         //Display a page indicating username or password is incorrect with a link back to login page
	         String title = "Login Failed";
	         String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	         out.println(docType + //
	               "<html>\n" + //
	               "<head><title>" + title + "</title></head>\n" + //
	               "<body bgcolor=\"#f0f0f0\">\n" + //
	               "<h2 align=\"center\">" + title + "</h2>\n" + //
	               "<ul>\n" + //

	               " <li> Username or Password incorrect. Try Again.\n" + //

	               "</ul>\n");

	          out.println("<a href=/CSCI4830TermProject/LogIn.html>Return to login</a> <br>");
	          out.println("</body></html>");
	         
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
