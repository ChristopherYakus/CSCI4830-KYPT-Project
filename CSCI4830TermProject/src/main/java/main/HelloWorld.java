package main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import util.DBController;
import util.DBResults;

@SuppressWarnings("unused")
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public HelloWorld() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		
		//check for table and/or create table
		DBController DB = new util.DBController(getServletContext());
		
		//test add for both success and no duplicates
		DB.add(0, 0, 0, 0, 0, 0, "me", "default", "hello world!");

		DB.add(0, 0, 0, 0, 0, 0, "me", "default", "It's ME!");
		
		DB.add(0, 0, 0, 0, 0, 0, "you", "default", "It's NOT ME!");
		
		//test get
		ArrayList<DBResults> rs = DB.get("user = 'me'");
		for (DBResults result : rs)
		{
			response.getWriter().println(result.getMessage() + "\n");
			
		}
		
		//DB.clearTable();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
