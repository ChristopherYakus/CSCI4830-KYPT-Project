package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Month;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;
import util.DBController;
import util.DBResults;

@WebServlet("/InsertBirthday")
public class InsertBirthday extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertBirthday() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String)request.getSession().getAttribute("newUser");
		String day = request.getParameter("bday");
		String month = request.getParameter("bmonth");
		String year = request.getParameter("byear");
		int dayInt = Integer.parseInt(day);
		int monthInt = Integer.parseInt(month);
		int yearInt = Integer.parseInt(year);

		Connection connection = null;
		
		try {
			DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        
	        String monthGenerate = "" + Month.of(monthInt);
	        monthGenerate = monthGenerate.substring(0, 1).toUpperCase() + monthGenerate.substring(1).toLowerCase();

	        DBController db = new DBController(getServletContext());
	        db.add(monthInt, dayInt, yearInt, 0, 0, 1, user, 
	        		(user + "'s Birthday"),
	        		(user + " was born on " + monthGenerate + " " + dayInt + ", " + yearInt + "."));

        	connection.close();	
		} catch (Exception e) {
		        e.printStackTrace();
			}
		response.sendRedirect("LogIn.html"); //go back to login page after birthday is entered
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
