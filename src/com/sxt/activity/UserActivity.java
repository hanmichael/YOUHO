package com.sxt.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.sxt.base.BaseActivity;
import com.sxt.bean.UserInfo;
import com.sxt.net.img.ImageLoad;
import com.sxt.view.CyclerImg;

public class UserActivity extends BaseActivity implements OnClickListener{

	private TextView mBack_Tv;
	private TextView mUserValueActivity_NickName;
	private TextView mUserValueActivity_Sex;
	private TextView mUserValueActivity_Birthday;
	private com.sxt.view.CyclerImg mHead;
	private ImageLoad load;

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_uservalue);
		mBack_Tv = (TextView) f(R.id.Back_Tv);
		mUserValueActivity_NickName = (TextView) f(R.id.UserValueActivity_NickName);
		mUserValueActivity_Sex = (TextView) f(R.id.UserValueActivity_Sex);
		mUserValueActivity_Birthday = (TextView) f(R.id.UserValueActivity_Birthday);
		mHead = (CyclerImg) f(R.id.UserValueActivity_HeadImg);
		mBack_Tv.setOnClickListener(this);
		mHead.setOnClickListener(this);
		UserInfo info = ((MyApplication)getApplicationContext()).userInfo;
		mUserValueActivity_NickName.setText(info.NickName);
		if(info.UserSex.equals("1"))
			mUserValueActivity_Sex.setText("男");
		else 
			mUserValueActivity_Sex.setText("女");
		mUserValueActivity_Birthday.setText(info.UserBirthday);
		load = new ImageLoad();
		load.LoadImg(info.UserHead, mHead);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(((MyApplication)getApplicationContext()).mheadBitmap == null){
		}else{
			mHead.setImageBitmap(((MyApplication)getApplicationContext()).mheadBitmap);
		}
	}

	@Override
	public void onClick(View arg0) {
		int ID = arg0.getId();
		if(R.id.Back_Tv == ID){
			finish();
		}else{
			//切换头像->选择头像获取方式

		}
	}

}
