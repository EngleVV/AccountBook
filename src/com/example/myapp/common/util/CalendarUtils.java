/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return simpleDateFormat.format(calendar.getTime());
	}

	/** date转化成String */
	public static String toStandardDateString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return simpleDateFormat.format(date);
	}

	/** 将字符串类型的时间转化成calendar数据 */
	public static Calendar StringToCalendar(String strDate) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Date date = null;
		try {
			date = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(date);
		return calendar;
	}
}
