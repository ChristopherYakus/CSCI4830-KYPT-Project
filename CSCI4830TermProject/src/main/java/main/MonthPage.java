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
 
	  DBController DB = new util.DBController(getServletContext());
	  ArrayList<DBResults> rs;
	  
	  String loggedInUser = "";
	  loggedInUser += request.getSession().getAttribute("user");
	  // If no user is logged in, the accessible events are holidays.
	  if (loggedInUser.equals("null"))
	  {
		  rs = DB.get("(month = '" + Integer.parseInt(goToMonth) +
				  "') AND (year = '" + Integer.parseInt(goToYear) + 
				  "') AND (user = 'holidays')");
	  }
	  // If a user is logged in, the accessible events are events that they have created AND holidays.
	  else
	  {
		  rs = DB.get("((month = '" + Integer.parseInt(goToMonth) +
				  "') AND (year = '" + Integer.parseInt(goToYear) +
				  "') AND (user = \"" + loggedInUser + "\") OR " +
				  "(month = '" + Integer.parseInt(goToMonth) +
				  "') AND (year = '" + Integer.parseInt(goToYear) + 
				  "') AND (user = 'holidays'))");
	  }
	  System.out.println(request.getSession().getAttribute("user"));
	
	  
	  for (DBResults res : rs)
	  {
             System.out.println("Title: " + res.getTitle());
             System.out.println("User: " + res.getUser());
             System.out.println("Month: " + res.getMonth());
             System.out.println("Day: " + res.getDay());
             System.out.println("Year: " + res.getYear() + "\n");
	  }

      Connection connection = null;
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
      docType += "<html>\r\n"
      		+ "<head>\r\n"
      		+ "<style>\r\n"
      		+ "header {\r\n"
      		+ "    background-color:black;\r\n"
      		+ "    color:white;\r\n"
      		+ "    text-align:center;\r\n"
      		+ "    padding:5px;	 \r\n"
      		+ "}\r\n"
      		+ "nav {\r\n"
      		+ "    line-height:30px;\r\n"
      		+ "    background-color:#eeeeee;\r\n"
      		+ "    height:300px;\r\n"
      		+ "    width:100px;\r\n"
      		+ "    float:left;\r\n"
      		+ "    padding:5px;	      \r\n"
      		+ "}\r\n"
      		+ "section {\r\n"
      		+ "    width:350px;\r\n"
      		+ "    float:left;\r\n"
      		+ "    padding:10px;	 	 \r\n"
      		+ "}\r\n"
      		+ "footer {\r\n"
      		+ "    background-color:black;\r\n"
      		+ "    color:white;\r\n"
      		+ "    clear:both;\r\n"
      		+ "    text-align:center;\r\n"
      		+ "    padding:5px;	 	 \r\n"
      		+ "}\r\n"
      		+ "table, th, td {\r\n"
      		+ "    border:1px solid black;\r\n"
      		+ "}"
      		+ "</style>\r\n"
      		+ "</head>\r\n"
      		+ "\r\n"
      		+ "<body>\r\n"
      		+ "<header>\r\n"
      		+ "<h1>Calendar</h1>\r\n"
      		+ "</header>\r\n"
      		+ "\r\n"
      		+ "<nav>\r\n"
      		+ "<form action=\"SearchEvent\" method=\"POST\">\r\n"
      		+ "<label for=\"search\">Search event:</label>\r\n"
      		+ "  <input type=\"text\" id=\"searchTitle\" name=\"searchTitle\" size=\"1\">\r\n"
      		+ "<input type=\"submit\" value=\"Submit\" />	</form>\r\n"
      		+ "</nav>";     
      docType += "<div> <table> <thead> <tr> <th colspan=\"7\">";
      docType += printMonth(Integer.parseInt(goToYear), Integer.parseInt(goToMonth), rs, response);
      
      out.println(docType);
      
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
      		+ "		Year: <input type=\"text\" name=\"goToYear\" required><br />"
      		+ "		"
      		+ "		<input type=\"submit\" value=\"Submit\" />"
      		+ "	</form>"
      		+ ""
      		+ "</section>");
      out.println("</body>\r\n"
      	+ "</html> <footer>\r\n"
      	+ "Copyright\r\n"
      	+ "</footer>\r\n"
      	+ "</body>\r\n"
      	+ "</html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
   
   static String printMonth(int year, int month, ArrayList<DBResults> rs, HttpServletResponse response) {
       YearMonth ym = YearMonth.of(year, month);
       int counter = 1;
       // Convert month integer into proper capitalized word
       String result = "" + Month.of(month);
       result = result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
       
       result += " " + year + "</th> </tr> <tr> <th>Sun</th> <th>Mon</th> <th>Tue</th> <th>Wed</th> <th>Thu</th> <th>Fri</th> <th>Sat</th> </tr> </thead> <tbody>";

       int day = LocalDate.of(year, month, 1).getDayOfWeek().getValue();
       if (day != 7)
           for (int i = 0; i < day; i++, counter++) {
        	   result += "<td></td>";
           }

       for (int i = 1; i <= ym.getMonth().length(ym.isLeapYear()); i++, counter++) {
    	   /* If a day has an event, the text becomes hyperlinked. 
    	    */
    	   String dayAddition = "<td>" + i + "</td>";
    	   for (DBResults res : rs)
    	   {
    		   if (res.getDay() == i)
    		   {
    			   dayAddition = "<td><a href=/CSCI4830TermProject/DayPage?month=" +
    			   res.getMonth() + "&day=" + res.getDay() + "&year=" + res.getYear() + ">" + i + "</a></td>";
    			   //http://localhost:8080/CSCI4830TermProject/DayPage?month=10&day=29&year=2001
    			   break;
    		   }
    	   }
    	   result += dayAddition;
           // Create new row when counter % 7 aka is on Saturday.
           if (counter % 7 == 0) {
        	   result += "</tr> <tr>";
           }
       }
       return result + "</tr></tbody></table></div>";
   }
   
}
