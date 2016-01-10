/*
 * LoginUti.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.common.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapp.ServerResult;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * 判断用户当前是否处于登陆状态
 * 
 * @author Engle
 * 
 */
public class LoginUtil {

	/**
	 * 判断当前用户是否处于登陆状态
	 * 
	 * @return 已登录:true; 未登录:false
	 */
	public static boolean IsLogin(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"login_info", Context.MODE_PRIVATE);

		if (sharedPreferences.contains("username")
				&& sharedPreferences.contains("sessionId")) {
			String username = sharedPreferences.getString("username", null);
			String sessionId = sharedPreferences.getString("sessionId", null);
			if (!isBlank(username) && !isBlank(sessionId)) {
				// 向服务器验证
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username);
				map.put("sessionId", sessionId);
				String url = HttpUtil.BASE_URL + "CheckSession.action";
				ServerResult result = new ServerResult();
				Gson gson = new Gson();
				try {
					result = gson.fromJson(HttpUtil.postRequest(url, map),
							ServerResult.class);
				} catch (Exception e) {
					Log.e("error", e.getMessage());
					return false;
				}
				return result.getResult();
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为空,str=null或者str=""均视为空
	 * 
	 * @param str
	 *            字符串
	 * @return 空返回true, 不空返回false
	 */
	private static Boolean isBlank(String str) {
		if (null != str) {
			if ("" == str) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
