package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//TODO: set up prepared statement to enter new user into table
		
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
