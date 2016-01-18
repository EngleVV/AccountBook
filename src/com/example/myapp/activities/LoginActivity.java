package com.example.myapp.activities;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.GlobleData;
import com.example.myapp.LoginSession;
import com.example.myapp.R;
import com.example.myapp.RegisterActivity;
import com.example.myapp.common.util.HttpUtil;
import com.example.myapp.common.util.InputCheckUtil;
import com.example.myapp.common.util.StringUtil;
import com.google.gson.Gson;

public class LoginActivity extends Activity {

	/** 读取preferences */
	private SharedPreferences sharedPreferences;

	/** 写入preferences */
	private Editor editor;

	private Button buttonLogin;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				Toast.makeText(getApplicationContext(), "登陆成功",
						Toast.LENGTH_SHORT).show();
				finish();
			} else if (msg.what == 0x456) {
				// 登陆失败
				buttonLogin.setText("登录");
				Toast.makeText(getApplicationContext(), "密码错误,请重试",
						Toast.LENGTH_SHORT).show();

			} else if (msg.what == 0x789) {
				// 网络异常
				buttonLogin.setText("登录");
				Toast.makeText(getApplicationContext(), "网络异常,请重试",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		buttonLogin = (Button) findViewById(R.id.login_btn_login);
		sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
		editor = sharedPreferences.edit();

		// 设置返回按钮
		setButtonBack(this);

		// 设置登陆按钮响应事件
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String username;
				final String password;
				EditText editTextUsername = (EditText) findViewById(R.id.login_username);
				username = editTextUsername.getText().toString();
				EditText editTextPassword = (EditText) findViewById(R.id.login_password);
				password = editTextPassword.getText().toString();
				if (InputCheckUtil.CheckUsername(username)) {
					if (InputCheckUtil.CheckPassword(password)) {
						new Thread() {
							@Override
							public void run() {
								Looper.prepare();
								login(username, password);
								Looper.loop();
							}
						}.start();
						// finish();
						// 此处等待,显示登录提示
						buttonLogin.setText("登录中");
					} else {
						// 提示密码输入不合理
						Toast.makeText(getApplicationContext(), "密码长度应为6~16位",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					// 提示用户名输入不合理
					Toast.makeText(getApplicationContext(), "用户名长度应为4~16位",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		// 注册按钮
		TextView textViewRegister = (TextView) findViewById(R.id.login_register);
		textViewRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	private void setButtonBack(final Context context) {
		ImageView imageViewBack = (ImageView) findViewById(R.id.login_title_btn_back);
		imageViewBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

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
			if (!StringUtil.isBlank(loginSession.getSessionId())) {
				globleData.setUsername(username);
				globleData.setSessionId(loginSession.getSessionId());
				globleData.setIsLogin(true);
				editor.putString("username", username);
				editor.putString("sessionId", loginSession.getSessionId());
				editor.apply();
				handler.sendEmptyMessage(0x123);
			} else {
				// 登录失败代码,密码错误
				handler.sendEmptyMessage(0x456);
			}
		} catch (Exception e) {
			// 网络异常
			handler.sendEmptyMessage(0x789);
		}
	}

}
