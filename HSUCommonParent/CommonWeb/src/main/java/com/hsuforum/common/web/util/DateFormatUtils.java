package com.hsuforum.common.web.util;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Date format utility for date time
 * @author Marvin
 *
 */
public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

	public static final FastDateFormat SIMPLE_ISO_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

	public static final FastDateFormat SIMPLE_ISO_TIME_FORMAT = FastDateFormat.getInstance("HHmmss");

	public static final FastDateFormat SIMPLE_ISO_TIME_FORMAT_IN_12 = FastDateFormat.getInstance("hh:mm a");

	public static final FastDateFormat SIMPLE_ISO_DATETIME_FORMAT = FastDateFormat.getInstance("yyyyMMdd'T'HHmmss");

	public static final FastDateFormat BRIEF_ISO_DATE_FORMAT = FastDateFormat.getInstance("yyMMdd");

	public static final FastDateFormat COMMON_DATETIME_FORMAT_IN_12 = FastDateFormat.getInstance("yyyy/MM/dd hh:mm:ss");

	public static final FastDateFormat SIMPLE_ISO_YEAR_MONTH_FORMAT = FastDateFormat.getInstance("yyyyMM");
}
