package com.example.myapp.activities;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.common.util.CalendarUtils;
import com.example.myapp.db.DetailDatabaseHelper;

/**
 * @author Engle 添加一笔记账记录的activity
 */
public class AddDetailActivity extends Activity {

	/** 数据库操作类 */
	private DetailDatabaseHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_detail);

		// 给日期选择器设置当前时间
		TextView textViewConsumeDate = (TextView) findViewById(R.id.add_detail_item_date_value);
		Calendar calendar = Calendar.getInstance();
		textViewConsumeDate.setText(CalendarUtils
				.toStandardDateString(calendar));

		// 保存按钮的事件监听
		Button button = (Button) findViewById(R.id.add_detail_button_submit);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// 要插入数据库的数据
					ContentValues contentValues = new ContentValues();
					// 金额
					EditText editTextAmount = (EditText) findViewById(R.id.add_detail_item_amount_value);
					contentValues.put("amount", editTextAmount.getText()
							.toString());
					Spinner spinnerType = (Spinner) findViewById(R.id.add_detail_item_type_value);
					contentValues.put("type", spinnerType.getSelectedItem()
							.toString().getBytes("gb2312"));
					EditText editTextDate = (EditText) findViewById(R.id.add_detail_item_date_value);
					contentValues
							.put("date", editTextDate.getText().toString());
					Spinner spinnerAccountType = (Spinner) findViewById(R.id.add_detail_item_accounttype_value);
					contentValues.put("accountType", spinnerAccountType
							.getSelectedItem().toString()
							.getBytes("gb2312" + ""));
					dbHelper = new DetailDatabaseHelper(AddDetailActivity.this,
							"detail.db3", 1);
					dbHelper.getWritableDatabase().insert("detail_record",
							null, contentValues);
				} catch (Exception e) {
					Toast.makeText(AddDetailActivity.this, "插入异常",
							Toast.LENGTH_SHORT).show();
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
}
