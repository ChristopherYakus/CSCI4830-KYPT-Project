package main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		response.getWriter().println("Hello World!");
		
		//check for table and/or create table
		DBController DB = new util.DBController(getServletContext());
		
		//test add for both success and no duplicates
		DB.add(0, 0, 0, 0, 0, 0, "me", "default", "hello world!");
		
		//test get
		DBResults rs = DB.get("user = 'me'");
		System.out.println(rs.getMessage());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
