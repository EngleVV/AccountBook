/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.db;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapp.common.DetailItem;

/**
 * @author Engle 处理detail_record数据的类
 */
public class DetailDatabaseHelper extends SQLiteOpenHelper {

	/** 表名 */
	private static final String TABLE_NAME = "detail_record";

	/** 建表sql语句 */
	final String CREATE_TABLE_SQL = "create table detail_record(uuid primary key, "
			+ "amount, "
			+ "user, "
			+ "type, "
			+ "date, "
			+ "accountType, lastModifyDate)";

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

	/**
	 * 插入数据集
	 * 
	 * @param list
	 *            数据集
	 * @return 插入结果
	 */
	public Boolean insertList(List<DetailItem> list) {
		try {
			for (DetailItem item : list) {
				ContentValues values = new ContentValues();
				values.put("amount", item.getDayDetailConsumeAmount());
				values.put("type",
						item.getDayDetailConsumeType().getBytes("gb2312"));
				values.put("date", item.getDayDetailConsumeDate());
				values.put("lastModifyDate", item.getLastModifyDate());
				values.put("accountType", item.getDayDetailAccountType()
						.getBytes("gb2312"));
				values.put("user", item.getDayDetailUsername());
				values.put("uuid", item.getUuid());
				if (this.getWritableDatabase().insert(TABLE_NAME, null, values) < 0) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 查询指定条件的总金额
	 * 
	 * @param condition
	 *            查询条件
	 * @return 总金额
	 */
	public String querySumAmount(SqlQuery sqlQuery) {
		Cursor cursor = this.getReadableDatabase().rawQuery(
				sqlQuery.getSqlString(), sqlQuery.getSqlConditions());
		if (cursor.moveToFirst()) {
			return String.format(Locale.CHINA, "%.02f",
					cursor.getDouble(cursor.getColumnIndex("sumamount")));
		}
		return null;
	}

	/**
	 * 查询指定条件的明细数据
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 明细表
	 */
	public List<DetailItem> queryDetailList(SqlQuery sqlQuery) {
		List<DetailItem> detailList = new ArrayList<DetailItem>();
		Cursor cursor = this.getReadableDatabase().rawQuery(
				sqlQuery.getSqlString(), sqlQuery.getSqlConditions());
		while (cursor.moveToNext()) {
			DetailItem item = new DetailItem();
			try {
				item.setDayDetailConsumeType(new String(cursor.getBlob(cursor
						.getColumnIndex("type")), "gb2312"));
				item.setDayDetailAccountType(new String(cursor.getBlob(cursor
						.getColumnIndex("accountType")), "gb2312"));
				item.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
				item.setDayDetailConsumeAmount(cursor.getString(cursor
						.getColumnIndex("amount")));
				item.setLastModifyDate(cursor.getString(cursor
						.getColumnIndex("lastModifyDate")));
				item.setDayDetailConsumeDate(cursor.getString(cursor
						.getColumnIndex("date")));
				detailList.add(item);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return detailList;
	}

	public Boolean deleteDetail(String uuid) {
		if ((this.getWritableDatabase().delete(TABLE_NAME, "uuid = ?",
				new String[] { uuid })) <= 0) {
			return false;
		}
		return true;
	}

	public Boolean updateUser(String username) {
		ContentValues values = new ContentValues();
		values.put("user", username);
		if ((this.getWritableDatabase().update(TABLE_NAME, values, "user = ''",
				null)) <= 0) {
			return false;
		}
		return true;
	}
}
