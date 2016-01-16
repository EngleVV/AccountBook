/*
 * ListenerStartOpponent.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Engle
 * 
 */
public class ListenerStartOpponent implements OnClickListener {

	private Context context;

	private Class<?> clz;

	/**
	 * 构造函数
	 */
	public ListenerStartOpponent(Context ctx, Class<?> c) {
		context = ctx;
		clz = c;
	}

	/*
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, clz);
		if(Activity.class.isAssignableFrom(clz)) {
			context.startActivity(intent);
		} else if (Service.class.isAssignableFrom(clz)) {
			context.startService(intent);
		}
	}
}
