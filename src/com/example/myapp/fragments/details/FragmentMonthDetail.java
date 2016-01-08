/*
 * FragmentMonthDetail.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.example.myapp.fragments.details;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.common.GroupDetailItem;
import com.example.myapp.common.Week;
import com.example.myapp.db.DetailDatabaseHelper;
import com.example.myapp.fragments.parents.FragmentGroupDetail;

/**
 * @author Engle
 * 
 */
public class FragmentMonthDetail extends FragmentGroupDetail {

	/**
	 * 加载父视图数据
	 */
	@Override
	protected GroupDetailItem loadGroupViewData(DetailDatabaseHelper dbHelper) {
		List<String> dateIndex = new ArrayList<String>();
		List<String> dateRangeList = new ArrayList<String>();
		List<String> dateRangeStartList = new ArrayList<String>();
		List<String> dateRangeEndList = new ArrayList<String>();
		List<String> dateAmountList = new ArrayList<String>();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 当月
		int month = calendar.get(Calendar.MONTH) + 1;
		// 当年
		int year = calendar.get(Calendar.YEAR);
		Week week = new Week(calendar.getTime());
		calendar.setTime(week.getEndWeekDate());
		// 本月天数
		int maxMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 第一周开始日期
		int weekStartDay = 1;
		// 第一周结束日期
		int weekEndDay = calendar.get(Calendar.DAY_OF_MONTH);
		int count = 1;
		double sumAmount = 0.0;
		while (weekEndDay <= maxMonthDay) {
			dateIndex.add(String.valueOf(count++));

			String dateRange = String.format(Locale.CHINA,
					"%02d月%02d日-%02d月-%02d日", month, weekStartDay, month,
					weekEndDay);
			dateRangeList.add(dateRange);

			dateRangeStartList.add(String.format(Locale.CHINA,
					"%d-%02d-%02d 00:00:00", year, month, weekStartDay));
			dateRangeEndList.add(String.format(Locale.CHINA,
					"%d-%02d-%02d 23:59:59", year, month, weekEndDay));

			weekStartDay = weekEndDay + 1;
			weekEndDay = weekStartDay + 6;
			if (weekEndDay > maxMonthDay && weekStartDay <= maxMonthDay) {
				weekEndDay = maxMonthDay;
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
				double amount = cursor.getDouble(cursor
						.getColumnIndex("sumamount"));
				sumAmount += amount;
				dateAmountList.add(String.format("%.2f", amount));
			}

		}
		TextView textViewTitleAmount = (TextView) getActivity().findViewById(
				R.id.show_detail_title_amount);
		textViewTitleAmount.setText(String.format("%.2f元", sumAmount));
		return new GroupDetailItem(dateIndex, "周", dateRangeList,
				dateRangeStartList, dateRangeEndList, dateAmountList);
	}

}
