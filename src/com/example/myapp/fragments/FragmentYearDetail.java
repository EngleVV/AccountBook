/*
 * FragmentYearDetail.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.database.Cursor;

import com.example.myapp.Constant;
import com.example.myapp.common.GroupDetailItem;
import com.example.myapp.db.DetailDatabaseHelper;

/**
 * @author Engle
 * 
 */
public class FragmentYearDetail extends FragmentGroupDetail {

	/*
	 * @see
	 * com.example.myapp.fragments.FragmentGroupDetail#loadGroupViewData(com
	 * .example.myapp.db.DetailDatabaseHelper)
	 */
	@Override
	protected GroupDetailItem loadGroupViewData(DetailDatabaseHelper dbHelper) {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		List<String> dateIndex = new ArrayList<String>();
		List<String> dateRangeList = new ArrayList<String>();
		List<String> dateRangeStartList = new ArrayList<String>();
		List<String> dateRangeEndList = new ArrayList<String>();
		List<String> dateAmountList = new ArrayList<String>();

		for (; month >= 0; month--) {
			int actualMonth = month + 1;
			dateIndex.add(String.valueOf(actualMonth));
			// 判断是否是闰年, 且为2月份
			if (((year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0)))
					&& 2 == actualMonth) {
				dateRangeList.add("02月01-02月29日");
				dateRangeStartList
						.add(String.format("%d-02-01 00:00:00", year));
				dateRangeEndList.add(String.format("%d-02-29 23:59:59", year));
			} else {
				dateRangeList.add(String
						.format("%02d月01-%02d月%02d日", actualMonth, actualMonth,
								Constant.DAYS_IN_MONTH[month]));
				dateRangeStartList.add(String.format("%d-%02d-01 00:00:00",
						year, actualMonth));
				dateRangeEndList.add(String.format("%d-%02d-%02d 23:59:59",
						year, actualMonth, Constant.DAYS_IN_MONTH[month]));
			}
			// 在此处计算GroupView内所需的总金额
			Cursor cursor = dbHelper
					.getReadableDatabase()
					.rawQuery(
							"select sum(amount) as sumamount from detail_record where date >= ? and date <= ?",
							new String[] {
									dateRangeStartList.get(dateRangeStartList
											.size() - 1),
									dateRangeEndList.get(dateRangeEndList
											.size() - 1) });
			if (cursor.moveToFirst()) {
				dateAmountList.add(String.format("%.2f",
						cursor.getDouble(cursor.getColumnIndex("sumamount"))));
			}
		}

		return new GroupDetailItem(dateIndex, "月", dateRangeList,
				dateRangeStartList, dateRangeEndList, dateAmountList);

	}

}
