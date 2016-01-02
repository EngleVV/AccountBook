/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.activities.AddDetailActivity;
import com.example.myapp.activities.ShowDetailActivity;
import com.example.myapp.common.Week;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.db.DetailDatabaseHelper;

/**
 * 主页面的fragment
 * 
 * @author Engle
 */
public class FragmentMainPage extends Fragment {

	/** 数据库操作对象 */
	private DetailDatabaseHelper dbHelper;

	/** 列表logo */
	private int[] logos = new int[] { R.drawable.date_day,
			R.drawable.date_week, R.drawable.date_month, R.drawable.date_year };

	/** 列表title */
	private String[] titles = new String[] { "今天", "本周", "本月", "本年" };

	/** 列表日期 */
	private String[] dates = new String[] { "12月12日", "12月7日-12月13日",
			"12月01日-12月31日", "01月01日-12月31日" };

	/** 消费金额 */
	private String[] amounts = new String[] { "0.00", "100.00", "1000.00",
			"3333.00" };

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 加载页面信息
		View rootView = inflater.inflate(R.layout.fragment_main_page, null);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		// 获取当前年月
		Calendar calendar = Calendar.getInstance();
		TextView textViewMonth = (TextView) rootView.findViewById(R.id.month);
		textViewMonth
				.setText(String.valueOf((calendar.get(Calendar.MONTH) + 1)));
		TextView textViewYear = (TextView) rootView.findViewById(R.id.year);
		textViewYear.setText("/" + calendar.get(Calendar.YEAR));

		String strToday = CalendarUtils.toStandardDateString(calendar);
		dbHelper = new DetailDatabaseHelper(getActivity(), "detail.db3", 1);
		SQLiteDatabase readDatabase = dbHelper.getReadableDatabase();
		// 获取日支出
		Cursor cursor = readDatabase
				.rawQuery(
						"select sum(amount) as sumamount from detail_record where date like ?",
						new String[] { strToday.substring(0, 10) + "%" });
		// 设置日支出
		if (cursor.moveToFirst()) {
			amounts[0] = String.format("%.2f",
					cursor.getDouble(cursor.getColumnIndex("sumamount")));
		}
		// 获取周支出
		Week week = new Week(calendar.getTime());
		// Toast.makeText(getActivity(),
		// simpleDateFormat.format(week.getStartWeekDate()),
		// Toast.LENGTH_LONG).show();
		cursor = readDatabase
				.rawQuery(
						"select sum(amount) as sumamount from detail_record where date >= ? and date <= ?",
						new String[] {
								simpleDateFormat.format(week.getStartWeekDate()),
								simpleDateFormat.format(week.getEndWeekDate()) });
		// 设置周支出
		if (cursor.moveToFirst()) {
			amounts[1] = String.format("%.2f",
					cursor.getDouble(cursor.getColumnIndex("sumamount")));
		}

		// 获取月支出
		cursor = readDatabase
				.rawQuery(
						"select sum(amount) as sumamount from detail_record where date like ?",
						new String[] { strToday.substring(0, 7) + "%" });
		// 设置月支出
		if (cursor.moveToFirst()) {
			amounts[2] = String.format("%.2f",
					cursor.getDouble(cursor.getColumnIndex("sumamount")));
		}
		// 获取年支出
		cursor = readDatabase
				.rawQuery(
						"select sum(amount) as sumamount from detail_record where date like ?",
						new String[] { strToday.substring(0, 4) + "%" });
		// 设置年支出
		if (cursor.moveToFirst()) {
			amounts[3] = String.format("%.2f",
					cursor.getDouble(cursor.getColumnIndex("sumamount")));
		}

		// 设置adapter
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < logos.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("logo", logos[i]);
			listItem.put("title", titles[i]);
			listItem.put("date", dates[i]);
			listItem.put("amount", amounts[i]);
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
				listItems, R.layout.simple_item_detail_list, new String[] {
						"logo", "title", "date", "amount" }, new int[] {
						R.id.logo, R.id.title, R.id.date, R.id.amount });

		ListView listView = (ListView) rootView.findViewById(R.id.detailList);
		listView.setAdapter(simpleAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				Intent intent = new Intent(getActivity(),
						ShowDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		// 显示月支出总金额
		TextView textViewAmountValue = (TextView) rootView
				.findViewById(R.id.amountValue);
		textViewAmountValue.setText(amounts[2] + "元");

		// 设置记一笔响应事件,跳转到添加activity
		Button buttonAddDetail = (Button) rootView
				.findViewById(R.id.main_add_button);
		buttonAddDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						AddDetailActivity.class);
				startActivity(intent);
			}
		});

		return rootView;
	}
}
