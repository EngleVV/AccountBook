/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.example.myapp.GlobleData;
import com.example.myapp.R;
import com.example.myapp.activities.AddDetailActivity;
import com.example.myapp.activities.ShowDetailActivity;
import com.example.myapp.common.DetailItem;
import com.example.myapp.common.Week;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.common.util.HttpUtil;
import com.example.myapp.db.DetailDatabaseHelper;
import com.google.gson.Gson;

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
	private String[] dates = new String[4];

	/** 消费金额 */
	private String[] amounts = new String[] { "0.00", "100.00", "1000.00",
			"3333.00" };

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 加载页面信息
		View rootView = inflater.inflate(R.layout.fragment_main_page, null);

		// 获取当前年月
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Week week = new Week(calendar.getTime());
		dates[0] = String.format("%02d月%02d日", currentMonth,
				calendar.get(Calendar.DAY_OF_MONTH));
		calendar = CalendarUtils.StringToCalendar(CalendarUtils
				.toStandardDateString(week.getStartWeekDate()));
		dates[1] = String.format("%02d月%02d日-",
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		calendar = CalendarUtils.StringToCalendar(CalendarUtils
				.toStandardDateString(week.getEndWeekDate()));
		dates[1] += String.format("%02d月%02d日",
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		calendar = Calendar.getInstance();
		dates[2] = String.format("%02d月01日-%02d月%02d日", currentMonth,
				currentMonth, maxDay);
		dates[3] = "01月01日-12月31日";

		TextView textViewMonth = (TextView) rootView.findViewById(R.id.month);
		textViewMonth.setText(String.format("%02d",
				calendar.get(Calendar.MONTH) + 1));
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
		cursor = readDatabase
				.rawQuery(
						"select sum(amount) as sumamount from detail_record where date >= ? and date <= ?",
						new String[] {
								CalendarUtils.toStandardDateString(week
										.getStartWeekDate()),
								CalendarUtils.toStandardDateString(week
										.getEndWeekDate()) });
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
				Intent intent = new Intent(getActivity(),
						AddDetailActivity.class);
				startActivity(intent);
			}
		});

		// 同步数据至服务器
		Button buttonUpdateToServer = (Button) rootView
				.findViewById(R.id.main_page_update_to_server);
		buttonUpdateToServer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					updateToServer();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});

		return rootView;
	}

	private void updateToServer() throws UnsupportedEncodingException {
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
				"select * from detail_record", null);
		final List<DetailItem> detailList = new ArrayList<DetailItem>();
		while (cursor.moveToNext()) {
			DetailItem item = new DetailItem();
			item.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
			item.setLastModifyDate(cursor.getString(cursor
					.getColumnIndex("lastModifyDate")));
			item.setDayDetailConsumeDate(cursor.getString(cursor
					.getColumnIndex("date")));
			item.setDayDetailAccountType(new String(cursor.getBlob(cursor
					.getColumnIndex("accountType")), "gb2312"));
			item.setDayDetailConsumeType(new String(cursor.getBlob(cursor
					.getColumnIndex("type")), "gb2312"));
			item.setDayDetailConsumeAmount(cursor.getString(cursor
					.getColumnIndex("amount")));
			detailList.add(item);
		}
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					getData(detailList);
				} catch (Exception e) {
					Log.e("engle", e.getMessage());
				}
				Looper.loop();
			}
		}.start();
	}

	public void getData(List<DetailItem> detailList) throws Exception {
		// List<DetailItem> detailList = new ArrayList<DetailItem>();
		// DetailItem detailItem = new DetailItem();
		// detailItem.setUuid("e43091e1-ee8f-4d91-92ee-8b308400c5a0");
		// detailItem.setDayDetailConsumeDate("2016-01-06 11:11:11");
		// detailItem.setDayDetailConsumeType("吃饭");
		// detailItem.setDayDetailAccountType("支付宝");
		// detailItem.setDayDetailConsumeAmount(String.format("%.2f", 99.99));
		// detailItem.setLastModifyDate("2016-01-06 11:11:11");
		// detailList.add(detailItem);
		// detailItem = new DetailItem();
		// detailItem.setUuid("e43091e1-ee8f-4d91-92ee-8b308400c5a1");
		// detailItem.setDayDetailConsumeDate("2016-01-06 11:11:11");
		// detailItem.setDayDetailConsumeType("吃饭");
		// detailItem.setDayDetailAccountType("支付宝");
		// detailItem.setDayDetailConsumeAmount(String.format("%.2f", 99.99));
		// detailItem.setLastModifyDate("2016-01-06 11:11:11");
		// detailList.add(detailItem);

		Gson gson = new Gson();
		String strJson = gson.toJson(detailList);

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("detailList", strJson);
		map1.put("sessionId",
				((GlobleData) getActivity().getApplication()).getSessionId());
		String url1 = "";
		url1 = HttpUtil.BASE_URL + "Synchronization.action";
		HttpUtil.postRequest(url1, map1);
	}
}
