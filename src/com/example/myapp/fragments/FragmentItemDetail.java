/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import android.widget.Toast;

import com.example.myapp.Constant;
import com.example.myapp.R;
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
		while (cursor.moveToNext()) {
			try {
				Map<String, Object> listItem = new HashMap<String, Object>();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss", Locale.CHINA);
				Date date = null;
				try {
					date = simpleDateFormat.parse(cursor.getString(cursor
							.getColumnIndex("date")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calendar.setTime(date);

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
				listItem.put("consume_amount",
						cursor.getString(cursor.getColumnIndex("amount")));
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
		return rootView;
	}

	abstract protected SqlQuery getSqlQuery();

}
