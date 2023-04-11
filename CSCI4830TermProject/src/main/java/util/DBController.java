package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;


@SuppressWarnings("unused")
public class DBController
{
	private ServletContext context = null;
	
	private Connection connection = null;
	
	private PreparedStatement preparedStatement = null;
	
	/**
	 * Tries to execute a basic query on the defined table 
	 * and if it does not succeed it creates the table to 
	 * the defined schema in the catch block.
	 * 
	 * @param context the servlet context to be passed
	 */
	public DBController(ServletContext context)
	{
		this.context = context;
	    DBConnection.getDBConnection(context);
        connection = DBConnection.connection;
		try //basic query to test if table exists
		{		    
       	 	String selectSQL = "SELECT * FROM events";
            preparedStatement = connection.prepareStatement(selectSQL);
            
			ResultSet rs = preparedStatement.executeQuery();
            
            System.out.println("Connection established!");
            
            rs.close();
            preparedStatement.close();
            connection.close();
		}
		catch (SQLException se) //if query fails table does not exist
		{
			System.out.println("Table does not exist");
			String table = 
					"CREATE TABLE events "
					+ "("
					+ "month INT NOT NULL,"
					+ "day INT NOT NULL,"
					+ "year INT NOT NULL,"
					+ "hour INT,"
					+ "minute INT,"
					+ "allDay TINYINT NOT NULL," //should be 1:true or 0:false
					+ "user VARCHAR(20) NOT NULL,"
					+ "title VARCHAR(30) NOT NULL,"
					+ "message VARCHAR(200) NOT NULL,"
					+ "PRIMARY KEY(title, message)"
					+ ");";

       	 	try 
       	 	{
				PreparedStatement preparedStmt = connection.prepareStatement(table);
	            preparedStmt.execute();
	            preparedStatement.close();
	            connection.close();
			} 
       	 	catch (SQLException e) 
       	 	{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds an event into the database
	 * @param month
	 * @param day
	 * @param year
	 * @param hour
	 * @param minute
	 * @param allDay
	 * @param user
	 * @param title
	 * @param message
	 * @return
	 */
	public boolean add(int month, int day, int year, int hour, int minute, int allDay, 
			String user, String title, String message)//input in fields
	{
		try
		{
			DBConnection.getDBConnection(context);
	        connection = DBConnection.connection;
	
	   	 	String selectSQL = "INSERT INTO events (month, day, year, hour, minute, allDay, user, title, message) "
	   	 			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, month+"");
            preparedStatement.setString(2, day+"");
            preparedStatement.setString(3, year+"");
            preparedStatement.setString(4, hour+"");
            preparedStatement.setString(5, minute+"");
            preparedStatement.setString(6, allDay+"");
            preparedStatement.setString(7, user);
            preparedStatement.setString(8, title);
            preparedStatement.setString(9, message);
            
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            
            System.out.println("Item successfully added");
            
            return true;
		}
		catch (SQLException se)
		{
			//se.printStackTrace();
			System.out.println("Cannot add. Uncomment stack trace in DBController.add() and try again");
			return false;
		}
	}
	
	/**
	 * Returns the result of a * query on the defined table using a custom WHERE condition.
	 * developer use only do not allow a user to access directly.
	 * 
	 * @param where a String containing the where logic block in the query
	 * @return the corresponding result set to the query or null if there is an error
	 */
	public ArrayList<DBResults> get(String where)
	{
		try //attempt to execute the query
		{
			ArrayList<DBResults> ret = new ArrayList<>();
			
			DBConnection.getDBConnection(context);
	        connection = DBConnection.connection;
	
	   	 	String selectSQL = "SELECT * FROM events WHERE " + where;
	        preparedStatement = connection.prepareStatement(selectSQL);
            //preparedStatement.setString(1, where);
	        
			ResultSet rs = preparedStatement.executeQuery();
	        System.out.println("Query Success!");
	        
			while (rs.next())
			{
		        DBResults result = new DBResults(
		        		rs.getInt("month"), rs.getInt("day"), rs.getInt("year"), 
		        		rs.getInt("hour"), rs.getInt("minute"), rs.getInt("allDay"), 
		        		rs.getString("user"), rs.getString("title"), rs.getString("message")
		        		);
		        ret.add(result);
			}
	        
	        preparedStatement.close();
	        connection.close();
			rs.close();
	        
	        return ret;
		}
		catch (SQLException se) //catch a failed query and terminate
		{
			se.printStackTrace();
			return null;
		}
	}
	
	public boolean update()
	{
		//TODO alter db, may not be necessary
		return false;
	}
	
	public boolean delete()
	{
		//TODO remove entry where a PK condition
		return false;
	}
	
	public boolean clearTable()
	{
		DBConnection.getDBConnection(context);
        connection = DBConnection.connection;

   	 	String drop = "DROP TABLE events";

        try 
        {
			preparedStatement = connection.prepareStatement(drop);

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            
            System.out.println("Table Cleared");
			
			return true;
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
			return false;
		}
	}
}