package util;

/**
 * stupid object because ResultSet closes itself. 
 * @author chris
 *
 */
public class DBResults
{
	private int month;
	private int day;
	private int year;
	private int hour;
	private int minute;
	private int allDay;
	private String user;
	private String title;
	private String message;
	
	
	
	public DBResults(int month, int day, int year, int hour, int minute, int allDay, 
			String user, String title, String message)
	{
		this.month = month;
		this.day = day;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
		this.allDay = allDay;
		this.user = user;
		this.title = title;
		this.message = message;
	}
	
	public int getMonth()
	{
		return month;
	}
	public int getDay()
	{
		return day;
	}
	public int getYear()
	{
		return year;
	}
	public int getHour()
	{
		return hour;
	}
	public int getMinute()
	{
		return minute;
	}
	public int getAllDay()
	{
		return allDay;
	}
	public String getUser()
	{
		return user;
	}
	public String getTitle()
	{
		return title;
	}
	public String getMessage()
	{
		return message;
	}
}