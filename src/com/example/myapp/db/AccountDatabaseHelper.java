/**
 * 
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
	private String CREATE_TABLE_SQL = "create table account_list(_id integer primary key autoincrement, name, amount)";

	public AccountDatabaseHelper(Context context, String name, int version) {
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
