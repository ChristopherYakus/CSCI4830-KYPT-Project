package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;

/**
 * Servlet implementation class InsertBirthday
 */
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
		String user = request.getParameter("uname");
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
	        String bdayInsert = "INSERT INTO events (user, day, month, year, title) values (?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(bdayInsert);
	        preparedStatement.setString(1, user);
	        preparedStatement.setInt(2, dayInt);
	        preparedStatement.setInt(3, monthInt);
	        preparedStatement.setInt(4, yearInt);
	        preparedStatement.setString(5, "Birthday");
        	preparedStatement.execute();
        	response.sendRedirect("LogIn.html");
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
