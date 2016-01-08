/*
 * GlobleData.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp;

import android.app.Application;

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

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
