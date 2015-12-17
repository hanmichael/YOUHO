package com.sxt.activity;

import android.view.View;

import com.sxt.base.BaseActivity;

public class SettingActivity extends BaseActivity{

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_setting);
		findViewById(R.id.Setting_OutBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				((MyApplication)getApplicationContext()).share.CleadXml();
				((MyApplication)getApplicationContext()).userInfo.id = "";
				finish();
			}
		});
	}

}
