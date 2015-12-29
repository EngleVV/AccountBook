/**
 * 
 */
package com.example.myapp.fragments;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	private final String names[] = new String[] { "现金", "银行卡", "支付宝", "微信" };

	/** 账户余额值 */
	private String balanceAmount[] = new String[] { "111.11", "222.22",
			"333.33", "444.44" };

	/** 编辑按钮图标 */
	private final int modify = R.drawable.logo_pay_modify;

	/** 数据库操作对象 */
	private AccountDatabaseHelper dbHelper;

	/** 页面对象 */
	private View rootView;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_account_center, null);

		// 从数据库中获取值来显示
		SetViewAmount();

		AccountListAdapter accountListAdapter = new AccountListAdapter(
				getActivity());

		// 为listView设置simpleAdapter
		ListView listViewAccountList = (ListView) rootView
				.findViewById(R.id.account_list);
		listViewAccountList.setAdapter(accountListAdapter);

		return rootView;
	}

	/**
	 * 刷新页面
	 */
	public void refresh() {
		SetViewAmount();
		ListView listViewAccountList = (ListView) rootView
				.findViewById(R.id.account_list);
		AccountListAdapter accountListAdapter = new AccountListAdapter(
				getActivity());
		listViewAccountList.setAdapter(accountListAdapter);
		listViewAccountList.invalidate();
	}

	/**
	 * 从数据库中获取余额来显示
	 */
	private void SetViewAmount() {
		dbHelper = new AccountDatabaseHelper(getActivity(), "account.db3", 1);
		SQLiteDatabase readDatabase = dbHelper.getReadableDatabase();
		Cursor cursor = readDatabase.rawQuery(
				"select amount from account_list", null);
		int index = 0;
		while (cursor.moveToNext()) {
			balanceAmount[index++] = String.format("%.2f",
					cursor.getDouble(cursor.getColumnIndex("amount")));
		}
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
							View dialogShowView = inflater.inflate(
									R.layout.simple_item_modify_balance, null);
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setTitle("修改" + names[position] + "账户余额")
									.setIcon(modify).create();
							builder.setView(dialogShowView);
							setPositiveButtion(builder, dialogShowView,
									position);
							setNegativeButtion(builder).create().show();

						}
					});

			return convertView;
		}

		/**
		 * dialog设置确定按钮点击事件
		 * 
		 * @param builder
		 * @return
		 */
		private AlertDialog.Builder setPositiveButtion(
				AlertDialog.Builder builder, final View dialogShowView,
				final int position) {
			return builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							DialogKeepShowing(dialog);
							EditText editTextAmount = (EditText) dialogShowView
									.findViewById(R.id.modify_balance_amount);
							String strAmount = editTextAmount.getText()
									.toString();
							if (ValidateInputAmount(strAmount)) {
								try {
									dbHelper.getWritableDatabase()
											.execSQL(
													"update account_list set amount = ? where id = ?",
													new String[] {
															strAmount,
															String.valueOf(position) });
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								DialogNotKeepShowing(dialog);
								Toast.makeText(getActivity(), "修改成功",
										Toast.LENGTH_SHORT);
								refresh();
							} else {
								Toast.makeText(getActivity(), "输入金额无效,请重新输入",
										Toast.LENGTH_SHORT).show();
								editTextAmount.setText(null);
							}

						}
					});
		}

		/**
		 * 为取消按钮设定点击事件
		 * 
		 * @param builder
		 * @return
		 */
		private AlertDialog.Builder setNegativeButtion(
				AlertDialog.Builder builder) {
			return builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							DialogNotKeepShowing(dialog);
						}
					});
		}

		/**
		 * 校验输入金额是否合法
		 * 
		 * @param strAmount
		 * @return
		 */
		private Boolean ValidateInputAmount(String strAmount) {
			double amount = 0.0;
			try {
				amount = Double.parseDouble(strAmount);
			} catch (Exception e) {
				return false;
			}
			if (amount < 0.0 || amount > 99999999.0)
				return false;
			return true;
		}

		/**
		 * 对话框点击按钮之后不会消失
		 * 
		 * @param dialog
		 */
		private void DialogKeepShowing(DialogInterface dialog) {
			try {
				Field field = dialog.getClass().getSuperclass()
						.getDeclaredField("mShowing");
				field.setAccessible(true);
				field.set(dialog, false);
				dialog.dismiss();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 对话框点击按钮之后会消失
		 * 
		 * @param dialog
		 */
		private void DialogNotKeepShowing(DialogInterface dialog) {
			try {
				Field field = dialog.getClass().getSuperclass()
						.getDeclaredField("mShowing");
				field.setAccessible(true);
				field.set(dialog, true);
				dialog.dismiss();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
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
