package com.example.myapp.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Engle 处理时间格式的日历工具类
 */
public class CalendarUtils {
	private CalendarUtils() {

	}

	/** 将时间转换成YYYY-MM-DD HH:MM:SS的格式 */
	public static String toStandardDateString(Calendar calendar) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss", Locale.CHINA);
		return simpleDateFormat.format(calendar.getTime());
	}
}
