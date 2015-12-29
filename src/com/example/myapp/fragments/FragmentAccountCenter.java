/**
 * 
 */
package com.example.myapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.db.AccountDatabaseHelper;

/**
 * @author Engle 账务中心的fragment页面
 * 
 */
public class FragmentAccountCenter extends Fragment {

	/** 条目的logo */
	private final int logos[] = new int[] { R.drawable.logo_pay_cash,
			R.drawable.logo_pay_bankcard, R.drawable.logo_pay_zhifubao,
			R.drawable.logo_pay_weixin };

	/** 条目标题名称 */
	private final String names[] = new String[] { "现    金", "银行卡", "支付宝", "微    信" };

	/** 账户余额值 */
	private String balanceAmount[] = new String[] { "111.11", "222.22",
			"333.33", "444.44" };

	/** 编辑按钮图标 */
	private final int modify = R.drawable.logo_pay_modify;

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

		AccountListAdapter accountListAdapter = new AccountListAdapter(
				getActivity());

		// 为listView设置simpleAdapter
		ListView listViewAccountList = (ListView) rootView
				.findViewById(R.id.account_list);
		listViewAccountList.setAdapter(accountListAdapter);

		return rootView;
	}

	/**
	 * 内部类
	 */
	public class AccountListAdapter extends BaseAdapter {
		/** 获取视图 */
		LayoutInflater inflater;

		/**
		 * 构造函数
		 * 
		 * @param context
		 *            上下文
		 */
		public AccountListAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(
						R.layout.simple_item_account_list, null);
				viewHolder.imageViewLogo = (ImageView) convertView
						.findViewById(R.id.account_list_logo);
				viewHolder.textViewName = (TextView) convertView
						.findViewById(R.id.account_list_name);
				viewHolder.textViewAmount = (TextView) convertView
						.findViewById(R.id.account_list_amount);
				viewHolder.imageViewModify = (ImageView) convertView
						.findViewById(R.id.account_list_modify);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.imageViewLogo.setImageResource(logos[position]);
			viewHolder.textViewName.setText(names[position]);
			viewHolder.textViewAmount.setText(balanceAmount[position]);
			viewHolder.imageViewModify.setImageResource(modify);

			// 给listView中的按钮绑定点击事件
			viewHolder.imageViewModify
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							new Dialog(getActivity()).show();
						}
					});

			return convertView;
		}
	}

	/**
	 * 方便获取每项中的内容
	 */
	public final class ViewHolder {
		public ImageView imageViewLogo;
		public TextView textViewName;
		public TextView textViewAmount;
		public ImageView imageViewModify;
	}
}
