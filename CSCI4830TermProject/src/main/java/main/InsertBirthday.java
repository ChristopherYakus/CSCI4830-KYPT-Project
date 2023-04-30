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

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			DBConnection.getDBConnection(getServletContext());
	        connection = DBConnection.connection;
	        /* Originally:
	        String bdayInsert = "INSERT INTO events (user, day, month, year, title) values (?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(bdayInsert);
	        preparedStatement.setString(1, user);
	        preparedStatement.setInt(2, dayInt);
	        preparedStatement.setInt(3, monthInt);
	        preparedStatement.setInt(4, yearInt);
	        preparedStatement.setString(5, "Birthday");
		    */
	        
	        // Fixed:
	        String bdayInsert = "INSERT INTO events (month, day, year, hour, minute, allDay, user, title, message) "
		 			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(bdayInsert);
	        preparedStatement.setInt(1, monthInt); // month
	        preparedStatement.setInt(2, dayInt); // day
	        preparedStatement.setInt(3, yearInt); // year
	        preparedStatement.setInt(4, 0); // Setting hour to 0
	        preparedStatement.setInt(5, 0); // Setting minute to 0
	        preparedStatement.setInt(6, 1); // Setting allDay to 1 (means it's your birthday all day)
	        preparedStatement.setString(7, user); // Setting user to user
	        preparedStatement.setString(8, user + "'s Birthday"); // Setting title to "[user]'s Birthday"
	        String monthGenerate = "" + Month.of(monthInt);
	        monthGenerate = monthGenerate.substring(0, 1).toUpperCase() + monthGenerate.substring(1).toLowerCase();
	        preparedStatement.setString(9, user + " was born on " + monthGenerate + " " + 
	        							dayInt + ", " + yearInt + "."); // Setting message
	        //
	        
        	preparedStatement.execute();	
        	connection.close();
        	
        	//response.sendRedirect("LogIn.html");	//go back to login page after birthday is entered
        	
		} catch (Exception e) {
		        e.printStackTrace();
			}
		
		response.sendRedirect("LogIn.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
