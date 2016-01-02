/*
 * WeekEnum.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp;

/**
 * @author Engle
 * 
 */
public enum WeekEnum {

	SUNDAY("周日", 1),

	MONDAY("周一", 2),

	TUESDAY("周二", 3),

	WEDNESDAY("周三", 4),

	THURSDAY("周四", 5),

	FRIDAY("周五", 6),

	SATURDAY("周六", 7);

	/** 中文名 */
	private String name;

	/** 日期码 */
	private int code;

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            中文名称
	 * @param code
	 *            日期码,以Calendar.get(Calendar.DAY_OF_WEEK)为日期码
	 */
	private WeekEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

}
