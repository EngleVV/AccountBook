package com.example.myapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.GlobleData;
import com.example.myapp.R;

public class UserInfoActivity extends Activity {

	/** 存储配置 */
	private SharedPreferences sharedPreferences;

	/** 读写配置 */
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);

		sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
		editor = sharedPreferences.edit();
		setButtonBack(this);
		setButtonLogout(this);
	}

	private void setButtonBack(final Context context) {
		ImageView imageViewBack = (ImageView) findViewById(R.id.user_info_title_btn_back);
		imageViewBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 设置退出登录按钮事件
	 */
	private void setButtonLogout(final Context context) {
		Button buttonLogout = (Button) findViewById(R.id.user_info_btn_logout);
		buttonLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.putString("username", null);
				editor.putString("sessionId", null);
				editor.apply();
				GlobleData globleData = (GlobleData) getApplication();
				globleData.clear();
				Toast.makeText(context, "成功退出登录", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

}
