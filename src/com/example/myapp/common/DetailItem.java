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
public class DetailItem {

	/** uuid主键 */
	private String uuid;

	/** 用户名 */
	private String dayDetailUsername;

	/** 日期 */
	private String dayDetailConsumeDate;

	/** 消费类型 */
	private String dayDetailConsumeType;

	/** 账户类型 */
	private String dayDetailAccountType;

	/** 消费金额 */
	private String dayDetailConsumeAmount;

	/** 最后修改时间 */
	private String lastModifyDate;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the dayDetailUsername
	 */
	public String getDayDetailUsername() {
		return dayDetailUsername;
	}

	/**
	 * @param dayDetailUsername
	 *            the dayDetailUsername to set
	 */
	public void setDayDetailUsername(String dayDetailUsername) {
		this.dayDetailUsername = dayDetailUsername;
	}

	/**
	 * @return the dayDetailConsumeDate
	 */
	public String getDayDetailConsumeDate() {
		return dayDetailConsumeDate;
	}

	/**
	 * @param dayDetailConsumeDate
	 *            the dayDetailConsumeDate to set
	 */
	public void setDayDetailConsumeDate(String dayDetailConsumeDate) {
		this.dayDetailConsumeDate = dayDetailConsumeDate;
	}

	/**
	 * @return the lastModifyDate
	 */
	public String getLastModifyDate() {
		return lastModifyDate;
	}

	/**
	 * @param lastModifyDate
	 *            the lastModifyDate to set
	 */
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
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
