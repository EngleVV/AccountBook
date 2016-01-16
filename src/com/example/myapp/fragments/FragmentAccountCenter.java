/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments;

import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.AccountItem;
import com.example.myapp.R;
import com.example.myapp.db.AccountDatabaseHelper;

/**
 * @author Engle 账务中心的fragment页面
 * 
 */
public class FragmentAccountCenter extends Fragment {

	/** 条目的logo */
	private final int logos[] = new int[] { R.drawable.logo_pay_cash,
			R.drawable.logo_pay_bankcard, R.drawable.logo_pay_ewallet,
			R.drawable.logo_pay_others };

	/** 编辑按钮图标 */
	private final int modify = R.drawable.logo_pay_modify;

	/** 数据库操作对象 */
	private AccountDatabaseHelper accountDbHelper;

	/** 页面对象 */
	private View rootView;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_account_center, null);
		accountDbHelper = new AccountDatabaseHelper(getActivity(),
				"account.db3", 1);

		// 从数据库中获取值来显示
		final List<AccountItem> accountList = accountDbHelper
				.queryAccountList();
		AccountListAdapter accountListAdapter = new AccountListAdapter(
				getActivity(), accountList);

		// 为listView设置simpleAdapter
		ListView listViewAccountList = (ListView) rootView
				.findViewById(R.id.account_list);
		listViewAccountList.setAdapter(accountListAdapter);

		// 添加账户按钮
		setAddAccountButton(inflater);

		return rootView;
	}

	private void setAddAccountButton(final LayoutInflater inflater) {
		ImageButton imageButtonAddAccount = (ImageButton) rootView
				.findViewById(R.id.account_center_title_add_account);
		imageButtonAddAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final View dialogShowView = inflater.inflate(
						R.layout.dialog_view_add_account, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("新增自定义账户")
						.setIcon(R.id.account_center_title_add_account)
						.create();
				builder.setView(dialogShowView)
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										AccountItem item = new AccountItem();
										EditText editViewAccountName = (EditText) dialogShowView
												.findViewById(R.id.add_account_name);
										String accountName = editViewAccountName
												.getText().toString();
										Spinner spinnerAccountType = (Spinner) dialogShowView
												.findViewById(R.id.add_account_type);
										String accountType = spinnerAccountType
												.getSelectedItem().toString();
										String accountAmount = "0.00";
										int priority = spinnerAccountType
												.getSelectedItemPosition();

										item.setAccountAmount(accountAmount);
										item.setAccountName(accountName);
										item.setAccountType(accountType);
										item.setPriority(priority);
										accountDbHelper.insertAccount(item);
										refresh();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).create().show();

			}
		});
	}

	/**
	 * 刷新页面
	 */
	public void refresh() {
		// SetViewAmount();
		ListView listViewAccountList = (ListView) rootView
				.findViewById(R.id.account_list);
		AccountListAdapter accountListAdapter = new AccountListAdapter(
				getActivity(), accountDbHelper.queryAccountList());
		listViewAccountList.setAdapter(accountListAdapter);
		listViewAccountList.invalidate();
	}

	/**
	 * 内部类
	 */
	public class AccountListAdapter extends BaseAdapter {
		/** 获取视图 */
		private LayoutInflater inflater;

		/** 数据 */
		private List<AccountItem> accountList;

		/**
		 * 构造函数
		 * 
		 * @param context
		 *            上下文
		 */
		public AccountListAdapter(Context context, List<AccountItem> list) {
			inflater = LayoutInflater.from(context);
			accountList = list;
		}

		@Override
		public int getCount() {
			return accountList.size();
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

			viewHolder.imageViewLogo.setImageResource(logos[accountList.get(
					position).getPriority()]);
			viewHolder.textViewName.setText(accountList.get(position)
					.getAccountName());
			viewHolder.textViewAmount.setText(accountList.get(position)
					.getAccountAmount());
			viewHolder.imageViewModify.setImageResource(modify);

			// 给listView中的按钮绑定点击事件
			viewHolder.imageViewModify
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							View dialogShowView = inflater.inflate(
									R.layout.dialog_view_modify_balance, null);
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setTitle(
									"修改"
											+ accountList.get(position)
													.getAccountName() + "账户余额")
									.setIcon(modify).create();
							builder.setView(dialogShowView);
							setPositiveButtion(builder, dialogShowView,
									position, accountList);
							setNegativeButtion(builder).create().show();

						}
					});

			convertView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("删除")
							.setMessage("是否确认删除")
							.setPositiveButton("确认",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

											if (accountDbHelper
													.deleteAccount(accountList
															.get(position)
															.getId())) {
												Toast.makeText(getActivity(),
														"删除成功",
														Toast.LENGTH_SHORT)
														.show();
												refresh();
											}

										}

									}).create().show();
					return true;

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
				final int position, final List<AccountItem> list) {
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
								accountDbHelper.modifyBalance(list
										.get(position).getId(), strAmount);

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
