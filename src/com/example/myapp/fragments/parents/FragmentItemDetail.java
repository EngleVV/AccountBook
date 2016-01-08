/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments.parents;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Constant;
import com.example.myapp.R;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.db.DetailDatabaseHelper;
import com.example.myapp.db.SqlQuery;

abstract public class FragmentItemDetail extends Fragment {

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_day_detail_list,
				null);

		Calendar calendar = Calendar.getInstance();

		DetailDatabaseHelper dbHelper = new DetailDatabaseHelper(getActivity(),
				"detail.db3", 1);
		SqlQuery sqlQuery = getSqlQuery();
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
				sqlQuery.getSqlString(), sqlQuery.getSqlConditions());
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		double sumAmount = 0.0;
		while (cursor.moveToNext()) {
			try {
				Map<String, Object> listItem = new HashMap<String, Object>();
				calendar = CalendarUtils.StringToCalendar(cursor
						.getString(cursor.getColumnIndex("date")));
				listItem.put(
						"day_of_month",
						String.format("%02d",
								calendar.get(Calendar.DAY_OF_MONTH)));
				listItem.put("day_of_week", Constant.DAYS_OF_WEEK[calendar
						.get(Calendar.DAY_OF_WEEK)]);
				listItem.put(
						"consume_type",
						new String(
								cursor.getBlob(cursor.getColumnIndex("type")),
								"gb2312"));
				listItem.put(
						"account_type",
						new String(cursor.getBlob(cursor
								.getColumnIndex("accountType")), "gb2312"));

				double amount = cursor.getDouble(cursor
						.getColumnIndex("amount"));
				sumAmount += amount;

				listItem.put("consume_amount", String.format("%.2f", amount));
				listItems.add(listItem);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
				listItems, R.layout.simple_item_day_detail_list, new String[] {
						"day_of_month", "day_of_week", "consume_type",
						"account_type", "consume_amount" }, new int[] {
						R.id.day_detail_item_date_month,
						R.id.day_detail_item_date_week,
						R.id.day_detail_item_type_consume,
						R.id.day_detail_item_type_account,
						R.id.day_detail_item_consume_amount });

		ListView listViewDayDetail = (ListView) rootView
				.findViewById(R.id.list_view_day_detail);
		listViewDayDetail.setAdapter(simpleAdapter);
		Toast.makeText(getActivity(), "FragmentDayDetail onCreateView",
				Toast.LENGTH_SHORT).show();

		TextView textViewTitleAmount = (TextView) getActivity().findViewById(
				R.id.show_detail_title_amount);
		textViewTitleAmount.setText(String.format("%.2f元", sumAmount));
		return rootView;
	}

	/**
	 * 设置数据库查询语句和查询条件,由子类实现
	 * 
	 * @return SqlQuery
	 */
	abstract protected SqlQuery getSqlQuery();

}
