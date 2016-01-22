package com.example.myapp;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.common.util.HttpUtil;
import com.example.myapp.common.util.InputCheckUtil;
import com.google.gson.Gson;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x010) {
					// 注册成功
					Toast.makeText(RegisterActivity.this, "注册成功",
							Toast.LENGTH_SHORT).show();
					finish();
				} else if (msg.what == 0x011) {
					// 注册失败
					Toast.makeText(RegisterActivity.this, "注册失败",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 0x012) {
					// 注册异常
					Toast.makeText(RegisterActivity.this, "注册异常",
							Toast.LENGTH_SHORT).show();
				}

			}
		};

		Button buttonRegister = (Button) findViewById(R.id.register_btn_register);
		buttonRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editTextUsr = (EditText) findViewById(R.id.register_username);
				EditText editTextPwd = (EditText) findViewById(R.id.register_password);
				EditText editTextConfirmPwd = (EditText) findViewById(R.id.register_confirm_password);
				String username = editTextUsr.getText().toString();
				String password = editTextPwd.getText().toString();
				String confirmPassword = editTextConfirmPwd.getText()
						.toString();

				if (validateRegisterInfo(username, password, confirmPassword)) {
					register(username, password, confirmPassword, handler);
				}
			}
		});

		ImageView buttonBack = (ImageView) findViewById(R.id.register_title_btn_back);
		buttonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * 校验注册输入信息
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param confirmPassword
	 *            确认密码
	 * @return 结果
	 */
	private Boolean validateRegisterInfo(String username, String password,
			String confirmPassword) {
		if (!InputCheckUtil.CheckUsername(username)) {
			Toast.makeText(getApplicationContext(), "用户名长度应为4~16位",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!InputCheckUtil.CheckPassword(password)) {
			Toast.makeText(getApplicationContext(), "用户名长度应为6~16位",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!password.equals(confirmPassword)) {
			Toast.makeText(getApplicationContext(), "两次输入密码不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void register(final String username, final String password,
			final String confirmPassword, final Handler handler) {
		new Thread() {
			@Override
			public void run() {

				Looper.prepare();

				Map<String, String> map = new HashMap<String, String>();
				String url = HttpUtil.BASE_URL + "ClientRegister.action";
				map.put("username", username);
				map.put("password", password);
				map.put("confirmPassword", confirmPassword);
				ServerResult result;
				Gson gson = new Gson();
				try {
					String strJson = HttpUtil.postRequest(url, map);
					result = gson.fromJson(strJson, ServerResult.class);
					if (result.getResult()) {
						handler.sendEmptyMessage(0x010);
					} else {
						handler.sendEmptyMessage(0x011);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handler.sendEmptyMessage(0x012);
				}
				Looper.loop();
			}
		}.start();

	}
}
