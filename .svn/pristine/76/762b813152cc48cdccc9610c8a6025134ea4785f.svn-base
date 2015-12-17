package com.sxt.activity;

import com.sxt.base.BaseActivity;
import com.sxt.model.WindowModel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private Button mBoysBtn,mGrilsBtn,mKidsBtn,mLifeBtn;
	private long lastTime = 0l;

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_login);
		mBoysBtn = (Button) f(R.id.loginAct_BoysBtn);
		mGrilsBtn = (Button) f(R.id.loginAct_GrilsBtn);
		mKidsBtn = (Button) f(R.id.loginAct_KidsBtn);
		mLifeBtn = (Button) f(R.id.loginAct_LifeBtn);
		myWindow();
		mBoysBtn.setOnClickListener(this);
		mGrilsBtn.setOnClickListener(this);
		mKidsBtn.setOnClickListener(this);
		mLifeBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.loginAct_BoysBtn:
			((MyApplication)getApplicationContext()).ShopID = 1;
			break;
		case R.id.loginAct_GrilsBtn:
			((MyApplication)getApplicationContext()).ShopID = 2;
			break;
		case R.id.loginAct_KidsBtn:
			((MyApplication)getApplicationContext()).ShopID = 3;
			break;
		case R.id.loginAct_LifeBtn:
			((MyApplication)getApplicationContext()).ShopID = 4;
			break;
		}
		Intent intent = new Intent(LoginActivity.this,FrameActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			long time = System.currentTimeMillis();
			if((time - lastTime) <1500){
				finish();	
			}else{
				Toast("双击退出");
				lastTime = time;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	//获取屏幕宽度->界面中slidingmenu最大拖动距离设置使用
	private void myWindow(){
		WindowManager manager = this.getWindowManager();
		WindowModel.WIDTH = manager.getDefaultDisplay().getWidth();
	}

}
