/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments.parents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.common.DetailItem;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.constant.Constant;
import com.example.myapp.db.DetailDatabaseHelper;
import com.example.myapp.db.SqlQuery;

abstract public class FragmentItemDetail extends Fragment {

	private DetailDatabaseHelper detailDbHelper;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_day_detail_list,
				null);

		detailDbHelper = new DetailDatabaseHelper(getActivity(), "detail.db3",
				1);
		setList(rootView, getData());

		return rootView;
	}

	/**
	 * 设置数据库查询语句和查询条件,由子类实现
	 * 
	 * @return SqlQuery
	 */
	abstract protected SqlQuery getSqlQuery();

	/**
	 * 获取数据
	 */
	private List<Map<String, Object>> getData() {
		Calendar calendar = Calendar.getInstance();
		SqlQuery sqlQuery = getSqlQuery();
		final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		double sumAmount = 0.0;
		for (DetailItem item : detailDbHelper.queryDetailList(sqlQuery)) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			calendar = CalendarUtils.StringToCalendar(item
					.getDayDetailConsumeDate());
			listItem.put("day_of_month",
					String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
			listItem.put("day_of_week",
					Constant.DAYS_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK)]);
			listItem.put("consume_type", item.getDayDetailConsumeType());
			listItem.put("account_type", item.getDayDetailAccountType());
			listItem.put("uuid", item.getUuid());

			double amount = Double
					.parseDouble(item.getDayDetailConsumeAmount());
			sumAmount += amount;

			listItem.put("consume_amount", String.format("%.2f", amount));
			listItems.add(listItem);
		}

		TextView textViewTitleAmount = (TextView) getActivity().findViewById(
				R.id.show_detail_title_amount);
		textViewTitleAmount.setText(String.format("%.2f元", sumAmount));

		return listItems;

	}

	private void setList(final View rootView,
			final List<Map<String, Object>> listItems) {
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

		listViewDayDetail
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, final int position, long id) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setTitle("删除").setMessage("是否确认删除")
								.setPositiveButton("确认", new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (detailDbHelper
												.deleteDetail(listItems
														.get(position)
														.get("uuid").toString())) {
											Toast.makeText(getActivity(),
													"删除成功", Toast.LENGTH_SHORT)
													.show();
											refresh(rootView);
										}

									}

								}).create().show();
						return true;
					}
				});
	}

	private void refresh(View rootView) {
		setList(rootView, getData());
	}
}
