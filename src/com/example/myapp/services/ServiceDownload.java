/*
 * ServiceDownload.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.services;

import com.example.myapp.MessageHandler;
import com.example.myapp.manager.DownloadManager;

import android.app.IntentService;
import android.content.Intent;

/**
 * @author Engle
 * 
 */
public class ServiceDownload extends IntentService {

	/**
	 * @param name
	 */
	public ServiceDownload() {
		super("ServiceDownload");
	}

	/*
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		MessageHandler handler = new MessageHandler(getApplicationContext());
		DownloadManager.DownloadToLocal(handler, getApplicationContext());
	}

}
