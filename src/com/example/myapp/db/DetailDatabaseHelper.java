/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Engle 处理detail_record数据的类
 */
public class DetailDatabaseHelper extends SQLiteOpenHelper {

	/** 建表sql语句 */
	final String CREATE_TABLE_SQL = "create table detail_record(uuid primary key, "
			+ "amount, " + "type, " + "date, " + "accountType, lastModifyDate)";

	public DetailDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--onUpdate Called--" + oldVersion + "--->"
				+ newVersion);
	}

}
