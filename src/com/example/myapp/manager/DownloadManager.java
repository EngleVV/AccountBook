/*
 * DownloadManager.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;

import com.example.myapp.GlobleData;
import com.example.myapp.MessageHandler;
import com.example.myapp.common.DetailItem;
import com.example.myapp.common.util.HttpUtil;
import com.example.myapp.db.DetailDatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Engle
 * 
 */
public class DownloadManager {

	public static void DownloadToLocal(final MessageHandler handler,
			final Context context) {
		 new Thread() {
		 @Override
		 public void run() {
		try {
			GlobleData globleData = (GlobleData) context
					.getApplicationContext();
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", globleData.getUsername());
			map.put("sessionId", globleData.getSessionId());
			String url = HttpUtil.BASE_URL + "ClientGetDetail.action";
			String strJson = HttpUtil.postRequest(url, map);
			Gson gson = new Gson();
			List<DetailItem> list = gson.fromJson(strJson,
					new TypeToken<List<DetailItem>>() {
					}.getType());
			if (0 != list.size()) {
				// 返回非空列表
				// 将拿到的数据插入到本地数据库
				for (DetailItem item : list) {
					ContentValues contentValues = new ContentValues();
					contentValues.put("uuid", item.getUuid());
					contentValues.put("amount",
							item.getDayDetailConsumeAmount());
					contentValues.put("type", item.getDayDetailConsumeType()
							.getBytes("gb2312"));
					contentValues.put("date", item.getDayDetailConsumeDate());
					contentValues.put("accountType", item
							.getDayDetailAccountType().getBytes("gb2312"));
					contentValues.put("lastModifyDate",
							item.getLastModifyDate());
					DetailDatabaseHelper dbHelper = new DetailDatabaseHelper(
							context, "detail.db3", 1);
					dbHelper.getWritableDatabase().insert("detail_record",
							null, contentValues);
				}

				handler.sendEmptyMessage(0x001);
			} else {
				// 空列表
				handler.sendEmptyMessage(0x002);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 解析出错
			handler.sendEmptyMessage(0x003);
		}
			}
		}.start();
	}

}
