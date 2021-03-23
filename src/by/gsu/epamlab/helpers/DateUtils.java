package by.gsu.epamlab.helpers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	
	public final static SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private DateUtils(){ }
	
	public static Date getTodayDateWithOffset(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, offset);
		return toSqlDate(calendar.getTime());	
	}
	
	public static Date convertFromString(String strDate) throws ParseException {
		java.util.Date date = INPUT_DATE_FORMAT.parse(strDate);
		return toSqlDate(date);
	}
	
	private static Date toSqlDate(java.util.Date date) {
		return new Date(date.getTime());
	}
}
