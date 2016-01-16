/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.example.myapp.GlobleData;
import com.example.myapp.R;
import com.example.myapp.ServerResult;
import com.example.myapp.activities.AddDetailActivity;
import com.example.myapp.activities.LoginActivity;
import com.example.myapp.activities.ShowDetailActivity;
import com.example.myapp.activities.UserInfoActivity;
import com.example.myapp.common.Week;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.common.util.HttpUtil;
import com.example.myapp.common.util.StringUtil;
import com.example.myapp.db.DetailDatabaseHelper;
import com.example.myapp.db.SqlQuery;
import com.google.gson.Gson;

/**
 * 主页面的fragment
 * 
 * @author Engle
 */
public class FragmentMainPage extends Fragment {

	/** 数据库操作对象 */
	private DetailDatabaseHelper detailDbHelper;

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

	private View rootView;

	/** 读取shareReferences */
	private SharedPreferences sharedPreferences;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 加载页面信息
		rootView = inflater.inflate(R.layout.fragment_main_page, null);

		detailDbHelper = new DetailDatabaseHelper(getActivity(), "detail.db3",
				1);

		final TextView textViewLogin = (TextView) rootView
				.findViewById(R.id.main_page_login);
		// 与子进程通信
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 成功登陆,将文本设置为用户名,暂不可点击
				if (msg.what == 0x567) {
					// 同步成功
					Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT)
							.show();
				} else if (msg.what == 0x678) {
					// 同步失败
					Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT)
							.show();
				} else if (msg.what == 0x001) {
					// 下载成功
					setListAmounts(rootView);
					setList(rootView);
					Toast.makeText(getActivity(), "下载成功", Toast.LENGTH_SHORT)
							.show();
				} else if (msg.what == 0x002) {
					// 下载列表为空
					Toast.makeText(getActivity(), "服务器暂无数据", Toast.LENGTH_SHORT)
							.show();
				} else if (msg.what == 0x003) {
					// 下载异常
					Toast.makeText(getActivity(), "下载数据异常", Toast.LENGTH_SHORT)
							.show();
				}
				System.out.println(msg.what);
			}
		};

		// 设置登陆按钮
		setLoginTips(textViewLogin);

		// 设置登录状态
		// setLoginState(handler);

		// 设置首页日期
		setTitleDate(rootView);

		// 设置list中显示的金额amounts[]
		setListAmounts(rootView);

		// 设置list中显示的日期范围
		setListDates();

		// 设置list
		setList(rootView);

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

		// Toast.makeText(getActivity(), "onCreate called", Toast.LENGTH_SHORT)
		// .show();

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		final GlobleData globleData = (GlobleData) getActivity()
				.getApplication();
		final TextView textViewLogin = (TextView) rootView
				.findViewById(R.id.main_page_login);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 成功登陆,将文本设置为用户名,暂不可点击
				if (msg.what == 0x234) {
					globleData.setIsLogin(true);
					setLoginUser(textViewLogin);
				} else if (msg.what == 0x456) {
					// 登陆失败,将文本设置为登陆,可点击进入登陆页面
					globleData.setIsLogin(false);
					setLoginButton(textViewLogin);
				}
				System.out.println(msg.what);
				Log.i("handler", String.valueOf(msg.what));
			}
		};

		setLoginState(handler);

		// application中并未加载到username和sessionId
		if (null == globleData.getIsLogin()) {
			textViewLogin.setText("加载中...");
		} else if (globleData.getIsLogin()) {
			// 已登录状态
			setLoginUser(textViewLogin);
		} else {
			// 未登录状态
			setLoginButton(textViewLogin);
		}

		setListAmounts(rootView);
		setList(rootView);
	}

	private void setLoginState(final Handler handler) {
		sharedPreferences = getActivity().getSharedPreferences("login_info",
				Context.MODE_PRIVATE);
		final String username = sharedPreferences.getString("username", null);
		final String sessionId = sharedPreferences.getString("sessionId", null);
		new Thread() {
			@Override
			public void run() {
				if (!StringUtil.isBlank(username)
						&& !StringUtil.isBlank(sessionId)) {
					// 向服务器验证
					Map<String, String> map = new HashMap<String, String>();
					map.put("username", username);
					map.put("sessionId", sessionId);
					String url = HttpUtil.BASE_URL + "CheckSession.action";
					ServerResult result = new ServerResult();
					Gson gson = new Gson();
					try {
						result = gson.fromJson(HttpUtil.postRequest(url, map),
								ServerResult.class);
						if (result.getResult()) {
							// 确实处于登陆状态
							handler.sendEmptyMessage(0x234);
						} else {
							// 登录失败状态
							handler.sendEmptyMessage(0x456);
						}
					} catch (Exception e) {
						Log.e("error", e.getMessage());
						handler.sendEmptyMessage(0x456);
					}
				} else {
					handler.sendEmptyMessage(0x456);
				}
			}
		}.start();
	}

	/**
	 * 设置登陆按钮为用户名
	 */
	private void setLoginUser(TextView v) {
		v.setText(((GlobleData) getActivity().getApplication()).getUsername());
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						UserInfoActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 设置登陆按钮
	 */
	private void setLoginButton(TextView v) {
		v.setText("登录");
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 设置登陆按钮
	 * 
	 * @param v
	 *            页面视图
	 */
	private void setLoginTips(TextView v) {
		v.setText("加载中...");

	}

	/**
	 * 设置首页的日期信息
	 */
	private void setTitleDate(View v) {
		Calendar calendar = Calendar.getInstance();
		TextView textViewMonth = (TextView) v.findViewById(R.id.month);
		textViewMonth.setText(String.format("%02d",
				calendar.get(Calendar.MONTH) + 1));
		TextView textViewYear = (TextView) v.findViewById(R.id.year);
		textViewYear.setText("/" + calendar.get(Calendar.YEAR));
	}

	/**
	 * 设置显示日期
	 */
	private void setListDates() {
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
	}

	/**
	 * 设置月消费金额
	 * 
	 * @param v
	 */
	private void setMonthAmount(View v) {
		TextView textViewAmountValue = (TextView) v
				.findViewById(R.id.amountValue);
		textViewAmountValue.setText(amounts[2] + "元");
	}

	/**
	 * 设置显示金额
	 */
	private void setListAmounts(View v) {
		Calendar calendar = Calendar.getInstance();
		Week week = new Week(calendar.getTime());
		String strToday = CalendarUtils.toStandardDateString(calendar);

		amounts[0] = detailDbHelper
				.querySumAmount(new SqlQuery(
						"select sum(amount) as sumamount from detail_record where date like ?",
						new String[] { strToday.substring(0, 10) + "%" }));

		// 获取周支出
		amounts[1] = detailDbHelper
				.querySumAmount(new SqlQuery(
						"select sum(amount) as sumamount from detail_record where date >= ? and date <= ?",
						new String[] {
								CalendarUtils.toStandardDateString(week
										.getStartWeekDate()),
								CalendarUtils.toStandardDateString(week
										.getEndWeekDate()) }));

		// 获取月支出
		amounts[2] = detailDbHelper
				.querySumAmount(new SqlQuery(
						"select sum(amount) as sumamount from detail_record where date like ?",
						new String[] { strToday.substring(0, 7) + "%" }));

		// 获取年支出
		amounts[3] = detailDbHelper
				.querySumAmount(new SqlQuery(
						"select sum(amount) as sumamount from detail_record where date like ?",
						new String[] { strToday.substring(0, 4) + "%" }));
		setMonthAmount(v);
	}

	/**
	 * 设置ListView
	 */
	private void setList(View v) {
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

		ListView listView = (ListView) v.findViewById(R.id.detailList);
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

	}

}
