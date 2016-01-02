/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapp.R;
import com.example.myapp.fragments.FragmentDayDetail;
import com.example.myapp.fragments.FragmentMonthDetail;
import com.example.myapp.fragments.FragmentWeekDetail;
import com.example.myapp.fragments.FragmentYearDetail;

public class ShowDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_detail);

		Intent intent = getIntent();
		int position = intent.getIntExtra("position", 0);
		switch (position) {
		case 0:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.show_detail_container,
							new FragmentDayDetail()).commit();
			break;
		case 1:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.show_detail_container,
							new FragmentWeekDetail()).commit();
			break;
		case 2:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.show_detail_container,
							new FragmentMonthDetail()).commit();
			break;
		case 3:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.show_detail_container,
							new FragmentYearDetail()).commit();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_detail, menu);
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
