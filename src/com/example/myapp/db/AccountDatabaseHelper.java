/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapp.AccountItem;

/**
 * @author Engle 处理account_list表的类
 */
public class AccountDatabaseHelper extends SQLiteOpenHelper {

	/** 表名 */
	private static final String TABLE_NAME = "account_list";

	/** 建表sql语句 */
	private String CREATE_TABLE_SQL = "create table account_list(_id integer primary key autoincrement, accountName, accountAmount, accountType, priority)";

	public AccountDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
		sqlInit(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--onUpdate Called--" + oldVersion + "--->"
				+ newVersion);
	}

	private void sqlInit(SQLiteDatabase db) {

		// 默认存在的账户
		String INIT_SQL_PRE = "insert into account_list values";
		String value[] = new String[] { "(null, '现金', 0.00, '现金账户', 0)",
				"(null, '招商银行', 0.00, '银行卡账户', 1)",
				"(null, '支付宝', 0.00, '虚拟账户', 2)",
				"(null, '微信', 0.00, '虚拟账户', 2)" };
		for (int i = 0; i < value.length; i++) {
			db.execSQL(INIT_SQL_PRE + value[i]);
		}

	}

	/**
	 * 查询账户列表
	 * 
	 * @return 对应账户列表
	 */
	public List<String> queryAccountName() {
		List<String> accountNameList = new ArrayList<String>();
		Cursor cursor = this.getReadableDatabase().rawQuery(
				"select accountName from account_list", null);
		while (cursor.moveToNext()) {
			accountNameList.add(cursor.getString(cursor
					.getColumnIndex("accountName")));
		}
		return accountNameList;
	}

	/**
	 * 减少指定账户余额
	 * 
	 * @param accountName
	 *            账户名
	 * @return 是否成功
	 */
	public Boolean cutBalance(String accountName, double amount) {
		try {
			this.getWritableDatabase()
					.execSQL(
							"update account_list set accountAmount = accountAmount - ? where accountName = ?",
							new Object[] { String.format("%.02f", amount),
									accountName });
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 查询账户列表
	 * 
	 * @return 列表结果
	 */
	public List<AccountItem> queryAccountList() {
		List<AccountItem> accountList = new ArrayList<AccountItem>();
		Cursor cursor = this
				.getReadableDatabase()
				.rawQuery(
						"select _id,accountName,accountType,accountAmount,priority from account_list order by priority",
						null);
		while (cursor.moveToNext()) {
			AccountItem item = new AccountItem();
			item.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			item.setAccountAmount(String.format("%.02f",
					cursor.getDouble(cursor.getColumnIndex("accountAmount"))));
			item.setAccountName(cursor.getString(cursor
					.getColumnIndex("accountName")));
			item.setAccountType(cursor.getString(cursor
					.getColumnIndex("accountType")));
			item.setPriority(cursor.getInt(cursor.getColumnIndex("priority")));
			accountList.add(item);
		}
		return accountList;
	}

	/**
	 * 修改指定账户余额
	 * 
	 * @param accountName
	 *            账户名
	 * @return 结果
	 */
	public Boolean modifyBalance(int id, String amount) {
		ContentValues values = new ContentValues();
		values.put("accountAmount",
				String.format("%.02f", Double.parseDouble(amount)));
		if ((this.getWritableDatabase().update(TABLE_NAME, values, "_id = ?",
				new String[] { String.valueOf(id) })) <= 0) {
			return false;
		}
		return true;
	}

	public Boolean deleteAccount(int id) {
		if ((this.getWritableDatabase().delete(TABLE_NAME, "_id = ?",
				new String[] { String.valueOf(id) })) <= 0) {
			return false;
		}
		return true;
	}

	public Boolean insertAccount(AccountItem item) {
		ContentValues values = new ContentValues();
		values.put("accountName", item.getAccountName());
		values.put("accountType", item.getAccountType());
		values.put("accountAmount", item.getAccountAmount());
		values.put("priority", item.getPriority());

		if (-1 == (this.getWritableDatabase().insert(TABLE_NAME, null, values))) {
			return false;
		}
		return true;
	}
}
