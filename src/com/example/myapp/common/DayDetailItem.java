/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.common;

/**
 * 本类存储日消费明细
 * 
 * @author Engle
 */
public class DayDetailItem {

	/** 日期 */
	private String dayDetailMonthDay;

	/** 星期 */
	private String dayDetailWeekDay;

	/** 消费类型 */
	private String dayDetailConsumeType;

	/** 账户类型 */
	private String dayDetailAccountType;

	/** 消费金额 */
	private String dayDetailConsumeAmount;

	/**
	 * @return the dayDetailMonthDay
	 */
	public String getDayDetailMonthDay() {
		return dayDetailMonthDay;
	}

	/**
	 * @param dayDetailMonthDay
	 *            the dayDetailMonthDay to set
	 */
	public void setDayDetailMonthDay(String dayDetailMonthDay) {
		this.dayDetailMonthDay = dayDetailMonthDay;
	}

	/**
	 * @return the dayDetailWeekDay
	 */
	public String getDayDetailWeekDay() {
		return dayDetailWeekDay;
	}

	/**
	 * @param dayDetailWeekDay
	 *            the dayDetailWeekDay to set
	 */
	public void setDayDetailWeekDay(String dayDetailWeekDay) {
		this.dayDetailWeekDay = dayDetailWeekDay;
	}

	/**
	 * @return the dayDetailConsumeType
	 */
	public String getDayDetailConsumeType() {
		return dayDetailConsumeType;
	}

	/**
	 * @param dayDetailConsumeType
	 *            the dayDetailConsumeType to set
	 */
	public void setDayDetailConsumeType(String dayDetailConsumeType) {
		this.dayDetailConsumeType = dayDetailConsumeType;
	}

	/**
	 * @return the dayDetailAccountType
	 */
	public String getDayDetailAccountType() {
		return dayDetailAccountType;
	}

	/**
	 * @param dayDetailAccountType
	 *            the dayDetailAccountType to set
	 */
	public void setDayDetailAccountType(String dayDetailAccountType) {
		this.dayDetailAccountType = dayDetailAccountType;
	}

	/**
	 * @return the dayDetailConsumeAmount
	 */
	public String getDayDetailConsumeAmount() {
		return dayDetailConsumeAmount;
	}

	/**
	 * @param dayDetailConsumeAmount
	 *            the dayDetailConsumeAmount to set
	 */
	public void setDayDetailConsumeAmount(String dayDetailConsumeAmount) {
		this.dayDetailConsumeAmount = dayDetailConsumeAmount;
	}
}
