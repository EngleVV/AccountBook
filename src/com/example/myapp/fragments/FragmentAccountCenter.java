/**
 * 
 */
package com.example.myapp.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myapp.R;
import com.example.myapp.db.AccountDatabaseHelper;

/**
 * @author Engle 账务中心的fragment页面
 * 
 */
public class FragmentAccountCenter extends Fragment {

	/** 条目的logo */
	private int logos[] = new int[] { R.drawable.logo_pay_cash,
			R.drawable.logo_pay_bankcard, R.drawable.logo_pay_zhifubao,
			R.drawable.logo_pay_weixin };

	/** 条目标题名称 */
	private String names[] = new String[] { "现    金", "银行卡", "支付宝", "微    信" };

	/** 账户余额值 */
	private String balanceAmount[] = new String[] { "111.11", "222.22",
			"333.33", "444.44" };

	/** 编辑按钮图标 */
	private int modify = R.drawable.logo_pay_modify;

	/** 数据库操作对象 */
	private AccountDatabaseHelper dbHelper;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_account_center, null);

		// 从数据库中获取值来显示
		dbHelper = new AccountDatabaseHelper(getActivity(), "account.db3", 1);
		SQLiteDatabase readDatabase = dbHelper.getReadableDatabase();
		Cursor cursor = readDatabase.rawQuery(
				"select amount from account_list", null);
		int index = 0;
		while (cursor.moveToNext()) {
			balanceAmount[index++] = String.format("%.2f",
					cursor.getDouble(cursor.getColumnIndex("amount")));
		}

		// 设置adapter的值
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < logos.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("logo", logos[i]);
			item.put("name", names[i]);
			item.put("balanceAmount", balanceAmount[i]);
			item.put("modify", modify);
			listItems.add(item);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
				listItems, R.layout.simple_item_account_list, new String[] {
						"logo", "name", "balanceAmount", "modify" }, new int[] {
						R.id.account_list_logo, R.id.account_list_name,
						R.id.account_list_amount, R.id.account_list_modify });

		// 为listView设置simpleAdapter
		ListView listViewAccountList = (ListView) rootView
				.findViewById(R.id.account_list);
		listViewAccountList.setAdapter(simpleAdapter);

		return rootView;
	}
}
