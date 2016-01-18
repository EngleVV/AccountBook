/*
 * MessageHandler.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp;

import java.io.Serializable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * @author Engle
 * 
 */
public class MessageHandler extends Handler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6573003893684507099L;

	private Context context;

	/**
	 * @param context
	 */
	public MessageHandler(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		// 成功登陆,将文本设置为用户名,暂不可点击
		if (msg.what == 0x567) {
			// 同步成功
			Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x678) {
			// 同步失败
			Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x001) {
			// 下载成功
			// setListAmounts(rootView);
			// setList(rootView);
			Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x002) {
			// 下载列表为空
			Toast.makeText(context, "服务器暂无数据", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x003) {
			// 下载异常
			Toast.makeText(context, "下载数据异常", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x010) {
			// 注册成功
			Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x011) {
			// 注册失败
			Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
		} else if (msg.what == 0x012) {
			// 注册异常
			Toast.makeText(context, "注册异常", Toast.LENGTH_SHORT).show();
		}

	}

}
