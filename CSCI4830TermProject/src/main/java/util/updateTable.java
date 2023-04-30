package util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/updateTable")
public class updateTable extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateTable() 
    {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		//check for table and/or create table
		DBController DB = new util.DBController(getServletContext());
		List<DBResults> allrs = DB.get("true");
		for (DBResults rs : allrs)
			out.print(rs.getTitle() + "<br>");
		DB.clearTable();
		DBController BD = new util.DBController(getServletContext());
		for (DBResults rs : allrs)
			BD.add(rs);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
