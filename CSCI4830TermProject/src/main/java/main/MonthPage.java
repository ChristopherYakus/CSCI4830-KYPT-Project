package main;
/**
 * @file MonthPage.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;
import util.DBController;
import util.DBResults;

@WebServlet("/MonthPage")
public class MonthPage extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public MonthPage() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  //Default Month is January 2000.
	  String goToMonth; //TECHNICALLY ONLY NUMBERS RIGHT NOW
	  String goToYear; 
	  goToMonth = request.getParameter("goToMonth");
	  goToYear = request.getParameter("goToYear");
	  if (goToMonth == null)
	  {
		  goToMonth = "1";
			System.out.println("Month is null! Going to default value (January).");
	  }
	  if (goToYear == null)
	  {
		  goToYear = "2000";
			System.out.println("Year is null! Going to default value (2000).");
	  }
	  
	  /* So, I could either run preparedStatement.executeQuery() for each day to see if there's an event for that day,
	   * or I could run it once, store its results/contents into an array, and instead use that array when checking each day.
	   * The latter sounds more appealing.
	   * I'm using DBResults because it's an object that has the attributes for an event.
	   * Not implemented in yet. (4/11, 1:08 pm)
	   */
	  DBResults[] monthEventArray = new DBResults[1000];
	  
	  DBController DB = new util.DBController(getServletContext());
		
	  ArrayList<DBResults> rs = DB.get("(month = '" + Integer.parseInt(goToMonth) +
	  "') AND (year = '" + Integer.parseInt(goToYear) + "')");
		
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         connection.close();        
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Month";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      docType += "<html>\n" + "<header><title>" + title + "</title></header>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h2 align=\"center\">" + title + "</h2>\n";
      /*docType += "<html>" +
"<head>" +
"<style>" +
"header {" +
    "background-color:black;" +
    "color:white;" +
    "text-align:center;" +
    "padding:5px;" +	 
"}" +
"nav {" +
    "line-height:30px;" +
    "background-color:#eeeeee;" +
    "height:300px;" +
    "width:100px;" +
    "float:left;" +
    "padding:5px;" +	      
"}" +
"section {" +
    "width:350px;" +
    "float:left;" +
    "padding:10px;" +	 	 
"}" +
"footer {" +
    "background-color:black;" +
    "color:white;" +
    "clear:both;" +
    "text-align:center;" +
    "padding:5px;" +	 	 
"}" +
"</style>" +
"</head>";*/
      //docType += "\n" + "<header><title>" + title + "</title></header>\n" + "<h2 align=\"center\">" + title + "</h2>\n";
      
      docType += "<div class=\"container\"> <div class=\"row\"> <div class=\"span12\"> <table class=\"tb\"> <thead> <tr> <th colspan=\"7\"> <span class=\"btn-group\"><a class=\"btn active\">";
      docType += printMonth(Integer.parseInt(goToYear), Integer.parseInt(goToMonth), rs, response);
      
      out.println(docType);

      //out.println("<a href=/webproject-ex-0214-Perkins/insert_perkins.html>Insert Data</a> <br>");
      
      //!!!!Note: Data validation for inputting year has not been implemented in yet.
      //Entering a character that is not a number will throw an error!
      out.println("<section>"
      		+ "	Go to:"
      		+ "	<form action=\"MonthPage\" method=\"POST\">"
      		+ "		<label for=\"month\">Month: </label>\r\n"
      	    + "		<select name=\"goToMonth\" id=\"month\">\r\n"
      	    + "  		<option value=\"1\" selected>January</option>\r\n"
      	    + "  		<option value=\"2\">February</option>\r\n"
      	    + "  		<option value=\"3\">March</option>\r\n"
      	    + "  		<option value=\"4\">April</option>\r\n"
	      	+ "  		<option value=\"5\">May</option>\r\n"
	      	+ "  		<option value=\"6\">June</option>\r\n"
	      	+ "  		<option value=\"7\">July</option>\r\n"
	      	+ "  		<option value=\"8\">August</option>\r\n"
	      	+ "  		<option value=\"9\">September</option>\r\n"
	      	+ "  		<option value=\"10\">October</option>\r\n"
	      	+ "  		<option value=\"11\">November</option>\r\n"
	      	+ "  		<option value=\"12\">December</option>\r\n"
      	    + "			</select> <br />"
      		+ "		Year: <input type=\"text\" name=\"goToYear\"> <br />"
      		+ "		"
      		+ "		<input type=\"submit\" value=\"Submit\" />"
      		+ "	</form>"
      		+ ""
      		+ "</section>");
      out.println(//"<footer>\r\n"
      		//+ "Copyright Nathan Perkins\r\n"
      		//+ "</footer>\r\n"
      		"</body>\r\n"
      		+ "</html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
   
   static String printMonth(int year, int month, ArrayList<DBResults> rs, HttpServletResponse response) {
       YearMonth ym = YearMonth.of(year, month);
       int counter = 1;
       String result = Month.of(month) + " " + year + "</a></span> </th> </tr> <tr> <th>Sun</th> <th>Mon</th> <th>Tue</th> <th>Wed</th> <th>Thu</th> <th>Fri</th> <th>Sat</th> </tr> </thead> <tbody>";

       int day = LocalDate.of(year, month, 1).getDayOfWeek().getValue();
       if (day != 7)
           for (int i = 0; i < day; i++, counter++) {
        	   result += "<td></td>";
           }

       for (int i = 1; i <= ym.getMonth().length(ym.isLeapYear()); i++, counter++) {
    	   /* If a day has an event, the text becomes hyperlinked. 
    	    * 
    	    */
    	   String wonky = "";
    	   wonky = "<td>" + i + "</td>";
    	   for (DBResults res : rs)
    	   {
    		   if (res.getDay() == i)
    		   {
    			   wonky = "<td><a href=/CSCI4830TermProject/UNFINISHED.java>" + i + "</a></td>";
    			   break;
    		   }
    	   }
    	   result += wonky;
           // Create new row when counter % 7 aka is on Saturday.
           if (counter % 7 == 0) {
        	   result += "</tr> <tr>";
           }
       }
       return result + "</tr></tbody></table></div></div></div>";
   }
   
}
