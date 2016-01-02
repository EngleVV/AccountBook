/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Engle 处理account_list表的类
 */
public class AccountDatabaseHelper extends SQLiteOpenHelper {

	/** 建表sql语句 */
	private String CREATE_TABLE_SQL = "create table account_list(id integer primary key, name, amount)";
	private String INIT_SQL = "insert into account_list values(0, '现金', 0.00), (1, '银行卡', 0.00),(2, '支付宝', 0.00),(3, '微信', 0.00)";

	public AccountDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
		db.execSQL(INIT_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--onUpdate Called--" + oldVersion + "--->"
				+ newVersion);
	}

}
