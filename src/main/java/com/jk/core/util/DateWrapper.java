package com.jk.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateWrapper {
	static String month[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };


	/**
	 * Default constructor.
	 */
	public DateWrapper() {

	}


	/**
	 * Returns string in the format DD-MON-YYYY.
	 * 
	 * @param date
	 * @return date as string in Oracle default date format which is
	 *         DD-MON-YYYY.
	 */
	public static String parseDate(Date date) {
		SimpleDateFormat dateFmt = new SimpleDateFormat("d-MMM-yyyy");
		return dateFmt.format(date);
	}


	/**
	 * Returns string after replacing numeric value of month with equivalent
	 * three letter month name.
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(String date) {
		int monthid = Integer
				.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
		String newdate = date.substring(0, date.indexOf("-")) + "-" + month[monthid - 1] + "-"
				+ (date.substring(date.lastIndexOf("-") + 1, date.length()));
		return newdate;
	}


	/**
	 * Returns string in the format DD-MM-YYYY.
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(java.sql.Date date) {
		String olddate = date.toString();
		String newdate = olddate.substring(olddate.lastIndexOf("-") + 1, olddate.length()) + "-"
				+ olddate.substring(olddate.indexOf("-") + 1, olddate.lastIndexOf("-")) + "-"
				+ olddate.substring(0, olddate.indexOf("-"));
		return newdate;

	}


	public static String getDateString(java.sql.Date date) {
		if (date == null) return "";

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());

		return cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.YEAR);
	}
}
