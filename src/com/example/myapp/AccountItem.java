/*
 * AccountItem.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp;

/**
 * @author Engle
 * 
 */
public class AccountItem {

	/** 主键自增id */
	private int id;

	/** 账户名 */
	private String accountName;

	/** 账户类型 */
	private String accountType;

	/** 账户余额 */
	private String accountAmount;

	/** 账户显示优先级 */
	private int priority;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the accountAmount
	 */
	public String getAccountAmount() {
		return accountAmount;
	}

	/**
	 * @param accountAmount
	 *            the accountAmount to set
	 */
	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

}
