/*
 * GlobleData.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * 存放全局变量
 * 
 * @author Engle
 */
public class GlobleData extends Application {

	/** 全局使用的用户名 */
	private String username;

	/** 每次访问服务器则用这个sessionId去访问 */
	private String sessionId;

	/** 用户的登陆状态 */
	private Boolean isLogin;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the isLogin
	 */
	public Boolean getIsLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin
	 *            the isLogin to set
	 */
	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences sharedPreferences = getSharedPreferences(
				"login_info", MODE_PRIVATE);
		setUsername(sharedPreferences.getString("username", null));
		setSessionId(sharedPreferences.getString("sessionId", null));
		setIsLogin(null);
	}

	/**
	 * 清除变量
	 */
	public void clear() {
		this.isLogin = false;
		this.sessionId = null;
		this.username = null;
	}

}
