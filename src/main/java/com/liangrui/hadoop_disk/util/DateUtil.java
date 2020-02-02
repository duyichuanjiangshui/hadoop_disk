package com.liangrui.hadoop_disk.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String longToString(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    Date date= new Date(millSec);
	    return sdf.format(date);
	}
	
	public static String DateToString(String dateFormat,Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	public String getYear(Calendar calendar)
	{
       return String.valueOf(calendar.get(Calendar.YEAR));
	}
	public String getMonth(Calendar calendar)
	{
		int t=calendar.get(Calendar.MONTH) + 1;
		if(t<10)
			return "0"+t;
		else
			return String.valueOf(t);
	}
	public String getDay(Calendar calendar)
	{
		int t=calendar.get(Calendar.DAY_OF_MONTH);
		if(t<10)
			return "0"+t;
		else
			return String.valueOf(t);
	}
	public String getHour(Calendar calendar)
	{
		int t=calendar.get(Calendar.HOUR_OF_DAY);
		if(t<10)
			return "0"+t;
		else
			return String.valueOf(t);
	}
	public String getminute(Calendar calendar)
	{
		int t=calendar.get(Calendar.MINUTE) + 1;
		if(t<10)
			return "0"+t;
		else
			return String.valueOf(t);
	}
	public String getSecond(Calendar calendar)
	{
		int t=calendar.get(Calendar.SECOND) + 1;
		if(t<10)
			return "0"+t;
		else
			return String.valueOf(t);
	}

}
