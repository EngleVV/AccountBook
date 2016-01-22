/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.GlobleData;
import com.example.myapp.R;
import com.example.myapp.common.DetailItem;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.common.util.InputCheckUtil;
import com.example.myapp.db.AccountDatabaseHelper;
import com.example.myapp.db.DetailDatabaseHelper;

/**
 * @author Engle 添加一笔记账记录的activity
 */
public class AddDetailActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_detail);

		// 给日期选择器设置当前时间
		TextView textViewConsumeDate = (TextView) findViewById(R.id.add_detail_item_date_value);
		Calendar calendar = Calendar.getInstance();
		textViewConsumeDate.setText(CalendarUtils
				.toStandardDateString(calendar));

		// 给账户名设置选项
		Spinner spinnerAccount = (Spinner) findViewById(R.id.add_detail_item_accounttype_value);
		AccountDatabaseHelper accountDbHelper = new AccountDatabaseHelper(this,
				"account.db3", 1);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				accountDbHelper.queryAccountName());
		spinnerAccount.setAdapter(arrayAdapter);

		// 再记一笔按钮的事件
		Button buttonRecordAgain = (Button) findViewById(R.id.add_detail_button_record_again);
		buttonRecordAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (AddDetail()) {
					// 插入一笔明细之后重新输入
					EditText editTextAmount = (EditText) findViewById(R.id.add_detail_item_amount_value);
					Spinner spinnerType = (Spinner) findViewById(R.id.add_detail_item_type_value);
					EditText editTextDate = (EditText) findViewById(R.id.add_detail_item_date_value);
					Spinner spinnerAccountType = (Spinner) findViewById(R.id.add_detail_item_accounttype_value);

					editTextAmount.setText(null);
					spinnerType.setSelection(0);
					editTextDate.setText(CalendarUtils
							.toStandardDateString(Calendar.getInstance()));
					spinnerAccountType.setSelection(0);
				}
			}
		});

		// 保存按钮的事件监听
		Button button = (Button) findViewById(R.id.add_detail_button_submit);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (AddDetail()) {
					finish();
				}
			}
		});

		// 页面返回按钮,返回上一个页面
		ImageView imageViewBtnBack = (ImageView) findViewById(R.id.add_detail_title_back);
		imageViewBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 标题栏的保存按钮
		ImageView imageViewBtnSave = (ImageView) findViewById(R.id.add_detail_title_save);
		imageViewBtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (AddDetail()) {
					finish();
				}
			}
		});

		final EditText editTextAmountValue = (EditText) findViewById(R.id.add_detail_item_amount_value);
		editTextAmountValue.setFocusable(true);
		editTextAmountValue.setFocusableInTouchMode(true);
		editTextAmountValue.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) editTextAmountValue
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(editTextAmountValue, 0);

			}
		}, 500);
	}

	private Boolean AddDetail() {
		try {
			// 获取各个组件
			EditText editTextAmount = (EditText) findViewById(R.id.add_detail_item_amount_value);
			Spinner spinnerType = (Spinner) findViewById(R.id.add_detail_item_type_value);
			EditText editTextDate = (EditText) findViewById(R.id.add_detail_item_date_value);
			Spinner spinnerAccountType = (Spinner) findViewById(R.id.add_detail_item_accounttype_value);

			// 保存消费类型和账户类型
			String strConsumeAmount = editTextAmount.getText().toString();
			String strConsumeType = spinnerType.getSelectedItem().toString();
			String strAccountType = spinnerAccountType.getSelectedItem()
					.toString();
			String strUsername = ((GlobleData) getApplication()).getUsername();
			strUsername = (null == strUsername) ? "" : strUsername;

			if (InputCheckUtil.CheckAmount(strConsumeAmount)) {
				DetailItem item = new DetailItem();
				item.setDayDetailConsumeAmount(strConsumeAmount);
				item.setDayDetailConsumeType(strConsumeType);
				item.setDayDetailAccountType(strAccountType);
				item.setDayDetailConsumeDate(editTextDate.getText().toString());
				item.setLastModifyDate(CalendarUtils
						.toStandardDateString(new Date()));
				item.setUuid(UUID.randomUUID().toString());
				item.setDayDetailUsername(strUsername);
				List<DetailItem> detailList = new ArrayList<DetailItem>();
				detailList.add(item);
				/** 数据库操作类 */
				DetailDatabaseHelper detailDbHelper = new DetailDatabaseHelper(
						AddDetailActivity.this, "detail.db3", 1);
				detailDbHelper.insertList(detailList);

				// 同时更新对应账户的余额
				AccountDatabaseHelper accountDbHelper = new AccountDatabaseHelper(
						AddDetailActivity.this, "account.db3", 1);
				Double amount = Double.parseDouble(editTextAmount.getText()
						.toString());
				accountDbHelper.cutBalance(strAccountType, amount);
				Toast.makeText(getApplicationContext(), "添加成功",
						Toast.LENGTH_SHORT).show();
				return true;
			} else {
				// 输入金额不合法
				Toast.makeText(getApplicationContext(), "输入金额不合法",
						Toast.LENGTH_SHORT).show();
				return false;
			}

		} catch (Exception e) {
			Toast.makeText(AddDetailActivity.this, "插入异常", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

	}
}
