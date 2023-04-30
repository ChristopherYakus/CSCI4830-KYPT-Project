package holidays;

import java.io.IOException;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import util.DBController;
import util.DBResults;

@SuppressWarnings("unused")
@WebServlet("/AddNationalHolidays")
public class AddNationalHolidays extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public AddNationalHolidays() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//TODO add holidays
		DBController db = new DBController(getServletContext());
		
		for (int year = 1870; year < 2200; year++)
		{
			//add(int month, int day, int year, int hour, int minute, int allDay, 
			//		String user, String title, String message)
			//int day = LocalDate.of(year, month, day).getDayOfWeek().getValue(); 1(mon) - 7(sun)
			int count;
			
			int jan = 1;
			int feb = 2;
			int mar = 3;
			int apr = 4;
			int may = 5;
			int june = 6;
			int july = 7;
			int aug = 8;
			int sep = 9;
			int oct = 10;
			int nov = 11;
			int dec = 12;
			
			//Add New Year's
			db.add(jan, 1, year, 0, 0, 1, "holidays", 
					"New Year's Day", "Start of the new Year!");
			
			//Add MLK Bday
			if (year >= 1983)
			{
				count = 0;
				for (int day = 1; day <= 21; day++)
				{
					int weekday = LocalDate.of(year, Month.of(jan), day).getDayOfWeek().getValue();
					if (weekday == 1) //if it is a monday
					{
						count++;
					}
					if (count == 3)
					{
						db.add(jan, day, year, 0, 0, 1, "holidays", 
								"MLK's Birthday", "Birthday of Civil Rights leader Martin Luther King jr.");
						break;
					}
				}
			}
				
			//Add Washington Bday
			if (year >= 1879)
			{
				if (year >= 1968)//washington's birthdat cant fall on his birthday anymore
				{
					db.add(feb, 22, year, 0, 0, 1, "holidays", 
							"George Washington's Birthday", "Birthday of the first President of the United States of America.");
				}
				else
				{
					count = 0;
					for (int day = 1; day <= 21; day++)
					{
						int weekday = LocalDate.of(year, Month.of(feb), day).getDayOfWeek().getValue();
						if (weekday == 1) //if it is a monday
						{
							count++;
						}
						if (count == 3)
						{
							db.add(feb, day, year, 0, 0, 1, "holidays", 
									"George Washington's Birthday", "Birthday of the first President of the United States of America.");
							break;
						}
					}
				}
			}
			
			//Add Memorial Day
			int lastMonday = 0;
			for (int day = 1; day <= 31; day++)
			{
				int weekday = LocalDate.of(year, Month.of(may), day).getDayOfWeek().getValue();
				if (weekday == 1) //if it is a monday
				{
					lastMonday = day;
				}
			}
			db.add(may, lastMonday, year, 0, 0, 1, "holidays", 
							"Memorial Day", "Day to honor the fallen servicemen of the American Armed Forces.");
			//Add Juneteenth
			if (year >= 2021)
			{
				db.add(june, 19, year, 0, 0, 1, "holidays", 
						"Juneteenth", "Celebration of African American emancipation from slavery.");
			}
			
			//Add Independence Day
			db.add(july, 4, year, 0, 0, 1, "holidays", 
					"Independance Day", "Celebration of American independence from Britain.");
			
			//Add Labor Day
			for (int day = 1; day <= 31; day++)
			{
				int weekday = LocalDate.of(year, Month.of(sep), day).getDayOfWeek().getValue();
				if (weekday == 1) //if it is a monday
				{
					db.add(sep, day, year, 0, 0, 1, "holidays", 
							"Labor Day", "Remembrance of the American Labor movement and is the onnnofficial end of Summer.");
					break;
				}
			}
			
			
			//Add Columbus Day
			if (year >= 1968)
			{
				count = 0;
				for (int day = 1; day <= 15; day++)
				{
					int weekday = LocalDate.of(year, Month.of(oct), day).getDayOfWeek().getValue();
					if (weekday == 1) //if it is a monday
					{
						count++;
					}
					if (count == 2)
					{
						db.add(oct, day, year, 0, 0, 1, "holidays", 
								"Columbus Day", "Celebration of the discovery of America by Christopher Columbus.");
						break;
					}
				}
			}
			
			//Add Veteran's Day
			if (year >= 1938)
			{
				db.add(nov, 11, year, 0, 0, 1, "holidays", 
						"Vetaran's Day", "Celebration of all veterans of the American Armed Forces and the end of World War 1");
			}
			
			//Add Thanksgiving Day
			if (year >= 1941)
			{
				count = 0;
				for (int day = 1; day <= 29; day++)
				{
					int weekday = LocalDate.of(year, Month.of(nov), day).getDayOfWeek().getValue();
					if (weekday == 4) //if it is a monday
					{
						count++;
					}
					if (count == 4)
					{
						db.add(nov, day, year, 0, 0, 1, "holidays", 
								"Thanksgiving", "Celebration of the feast between the Plymouth colonists and the native Wampanog tribe.");
						break;
					}
				}
			}
			
			//Add Christmas
			db.add(dec, 25, year, 0, 0, 1, "holidays", 
					"Christmas Day", "Celebration of the birth of Jesus Christ");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
