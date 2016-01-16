/*
 * ConstantWeek.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.constant;

/**
 * @author Engle
 * 
 */
public class Constant {

	/** 星期 以Calendar.get(Calendar.DAY_OF_WEKK)为索引号存储的星期值 */
	public static final String[] DAYS_OF_WEEK = new String[] { "", "周日", "周一",
			"周二", "周三", "周四", "周五", "周六" };

	/** 存储每个月的天数,以Calendar.get(Calendar.MONTH)为索引 */
	public static final int[] DAYS_IN_MONTH = new int[] { 31, 28, 31, 30, 31,
			30, 31, 31, 30, 31, 30, 31 };
}
