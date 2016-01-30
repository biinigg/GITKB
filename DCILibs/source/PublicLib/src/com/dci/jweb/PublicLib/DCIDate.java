package com.dci.jweb.PublicLib;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DCIDate {
	public static Date parseDate(String t) {
		Date datetime = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		try {
			datetime = formatter.parse(t);
		} catch (Exception e) {
			e.printStackTrace();
			datetime = null;
		}
		return datetime;
	}

	public static String parseString(Timestamp t, String format) {
		String datetime = "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		try {
			datetime = formatter.format(t);
		} catch (Exception e) {
			e.printStackTrace();
			datetime = "";
		}
		return datetime;
	}

	public static String parseString(Date t, String format) {
		String datetime = "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		try {
			datetime = formatter.format(t);
		} catch (Exception e) {
			e.printStackTrace();
			datetime = "";
		}
		return datetime;
	}

	public static int parseIntSec(String t, String format) {
		int sec = 0;

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		try {

			Date date = formatter.parse(t);
			c.setTime(date);
			sec = c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);

		} catch (Exception e) {
			e.printStackTrace();
			sec = 0;
		}
		return sec;
	}

	public static String parseStringSec(String t, String format) {
		String sec = "0";

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		try {

			Date date = formatter.parse(t);
			c.setTime(date);
			sec = String.valueOf(c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60
					+ c.get(Calendar.SECOND));

		} catch (Exception e) {
			e.printStackTrace();
			sec = "0";
		}
		return sec;
	}

	public static String parseShowDate(String date, String sp) {
		String show = null;
		if (date != null && date.length() == 8) {
			show = date.substring(0, 4) + sp + date.substring(4, 6) + sp + date.substring(6);
		}
		return show;
	}

	public static String parseShowTime(String time) {
		String show = null;
		if (time != null) {
			if (time.length() == 4) {
				show = time.substring(0, 2) + ":" + time.substring(2);
			} else if (time.length() == 6) {
				show = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4);
			} else if (time.length() > 6) {
				show = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6) + "."
						+ time.substring(6);
			}
		}
		return show;
	}

	public static String getToday(String returnFormat) {
		String today = null;
		SimpleDateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat(returnFormat);
			Date d = Calendar.getInstance().getTime();
			today = formatter.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
			today = "";
		}
		return today;
	}

	public static String getTodayAdd(int addDays, String returnFormat) {
		String today = null;
		SimpleDateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat(returnFormat);
			Calendar c = Calendar.getInstance();
			c.setTime(Calendar.getInstance().getTime());
			c.add(Calendar.DATE, addDays);
			Date d = c.getTime();

			today = formatter.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
			today = "";
		}
		return today;
	}

	public static String getCurrTime() {
		String currtime = null;

		currtime = String.valueOf(System.currentTimeMillis() / 1000L);

		return currtime;
	}

}
