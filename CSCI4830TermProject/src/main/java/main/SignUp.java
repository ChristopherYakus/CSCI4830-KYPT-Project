package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
		
		Connection connection = null;
		String signupInsert = "INSERT INTO user (username, password) values (?, ?)";
		//TODO: don't use prepared statement if username already exists in database
		//create some sort of message to indicate failure and redirect to home screen
		//maybe use a statement that checks if the username exists in the table as the if condition
		//if either username or password are blank automatically fail, display message and send back to sign up page 
		try {
			DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        //If username and password are both filled in and valid
	        PreparedStatement preparedStmt = connection.prepareStatement(signupInsert);
	        preparedStmt.setString(1, user);
	        preparedStmt.setString(2, pass);
	        preparedStmt.execute();
	        connection.close();
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		//TODO: set up page with boxes for birthday submission and button to redirect to login page
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Enter Birthday";
	    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	          "transitional//en\">\n"; //
	    out.println(docType + //
	          "<html>\n" + //
	          "<head><title>" + title + "</title></head>\n" + //
	          "<body bgcolor=\"#f0f0f0\">\n" + //
	          "<h1 align=\"center\">" + title + "</h1>\n");
	    
	    //TODO: Set up prepared statement to insert birthday entry
		response.sendRedirect("LogIn.html");	//temp for presentation
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
