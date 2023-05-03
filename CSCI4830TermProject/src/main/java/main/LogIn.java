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
	         docType += "<html>\r\n"
	         		+ "<head>\r\n"
	         		+ "<style>\r\n"
	         		+ "header {\r\n"
	         		+ "    background-color:black;\r\n"
	         		+ "    color:white;\r\n"
	         		+ "    text-align:center;\r\n"
	         		+ "    padding:5px;	 \r\n"
	         		+ "}\r\n"
	         		+ "nav {\r\n"
	         		+ "    line-height:30px;\r\n"
	         		+ "    background-color:#eeeeee;\r\n"
	         		+ "    height:300px;\r\n"
	         		+ "    width:100px;\r\n"
	         		+ "    float:left;\r\n"
	         		+ "    padding:5px;	      \r\n"
	         		+ "}\r\n"
	         		+ "section {\r\n"
	         		+ "    width:350px;\r\n"
	         		+ "    float:left;\r\n"
	         		+ "    padding:10px;	 	 \r\n"
	         		+ "}\r\n"
	         		+ "footer {\r\n"
	         		+ "    background-color:black;\r\n"
	         		+ "    color:white;\r\n"
	         		+ "    clear:both;\r\n"
	         		+ "    text-align:center;\r\n"
	         		+ "    padding:5px;	 	 \r\n"
	         		+ "}\r\n"
	         		+ "table, th, td {\r\n"
	         		+ "    border:1px solid black;\r\n"
	         		+ "}"
	         		+ "</style>\r\n"
	         		+ "</head>\r\n"
	         		+ "\r\n"
	         		+ "<body>\r\n"
	         		+ "<header>\r\n"
	         		+ "<h1>" + title + "</h1>\r\n"
	         		+ "</header>\r\n"
	         		+ "\r\n"
	         		+ "	<nav>\r\n"
	         		+ "<a href=/CSCI4830TermProject/SignUp.html>Sign Up</a> <br>"
	                + "	</nav>"
	         		+ "<section>"
	         		+ "Username or password incorrect. Try Again.\n"
	         		+ "</ul>\n"
	         		+ "<br><br><a href=/CSCI4830TermProject/LogIn.html>Return to Log In</a> <br>"
	         		+ "</section>"
	                + "</body>\r\n"
        	      	+ "</html> <footer>\r\n"
        	      	+ "Team KYPP\r\n"
        	      	+ "</footer>\r\n"
        	      	+ "</body>\r\n"
        	      	+ "</html>";
	         
	         out.println(docType);
	         
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
