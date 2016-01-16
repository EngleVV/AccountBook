/*
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */

package com.example.myapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapp.ListenerStartOpponent;
import com.example.myapp.MessageHandler;
import com.example.myapp.R;
import com.example.myapp.activities.LoginActivity;
import com.example.myapp.manager.DownloadManager;
import com.example.myapp.services.ServiceUpload;

/**
 * @author Engle
 * 
 */
public class FragmentSettingCenter extends Fragment {

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_setting_center, null);

		// 跳转至登陆页面
		Button btnLogin = (Button) rootView
				.findViewById(R.id.setting_center_btn_login);
		btnLogin.setOnClickListener(new ListenerStartOpponent(getActivity(),
				LoginActivity.class));

		// 同步数据至服务器
		Button buttonUploadToServer = (Button) rootView
				.findViewById(R.id.setting_center_update_to_server);
		buttonUploadToServer.setOnClickListener(new ListenerStartOpponent(
				getActivity(), ServiceUpload.class));

		// 从数据库同步到本地
		Button buttonDownloadToLocal = (Button) rootView
				.findViewById(R.id.setting_center_download_to_local);
		// buttonDownloadToLocal.setOnClickListener(new ListenerStartOpponent(
		// getActivity(), ServiceDownload.class));
		buttonDownloadToLocal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DownloadManager.DownloadToLocal(new MessageHandler(
						getActivity()), getActivity());
			}
		});
		return rootView;
	}

}
