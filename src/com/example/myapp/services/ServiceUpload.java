/*
 * UploadService.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.myapp.MessageHandler;
import com.example.myapp.manager.UploadManager;

/**
 * @author Engle
 * 
 */
public class ServiceUpload extends IntentService {

	/**
	 * @param name
	 */
	public ServiceUpload() {
		super("UploadService");
	}

	/*
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {

		MessageHandler handler = new MessageHandler(getApplicationContext());
		UploadManager.uploadToServer(handler, getApplicationContext());
	}

	@Override
	public void onDestroy() {
		Log.i("engle", "Service is destoried");
	}

}
