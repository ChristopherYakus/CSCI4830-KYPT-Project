package main;

import java.io.IOException;
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
		
		//TODO: prepared statement on table of users&passwords to see if match. If no match redirect back to login page
		Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         
	         //TODO: no user database yet, fix below stuff to run properly
	         /*
	         String selectSQL = "SELECT * FROM users WHERE USER LIKE ? AND PASSWORD LIKE ?";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         preparedStatement.setString(1, user);
	         preparedStatement.setString(2, pass);
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         if (!rs.next()) {
	        	 response.sendRedirect("LogIn.html");
	         }*/
	         
	         //if match, set user variable and redirect to home page
	         request.getSession().setAttribute("user", user);	//set the user name as a session variable so other servlets can use
	         //String user = (String)request.getSession().getAttribute("user"); How to get the session variable
	         response.sendRedirect("HomePage.html");	//go to home page upon successful sign in
	         
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
