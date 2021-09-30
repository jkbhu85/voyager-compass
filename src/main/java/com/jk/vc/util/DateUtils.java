package com.jk.vc.util;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

	// no instantiation
	private DateUtils() {
	}

	/**
	 * Returns string in the format DD-MON-YYYY.
	 * 
	 * @param date
	 * 
	 * @return date as string in Oracle default date format which is
	 * DD-MON-YYYY.
	 */
	public static String parseDate(Date date)
	{
		SimpleDateFormat dateFmt = new SimpleDateFormat("d-MMM-yyyy");
		return dateFmt.format(date);
	}

	private static final String DD_MMM_YYYY = "dd-MMM-yyyy";
	private static final String DD_MM_YYYY = "dd-MM-yyyy";

	/**
	 * Returns string after replacing numeric value of month with equivalent
	 * three letter month name.
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String convertDate(String date)
	{
		Date result;
		try {
			result = new SimpleDateFormat(DD_MMM_YYYY).parse(date);
			return new SimpleDateFormat(DD_MM_YYYY).format(result);
		}
		catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns string in the format DD-MM-YYYY.
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String parseDate(java.sql.Date date)
	{
		String olddate = date.toString();
		String newdate = olddate.substring(olddate.lastIndexOf("-") + 1,
				olddate.length()) + "-"
				+ olddate.substring(olddate.indexOf("-") + 1,
						olddate.lastIndexOf("-"))
				+ "-"
				+ olddate.substring(0, olddate.indexOf("-"));
		return newdate;

	}

	public static String getDateString(java.sql.Date date)
	{
		if (date == null)
			return "";

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());

		return cal.get(Calendar.DAY_OF_MONTH) + "-"
				+ (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.YEAR);
	}

}
