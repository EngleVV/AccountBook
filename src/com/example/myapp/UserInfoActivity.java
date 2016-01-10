package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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
		setButtonLogout(this);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
