/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.fragments.FragmentAccountCenter;
import com.example.myapp.fragments.FragmentMainPage;
import com.example.myapp.fragments.FragmentSettingCenter;

/**
 * 主页面的activity
 * 
 * @author Engle
 * 
 */
public class MainIndexActivity extends FragmentActivity {

	/** 底下tab导航栏 */
	private FragmentTabHost fragmentTabHost;

	/** 获取xml中的view */
	private LayoutInflater layoutInflater;

	/** tab导航栏的文字部分 */
	private String[] strTabText = new String[] { "主页", "账户", "更多" };

	/** tab导航栏的图标 */
	private int[] iTabImage = new int[] { R.drawable.tab_btn_home,
			R.drawable.tab_btn_accountbook, R.drawable.tab_btn_more };

	/** tab分页的fragment */
	@SuppressWarnings("rawtypes")
	private Class fragmentArrays[] = new Class[] { FragmentMainPage.class,
			FragmentAccountCenter.class, FragmentSettingCenter.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_index);

		// 初始化layoutInflater
		layoutInflater = LayoutInflater.from(this);

		// fragmentTabHost对象初始化
		fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		fragmentTabHost.setup(this, getSupportFragmentManager(),
				R.id.realtabcontent);

		// 设置tab标签以及页面的信息
		for (int i = 0; i < 3; i++) {
			TabSpec tabSpec = fragmentTabHost.newTabSpec(strTabText[i])
					.setIndicator(getTabItemView(i));
			fragmentTabHost.addTab(tabSpec, fragmentArrays[i], null);
			fragmentTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	// 根据序号返回对应的视图
	@SuppressLint("InflateParams")
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(iTabImage[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(strTabText[index]);
		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_index, menu);
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
