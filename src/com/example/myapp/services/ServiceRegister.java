/*
 * ServiceRegister.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.services;

import java.util.HashMap;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.example.myapp.MessageHandler;
import com.example.myapp.ServerResult;
import com.example.myapp.common.util.HttpUtil;
import com.google.gson.Gson;

/**
 * @author Engle
 * 
 */
public class ServiceRegister extends IntentService {

	/**
	 * @param name
	 */
	public ServiceRegister() {
		super("ServiceRegister");
	}

	/*
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(final Intent intent) {
		final MessageHandler handler = new MessageHandler(
				getApplicationContext());
		new Thread() {
			@Override
			public void run() {

				Looper.prepare();
				Bundle bundle = intent.getExtras();
				String username = bundle.getString("username");
				String password = bundle.getString("password");
				String confirmPassword = bundle.getString("confirmPassword");

				Map<String, String> map = new HashMap<String, String>();
				String url = HttpUtil.BASE_URL + "ClientRegister.action";
				map.put("username", username);
				map.put("password", password);
				map.put("confirmPassword", confirmPassword);
				ServerResult result;
				Gson gson = new Gson();
				try {
					String strJson = HttpUtil.postRequest(url, map);
					result = gson.fromJson(strJson, ServerResult.class);
					if (result.getResult()) {
						Toast.makeText(getApplicationContext(), "注册成功",
								Toast.LENGTH_SHORT).show();
						// handler.sendEmptyMessage(0x010);
					} else {
						Toast.makeText(getApplicationContext(), "注册失败",
								Toast.LENGTH_SHORT).show();
						// handler.sendEmptyMessage(0x011);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "注册异常",
							Toast.LENGTH_SHORT).show();
					// handler.sendEmptyMessage(0x012);
				}
				Looper.loop();
			}
		}.start();
	}
}
