package com.sxt.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sxt.base.BaseActivity;

/**
 * 选择获取头像方式
 * 调用系统相机以及图库获取图片->Intent
 * */
public class SelectPhotoActivity extends BaseActivity 
implements OnClickListener{

	private Button mCamaroBtn,mGalleryBtn,mCanelBtn;

	@Override
	protected void initView() {
		super.initView();
		mCamaroBtn = (Button) f(R.id.CamaroBtn);
		mGalleryBtn = (Button) f(R.id.GalleryBtn);
		mCanelBtn = (Button) f(R.id.CanelBtn);
		mCamaroBtn.setOnClickListener(this);
		mGalleryBtn.setOnClickListener(this);
		mCanelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		int ID = arg0.getId();
		switch(ID){
		case R.id.CamaroBtn:
			Intent intent = new Intent(this,CameraActivity.class);
			startActivity(intent);
			break;
		case R.id.GalleryBtn:
			Intent intent1 = new Intent(this,PhotoAct.class);
			startActivity(intent1);
			break;
		case R.id.CanelBtn:
			finish();
			break;
		}
	}

}
