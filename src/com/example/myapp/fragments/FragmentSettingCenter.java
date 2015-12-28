/**
 * 
 */
package com.example.myapp.fragments;

import com.example.myapp.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Engle
 * 
 */
public class FragmentSettingCenter extends Fragment {
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting_center, null);
	}

}
