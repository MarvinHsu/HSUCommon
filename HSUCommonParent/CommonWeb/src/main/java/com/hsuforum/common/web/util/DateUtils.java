package com.hsuforum.common.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * Date utility
 * @author Marvin
 *
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	public final static String ISO_LINK = "-";
	public final static String TAIWAN_LINK = "/";

	/**
	 * Get Brief ISO Date String ("yyMMdd")
	 */
	public static String getBriefISODateStr(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.BRIEF_ISO_DATE_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get Calendar by taiwan date string
	 * @param taiwanStr
	 *            Taiwan date string formate:"YYYMMdd" or "YYMMdd"
	 * @return
	 */
	public static Calendar getCalendarByTaiwanStr(String taiwanStr) {

		int strLen = StringUtils.length(taiwanStr);

		if (StringUtils.isBlank(taiwanStr) || strLen < 6 || strLen > 7) {
			return null;
		}

		Calendar c = Calendar.getInstance();

		int year = 0;
		int month = 0;
		int day = 0;

		if (strLen == 6) {
			year = Integer.parseInt(StringUtils.substring(taiwanStr, 0, 2)) + 1911;
			month = Integer.parseInt(StringUtils.substring(taiwanStr, 2, 4));
			day = Integer.parseInt(StringUtils.substring(taiwanStr, 4, 6));
		} else { // strLen==7
			year = Integer.parseInt(StringUtils.substring(taiwanStr, 0, 3)) + 1911;
			month = Integer.parseInt(StringUtils.substring(taiwanStr, 3, 5));
			day = Integer.parseInt(StringUtils.substring(taiwanStr, 5, 7));

		}

		
		Date taiwanDate = DateUtils.getDate(year, month, day);
		if (strLen == 6) {
			if (!taiwanStr.equals(DateUtils.getTaiwanDateStr(taiwanDate, "", false))) {
				return null;
			}
		} else { // strLen==7
			if (!taiwanStr.equals(StringUtils.leftPad(DateUtils.getTaiwanDateStr(taiwanDate, "", false), 7, "0"))) {
				return null;
			}
		}
		c.setTime(taiwanDate);

		return c;
	}

	/**
	 * Get calendar by yyyyMM
	 * @return
	 */
	public static Calendar getCalendarByYYYYMM(String yearMonth) {
		if (StringUtils.isBlank(yearMonth) || yearMonth.length() != 6) {
			return null;
		}

		Integer year = Integer.parseInt(yearMonth.substring(0, 4));		
		Integer month = Integer.parseInt(yearMonth.substring(4, 6));

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}

	/**
	 * Get date range 
	 * ("20090101", 1) => Calendar[2] = { CalendarStart(
	 * "20090101 00:00:00") , CalendarEnd("20090102 00:00:00") }
	 *
	 * @param date
	 *            
	 * @param i day range
	 * @return
	 */
	public static Calendar[] getCalendarRange(Calendar date, int i) {
		if (null == date) {
			return null;
		}
		Calendar dateStart = getFirstMinuteOfDay(date);

		Calendar dateEnd = Calendar.getInstance();
		dateEnd.setTime(date.getTime());
		dateEnd.add(Calendar.DAY_OF_WEEK, i);
		dateEnd = getFirstMinuteOfDay(dateEnd);

		Calendar[] range = { dateStart, dateEnd };
		return range;
	}

	/**
	 * 
	 * Get date range
	 * @return
	 */
	public static Calendar[] getCalendarRange(Calendar date, int field, int amount) {
		if (null == date) {
			return null;
		}
		Calendar dateStart = getFirstMinuteOfDay(date);
		Calendar dateEnd = Calendar.getInstance();
		dateEnd.setTime(date.getTime());
		dateEnd.add(field, amount);
		dateEnd = getFirstMinuteOfDay(dateEnd);

		Calendar[] range = { dateStart, dateEnd };
		return range;
	}

	/**
	 * Get common date string
	 */
	public static String getCommonDateTimeStrIn12(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.COMMON_DATETIME_FORMAT_IN_12.getPattern();

		return DateFormatUtils.format(dt, format);
	}
	/**
	 * Get date
	 * @param iYear
	 * @param iMonth
	 * @param iDay
	 * @return
	 */
	public static Date getDate(int iYear, int iMonth, int iDay) {

		if (iYear <= 0 || iMonth <= 0 || iDay <= 0) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.clear();

		calendar.set(Calendar.YEAR, iYear);
		calendar.set(Calendar.MONTH, iMonth - 1);
		calendar.set(Calendar.DAY_OF_MONTH, iDay);

		return calendar.getTime();
	}
	/**
	 * Get Date
	 * @param iYear
	 * @param iMonth
	 * @param iDay
	 * @param iHour
	 * @param iMin
	 * @param iSec
	 * @return
	 */
	public static Date getDateTime(int iYear, int iMonth, int iDay, int iHour, int iMin, int iSec) {

		Calendar calendar = Calendar.getInstance();

		calendar.clear();

		calendar.set(Calendar.YEAR, iYear);
		calendar.set(Calendar.MONTH, iMonth - 1);
		calendar.set(Calendar.DAY_OF_MONTH, iDay);
		calendar.set(Calendar.HOUR_OF_DAY, iHour);
		calendar.set(Calendar.MINUTE, iMin);
		calendar.set(Calendar.SECOND, iSec);

		return calendar.getTime();
	}
	/**
	 * Get day of date2 - date1
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDays(Date date1, Date date2) {
		if (null == date1 || null == date2) {
			return 0;
		} else if (date2.compareTo(date1) < 0) {
			return 0;
		}
		
		int days = 0;
		days = (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
		return days;
	}

	/**
	 * 
	 * Get Calendar (am 00:00)
	 * @param Calendar
	 * @return Calendar
	 */
	public static Calendar getFirstMinuteOfDay(Calendar dt) {

		if (null == dt) {
			return null;
		}

		Calendar calendar = dt;

		Calendar rtnCalendar = Calendar.getInstance();
		rtnCalendar.clear();
		rtnCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		rtnCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		rtnCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

		return rtnCalendar;
	}

	/**
	 * 
	 * Get Date (am 00:00)
	 * @param Date
	 * @return Date
	 */
	public static Date getFirstMinuteOfDay(Date dt) {

		if (null == dt) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		Calendar rtnCalendar = Calendar.getInstance();
		rtnCalendar.clear();
		rtnCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		rtnCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		rtnCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

		return rtnCalendar.getTime();
	}

	/**
	 * Convert iso date string to date
	 * <ul>
	 * <li>DateUtils.getISODate("2005-10-10") = Date(2005-10-10)</li>
	 * </ul>
	 *
	 * @param isoDate
	 * @return
	 */
	public static Date getISODate(String isoDate) {
		return getISODate(isoDate, ISO_LINK);
	}

	/**
	 * Convert iso date string to date
	 * <ul>
	 * <li>DateUtils.getISODate("2005-10-10", "-") = Date(2005-10-10)</li>
	 * </ul>
	 *
	 * @param isoDate
	 * @param link
	 * @return
	 */
	public static Date getISODate(String isoDate, String link) {
		if (StringUtils.isBlank(isoDate)) {
			return null;
		}
		Date dt = null;

		if (StringUtils.isNotBlank(link)) {
			String[] tokens = isoDate.split(link);

			if (tokens.length == 3) {

				int iYear = StringUtils.str2Int(tokens[0]);
				int iMonth = StringUtils.str2Int(tokens[1]);
				int iDay = StringUtils.str2Int(tokens[2]);

				dt = DateUtils.getDate(iYear, iMonth, iDay);
			}
		}

		else {

			if (isoDate.length() == 8) {
				int iYear = StringUtils.str2Int(isoDate.substring(0, 4));
				int iMonth = StringUtils.str2Int(isoDate.substring(4, 6));
				int iDay = StringUtils.str2Int(isoDate.substring(6, 8));
				dt = DateUtils.getDate(iYear, iMonth, iDay);
			}
		}
		return dt;
	}

	/**
	 * Get iso date string ("yyyy-MM-dd")
	 *
	 * @param dt
	 * @return
	 */
	public static String getISODateStr(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.ISO_DATE_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);

	}

	/**
	 * Get iso date string 
	 * DateUtils.getISODateStr(new Date(), "/") = "yyyy/MM/dd"
	 *
	 * @param dt
	 * @param link
	 * @return
	 */
	public static String getISODateStr(Date dt, String link) {
		if (null == dt) {
			return "";
		}

		Calendar ctime = Calendar.getInstance();
		ctime.setTime(dt);

		StringBuffer sb = new StringBuffer();
		sb.append(ctime.get(Calendar.YEAR));

		sb.append(link);

		String month = String.valueOf(ctime.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			sb.append("0");
		}
		sb.append(month);

		sb.append(link);

		String day = String.valueOf(ctime.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) {
			sb.append("0");
		}
		sb.append(day);

		return sb.toString();

	}

	/**
	 * Convert iso date time to Date
	 * <ul>
	 * <li>DateUtils.getISODate("2005-10-10T12:34:56", "-") =
	 * Date(2005-10-10T12:34:56)</li>
	 * </ul>
	 *
	 * @param isoDate
	 * @param link
	 * @return
	 */
	public static Date getISODateTime(String isoDateTime) {
		if (StringUtils.isBlank(isoDateTime)) {
			return null;
		}

		String dateTimeLink = "T";
		String dateLink = "-";
		String timeLink = ":";

		boolean isParseOK = true;

		Date dt = null;

		int iYear = 0;
		int iMonth = 0;
		int iDay = 0;
		int iHour = 0;
		int iMin = 0;
		int iSec = 0;

		String[] tokens = isoDateTime.split(dateTimeLink);

		if (tokens.length == 2) {

			String sDate = tokens[0];
			String sTime = tokens[1];

			String[] dateTokens = sDate.split(dateLink);

			if (dateTokens.length == 3) {

				iYear = StringUtils.str2Int(dateTokens[0]);
				iMonth = StringUtils.str2Int(dateTokens[1]);
				iDay = StringUtils.str2Int(dateTokens[2]);

			} else {
				isParseOK = false;
			}

			String[] timeTokens = sTime.split(timeLink);

			if (isParseOK && timeTokens.length == 3) {

				iHour = StringUtils.str2Int(timeTokens[0]);
				iMin = StringUtils.str2Int(timeTokens[1]);
				iSec = StringUtils.str2Int(timeTokens[2]);

			} else {
				isParseOK = false;
			}

		}

		if (isParseOK) {

			dt = DateUtils.getDateTime(iYear, iMonth, iDay, iHour, iMin, iSec);
		}

		return dt;
	}

	/**
	 * Get iso date time string
	 * DateUtils.getISODateTimeStr(new Date()) = "yyyy-MM-ddTHH:mm:ss"
	 *
	 * @param dt
	 * @return
	 */
	public static String getISODateTimeStr(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.ISO_DATETIME_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get iso date time string
	 * DateUtils.getISOTimeStr(new Date()) = "HH:mm:ss"
	 *
	 * @param dt
	 * @return
	 */
	public static String getISOTimeStr(Date dt) {

		if (null == dt) {
			return "";
		}
		String format = DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get iso time string
	 * DateUtils.getISOTimeStr(new Date()) = "hh:mm a"
	 *
	 * @author Eustace 2009/05/21
	 * @param dt
	 * @return
	 */
	public static String getISOTimeStr2(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.SIMPLE_ISO_TIME_FORMAT_IN_12.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get last day of month (2007-01-12 = 2007-01-31)
	 *
	 * @param Date
	 * @return Date 
	 */
	public static Date getLastDayOfMonth(Date dt) {

		if (null == dt) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

		Calendar rtnCalendar = Calendar.getInstance();
		rtnCalendar.clear();
		rtnCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		rtnCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		rtnCalendar.set(Calendar.DAY_OF_MONTH, day);

		return rtnCalendar.getTime();
	}

	/**
	 * 
	 * Get last day of year(2007-01-12 = 2007-12-31)
	 * @param Date
	 * @return Date 
	 */
	public static Date getLastDayOfYear(Date dt) {

		if (null == dt) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		Calendar rtnCalendar = Calendar.getInstance();
		rtnCalendar.clear();
		rtnCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		rtnCalendar.set(Calendar.MONTH, 11);
		rtnCalendar.set(Calendar.DAY_OF_MONTH, 31);

		return rtnCalendar.getTime();
	}

	/**
	 * Get simple iso date string
	 * DateUtils.getSimpleISODateStr(new Date()) = "yyyyMMdd"
	 */
	public static String getSimpleISODateStr(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.SIMPLE_ISO_DATE_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get simple iso date string
	 * DateUtils.getSimpleISODateTimeStr(new Date()) = "yyyyMMddTHHmmss"
	 *
	 * @param dt
	 * @return
	 */
	public static String getSimpleISODateTimeStr(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.SIMPLE_ISO_DATETIME_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get simple iso time string
	 * DateUtils.getSimpleISOTimeStr(new Date()) = "HHmmss"
	 */
	public static String getSimpleISOTimeStr(Date dt) {

		if (null == dt) {
			return "";
		}

		String format = DateFormatUtils.SIMPLE_ISO_TIME_FORMAT.getPattern();

		return DateFormatUtils.format(dt, format);
	}

	/**
	 * Get Taiwan date string("yyy-MM-dd")
	 *
	 * @param dt
	 * @return
	 */
	public static String getTaiwanDateStr(Date dt, boolean isSixDigits) {
		return DateUtils.getTaiwanDateStr(dt, DateUtils.TAIWAN_LINK, isSixDigits);
	}

	/**
	 * Get Taiwan date string
	 * DateUtils.getTaiwanDateStr(new Date(), "-") = "yyy-MM-dd"
	 * @param dt
	 * @param link
	 * @param isSixDigits
	 * @return
	 */
	public static String getTaiwanDateStr(Date dt, String link, boolean isSixDigits) {

		if (dt == null) {
			return "";
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(dt);

		
		int iYear = calendar.get(Calendar.YEAR) - 1911;

		if (isSixDigits && iYear > 99) {
			iYear = iYear % 100;
		}

		String year = String.valueOf(iYear);
		if (isSixDigits) {
			year = StringUtils.leftPad(year, 3, "0");
		}

		int iMonth = calendar.get(Calendar.MONTH) + 1;
		String month = String.valueOf(iMonth);
		month = StringUtils.leftPad(month, 2, "0");

		int iDay = calendar.get(Calendar.DAY_OF_MONTH);
		String day = String.valueOf(iDay);
		day = StringUtils.leftPad(day, 2, "0");

		StringBuffer sb = new StringBuffer();
		sb.append(year).append(link).append(month).append(link).append(day);

		return sb.toString();

	}

	/**
	 * Get Taiwan date string
	 * 
	 * @param dt
	 * @param link
	 * @return 
	 */
	public static String getTaiwanDateStr7Digits(Date dt, String link) {
		String taiwanDateStr = getTaiwanDateStr(dt, link, false);
		return StringUtils.leftPad(taiwanDateStr, 7, "0");
	}

	/**
	 * 
	 * Get Taiwan date string
	 * @param calendar
	 * @return
	 */
	public static String getTaiwanDateStrByCalendar(Calendar calendar) {
		return StringUtils.leftPad(DateUtils.getTaiwanDateStr(calendar.getTime(), "", false), 7, "0");
	}

	/**
	 * 
	 * Get Taiwan date string
	 * @param value
	 * @return 
	 */
	public static String getTaiwanYYYMMByYYYYMM(String value) {
		if (StringUtils.isBlank(value) || StringUtils.length(value) != 6) {
			return "";
		}

		return getTaiwanYYYMMByYYYYMM(value, "");

	}

	/**
	 *  
	 * Get Taiwan date string
	 * getTaiwanYYYMMByYYYYMM(200901, "-") = 098-01
	 * @param value
	 * @return 
	 */
	public static String getTaiwanYYYMMByYYYYMM(String value, String link) {
		if (StringUtils.length(value) != 6) {
			return "";
		}

		String year = String.valueOf(Integer.parseInt(value.substring(0, 4)) - 1911);

		String taiwanYYYMM = StringUtils.leftPad(year, 3, "0") + link + value.substring(4, 6);

		return taiwanYYYMM;

	}

	/**
	 * Get year and month string
	 * <p>
	 *
	 * @param calendar
	 * @return yyyyMM
	 */
	public static String getYeayMonth(Calendar calendar) {

		if (null == calendar) {
			return "";
		}

		String format = DateFormatUtils.SIMPLE_ISO_YEAR_MONTH_FORMAT.getPattern();

		return DateFormatUtils.format(calendar.getTime(), format);
	}

	/**
	 * 
	 * Get date string by taiwan date string
	 * @param value
	 * @return 
	 */
	public static String getYYYYMMByRocYYYMM(String value) {
		if (StringUtils.length(value) != 5) {
			return "";
		}

		String year = String.valueOf(Integer.parseInt(value.substring(0, 3)) + 1911);

		return year + value.substring(3, 5);
	}

	/**
	 * 
	 * Get date by date string("yyyyMMdd")
	 * @param dateString
	 * @return
	 * @throws ParseException 
	 */
	public static Date simpleISODateStringToDate(String dateString) throws ParseException {
		SimpleDateFormat sdm = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdm.parse(dateString);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}
}
