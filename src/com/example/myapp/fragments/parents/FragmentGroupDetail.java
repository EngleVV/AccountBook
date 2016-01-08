/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments.parents;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.myapp.Constant;
import com.example.myapp.R;
import com.example.myapp.common.DayDetailItem;
import com.example.myapp.common.GroupDetailItem;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.db.DetailDatabaseHelper;

/**
 * 明细页面,listView每条明细可展开,里面会显示对应的日明细
 * 
 * @author Engle
 * 
 */
abstract public class FragmentGroupDetail extends Fragment {

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 加载页面资源
		View rootView = inflater.inflate(R.layout.fragment_month_detail_list,
				null);
		// 父视图对象
		GroupDetailItem groupDetailItem;
		// 子元素的数据日期
		List<List<DayDetailItem>> dayDetailList;
		// 初始化数据库
		DetailDatabaseHelper dbHelper = new DetailDatabaseHelper(getActivity(),
				"detail.db3", 1);
		// 加载父视图所需数据
		groupDetailItem = this.loadGroupViewData(dbHelper);
		// 加载子视图所需数据
		dayDetailList = this.loadChildViewData(groupDetailItem, dbHelper);
		ExpandableListView expandableListView = (ExpandableListView) rootView
				.findViewById(R.id.list_view_month_detail);
		expandableListView.setAdapter(new MonthDetailExpandableAdapter(
				getActivity(), groupDetailItem, dayDetailList));

