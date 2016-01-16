/*
 * UploadManager.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.myapp.GlobleData;
import com.example.myapp.MessageHandler;
import com.example.myapp.ServerResult;
import com.example.myapp.common.DetailItem;
import com.example.myapp.common.util.HttpUtil;
import com.example.myapp.db.DetailDatabaseHelper;
import com.example.myapp.db.SqlQuery;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * @author Engle
 * 
 */
public class UploadManager {

	private UploadManager() {

	}

	/**
	 * 本地消费明细上传到服务器
	 * 
	 * @param handler
	 *            用于主线程与子线程的通信
	 * @param context
	 *            上下文
	 */
	public static void uploadToServer(final MessageHandler handler,
			final Context context) {
		DetailDatabaseHelper detailDbHelper = new DetailDatabaseHelper(context,
				"detail.db3", 1);
		final List<DetailItem> detailList = detailDbHelper
				.queryDetailList(new SqlQuery("select * from detail_record",
						null));

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					// 返回true,同步成功
					if (getData(detailList, context)) {
						handler.sendEmptyMessage(0x567);
					} else {
						// 同步失败
						handler.sendEmptyMessage(0x678);
					}
				} catch (Exception e) {
					Log.e("engle", e.getMessage());
				}
				Looper.loop();
			}
		}.start();
	}

	/**
	 * 同步至服务器
	 * 
	 * @param detailList
	 * @throws Exception
	 */
	private static Boolean getData(List<DetailItem> detailList, Context context) {

		Gson gson = new Gson();
		String strJson = gson.toJson(detailList);
		GlobleData globleData = (GlobleData) context.getApplicationContext();
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", globleData.getUsername());
		map.put("detailList", strJson);
		map.put("sessionId", globleData.getSessionId());
		String url = HttpUtil.BASE_URL + "Synchronization.action";
		ServerResult result = null;
		try {
			result = gson.fromJson(HttpUtil.postRequest(url, map),
					ServerResult.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getResult();
	}
}
