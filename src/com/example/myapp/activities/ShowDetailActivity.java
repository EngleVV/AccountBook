/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.fragments.details.FragmentDayDetail;
import com.example.myapp.fragments.details.FragmentMonthDetail;
import com.example.myapp.fragments.details.FragmentWeekDetail;
import com.example.myapp.fragments.details.FragmentYearDetail;

public class ShowDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_detail);
		setButtonBack(this);
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

	private void setButtonBack(final Context context) {
		ImageView imageViewBack = (ImageView) findViewById(R.id.show_detail_title_btn_back);
		imageViewBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