		return rootView;
	}

	/**
	 * 加载父视图数据, 由子类实现
	 */
	abstract protected GroupDetailItem loadGroupViewData(
			DetailDatabaseHelper dbHelper);

	/**
	 * 加载子视图数据
	 */
	protected List<List<DayDetailItem>> loadChildViewData(
			GroupDetailItem groupDetailItem, DetailDatabaseHelper dbHelper) {
		List<List<DayDetailItem>> dayDetailList = new ArrayList<List<DayDetailItem>>();
		for (int i = 0; i < groupDetailItem.getDateRangeList().size(); i++) {
			List<DayDetailItem> dayDetail = new ArrayList<DayDetailItem>();
			Cursor cursor = dbHelper
					.getReadableDatabase()
					.rawQuery(
							"select amount,type,date,accountType from detail_record where date > ? and date < ? order by date desc",
							new String[] {
									groupDetailItem.getDateRangeStartList()
											.get(i),
									groupDetailItem.getDateRangeEndList()
											.get(i) });
			while (cursor.moveToNext()) {
				try {
					DayDetailItem dayDetailItem = new DayDetailItem();
					// 用明细日期初始化Calendar
					Calendar calendar = CalendarUtils.StringToCalendar(cursor
							.getString(cursor.getColumnIndex("date")));

					dayDetailItem.setDayDetailMonthDay(String.format("%02d",
							calendar.get(Calendar.DAY_OF_MONTH)));
					dayDetailItem
							.setDayDetailWeekDay(Constant.DAYS_OF_WEEK[calendar
									.get(Calendar.DAY_OF_WEEK)]);
					dayDetailItem.setDayDetailConsumeType(new String(cursor
							.getBlob(cursor.getColumnIndex("type")), "gb2312"));
					dayDetailItem.setDayDetailAccountType(new String(cursor
							.getBlob(cursor.getColumnIndex("accountType")),
							"gb2312"));
					dayDetailItem.setDayDetailConsumeAmount(String.format(
							"%.2f",
							cursor.getDouble(cursor.getColumnIndex("amount"))));
					dayDetail.add(dayDetailItem);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			dayDetailList.add(dayDetail);
		}
		return dayDetailList;
	}

	/**
	 * 扩展ExpandableListAdapter
	 */
	public class MonthDetailExpandableAdapter extends BaseExpandableListAdapter {

		/** 从xml中获取视图对象 */
		private LayoutInflater inflater;

		/** 父视图元素集合 */
		private GroupDetailItem groupDetailItem;

		/** 子视图元素集合 */
		private List<List<DayDetailItem>> dayDetailList;

		public MonthDetailExpandableAdapter(Context context,
				GroupDetailItem groupDetailItem,
				List<List<DayDetailItem>> dayDetailList) {
			inflater = LayoutInflater.from(context);
			this.groupDetailItem = groupDetailItem;
			this.dayDetailList = dayDetailList;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			ViewGroupHolder holder;
			if (null == convertView) {
				convertView = inflater.inflate(
						R.layout.simple_item_month_detail_list, null);
				holder = new ViewGroupHolder();
				holder.textViewWeekIndex = (TextView) convertView
						.findViewById(R.id.month_detail_week_index);
				holder.textViewDateTag = (TextView) convertView
						.findViewById(R.id.month_detail_date_tag);
				holder.textViewDateRange = (TextView) convertView
						.findViewById(R.id.month_detail_date_range);
				holder.textViewAmount = (TextView) convertView
						.findViewById(R.id.month_detail_amount);
				convertView.setTag(holder);
			} else {
				holder = (ViewGroupHolder) convertView.getTag();
			}
			holder.textViewWeekIndex.setText(groupDetailItem.getDateIndex()
					.get(groupPosition));
			holder.textViewDateTag.setText(groupDetailItem.getDateTag());
			holder.textViewDateRange.setText(groupDetailItem.getDateRangeList()
					.get(groupPosition));
			holder.textViewAmount.setText(groupDetailItem.getDateAmountList()
					.get(groupPosition));
			return convertView;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			DayDetailHodler dayDetailHodler;
			if (null == convertView) {
				dayDetailHodler = new DayDetailHodler();
				convertView = inflater.inflate(
						R.layout.simple_item_day_detail_list, null);
				dayDetailHodler.textViewMonthDay = (TextView) convertView
						.findViewById(R.id.day_detail_item_date_month);
				dayDetailHodler.textViewWeekDay = (TextView) convertView
						.findViewById(R.id.day_detail_item_date_week);
				dayDetailHodler.textViewConsumeType = (TextView) convertView
						.findViewById(R.id.day_detail_item_type_consume);
				dayDetailHodler.textViewAccountType = (TextView) convertView
						.findViewById(R.id.day_detail_item_type_account);
				dayDetailHodler.textViewConsumeAmount = (TextView) convertView
						.findViewById(R.id.day_detail_item_consume_amount);
				convertView.setTag(dayDetailHodler);
			} else {
				dayDetailHodler = (DayDetailHodler) convertView.getTag();
			}
			dayDetailHodler.textViewMonthDay.setText(dayDetailList
					.get(groupPosition).get(childPosition)
					.getDayDetailMonthDay());
			dayDetailHodler.textViewWeekDay.setText(dayDetailList
					.get(groupPosition).get(childPosition)
					.getDayDetailWeekDay());
			dayDetailHodler.textViewConsumeType.setText(dayDetailList
					.get(groupPosition).get(childPosition)
					.getDayDetailConsumeType());
			dayDetailHodler.textViewAccountType.setText(dayDetailList
					.get(groupPosition).get(childPosition)
					.getDayDetailAccountType());
			dayDetailHodler.textViewConsumeAmount.setText(dayDetailList
					.get(groupPosition).get(childPosition)
					.getDayDetailConsumeAmount());
			return convertView;
		}

		@Override
		public int getGroupCount() {
			return dayDetailList.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return dayDetailList.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	/**
	 * 父视图明细的holder
	 */
	public static class ViewGroupHolder {
		private TextView textViewWeekIndex;
		private TextView textViewDateTag;
		private TextView textViewDateRange;
		private TextView textViewAmount;
	}

	/**
	 * 日明细视图的holder
	 */
	public static class DayDetailHodler {
		private TextView textViewMonthDay;
		private TextView textViewWeekDay;
		private TextView textViewConsumeType;
		private TextView textViewAccountType;
		private TextView textViewConsumeAmount;
	}
}
