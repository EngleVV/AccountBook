/**
 * 
 */
package com.example.myapp.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * @author Engle
 * 
 */
public class FragmentMonthDetail extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_month_detail_list,
				null);
		ExpandableListView expandableListView = (ExpandableListView) rootView
				.findViewById(R.id.list_view_month_detail);
		expandableListView.setAdapter(new MonthDetailExpandableAdapter(
				getActivity()));
		return rootView;
	}

	public class MonthDetailExpandableAdapter extends BaseExpandableListAdapter {

		/** 从xml中获取视图对象 */
		LayoutInflater inflater;

		public MonthDetailExpandableAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = inflater.inflate(
					R.layout.simple_item_month_detail_list, null);
			ViewGroupHolder holder = new ViewGroupHolder();
			holder.textViewWeekIndex = (TextView) convertView
					.findViewById(R.id.month_detail_week_index);
			holder.textViewDateRange = (TextView) convertView
					.findViewById(R.id.month_detail_date_range);
			holder.textViewAmount = (TextView) convertView
					.findViewById(R.id.month_detail_amount);
			holder.textViewWeekIndex.setText("1");
			holder.textViewDateRange.setText("01.01-01.03");
			holder.textViewAmount.setText("100.00");
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.fragment_day_detail_list,
					null);
			List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("day_of_month", "1");
			listItem.put("day_of_week", "周五");
			listItem.put("consume_type", "吃饭");
			listItem.put("account_type", "支付宝");
			listItem.put("consume_amount", "199.99");
			listItems.add(listItem);

			SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
					listItems, R.layout.simple_item_day_detail_list,
					new String[] { "day_of_month", "day_of_week",
							"consume_type", "account_type", "consume_amount" },
					new int[] { R.id.day_detail_item_date_month,
							R.id.day_detail_item_date_week,
							R.id.day_detail_item_type_consume,
							R.id.day_detail_item_type_account,
							R.id.day_detail_item_consume_amount });
			ListView listViewChild = (ListView) convertView
					.findViewById(R.id.list_view_day_detail);
			listViewChild.setAdapter(simpleAdapter);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	/**
	 * 方便获取视图中的成员
	 */
	public final class ViewGroupHolder {
		private TextView textViewWeekIndex;
		private TextView textViewDateRange;
		private TextView textViewAmount;
	}
}
