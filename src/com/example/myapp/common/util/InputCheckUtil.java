/*
 * InputCheckUtil.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.common.util;

/**
 * @author Engle
 * 
 */
public class InputCheckUtil {

	/** 限制最大输入金额 */
	private static final double MAX_INPUT_AMOUNT = 1000000.00;

	/** 密码最大长度 */
	private static final int MAX_PASSWORD_LENGTH = 16;

	/** 密码最小程度 */
	private static final int MIN_PASSWORD_LENGTH = 6;

	/** 用户名最小长度 */
	private static final int MIN_USERNAME_LENGTH = 4;

	/** 用户名最大长度 */
	private static final int MAX_USERNAME_LENGTH = 16;

	/**
	 * 判断输入金额是否在0-MAX_INPUT_AMOUNT之间
	 * 
	 * @param strAmount
	 *            输入金额
	 * @return 结果
	 */
	public static Boolean CheckAmount(String strAmount) {
		if (StringUtil.isBlank(strAmount))
			return false;
		try {
			double amount = Double.parseDouble(strAmount);
			if (amount <= 0 || amount > MAX_INPUT_AMOUNT) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 校验密码输入是否合理
	 * 
	 * @param strPwd
	 *            输入密码
	 * @return 结果
	 */
	public static Boolean CheckPassword(String strPwd) {
		if (StringUtil.isBlank(strPwd))
			return false;
		if (strPwd.length() <= MAX_PASSWORD_LENGTH
				&& strPwd.length() >= MIN_PASSWORD_LENGTH)
			return true;
		else
			return false;
	}

	/**
	 * 校验输入用户名是否合理
	 * 
	 * @param strUsr
	 *            输入用户名
	 * @return 结果
	 */
	public static Boolean CheckUsername(String strUsr) {
		if (StringUtil.isBlank(strUsr))
			return false;
		if (strUsr.length() <= MAX_USERNAME_LENGTH
				&& strUsr.length() >= MIN_USERNAME_LENGTH)
			return true;
		else
			return false;
	}
}
