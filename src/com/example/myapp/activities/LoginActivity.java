package com.example.myapp.activities;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.GlobleData;
import com.example.myapp.LoginSession;
import com.example.myapp.R;
import com.example.myapp.common.util.HttpUtil;
import com.google.gson.Gson;

public class LoginActivity extends Activity {

	/** 读取preferences */
	private SharedPreferences sharedPreferences;

	/** 写入preferences */
	private Editor editor;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Toast.makeText(getApplicationContext(),
					((GlobleData) getApplication()).getSessionId(),
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button buttonLogin = (Button) findViewById(R.id.login_btn_login);
		sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
		editor = sharedPreferences.edit();

		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String username;
				final String password;
				EditText editTextUsername = (EditText) findViewById(R.id.login_username);
				username = editTextUsername.getText().toString();
				EditText editTextPassword = (EditText) findViewById(R.id.login_password);
				password = editTextPassword.getText().toString();
				new Thread() {
					@Override
					public void run() {
						Looper.prepare();
						login(username, password);
						Looper.loop();
					}
				}.start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

	@SuppressWarnings("unchecked")
	private void login(String username, String password) {
		// 登录代码
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		String url = HttpUtil.BASE_URL + "ClientLogin.action";
		try {
			final GlobleData globleData = (GlobleData) getApplication();

			LoginSession loginSession = new LoginSession();
			Gson gson = new Gson();
			loginSession = gson.fromJson(HttpUtil.postRequest(url, map),
					LoginSession.class);
			// 返回sessionid则登录成功
			Log.i(username, loginSession.getSessionId());
			handler.sendEmptyMessage(0x123);
			if (null != loginSession.getSessionId()) {
				globleData.setUsername(username);
				globleData.setSessionId(loginSession.getSessionId());
				editor.putString("sessionid", loginSession.getSessionId());
				Toast.makeText(this, "登录成功" + loginSession.getSessionId(),
						Toast.LENGTH_SHORT).show();
			} else {
				// 登录失败代码
				Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}
