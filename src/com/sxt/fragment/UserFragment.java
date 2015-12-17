package com.sxt.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxt.activity.MyApplication;
import com.sxt.activity.R;
import com.sxt.activity.SettingActivity;
import com.sxt.activity.SignActivity;
import com.sxt.activity.UserActivity;
import com.sxt.base.BaseFragment;
import com.sxt.net.img.ImageLoad;
import com.sxt.view.CyclerImg;

/**
 * 用户fragment
 * 1.用户是否登录
 * */
public class UserFragment extends BaseFragment implements OnClickListener{

	private View view;
	private com.sxt.view.CyclerImg mMyFragment_UserHead;
	private TextView mMyFragment_UserName;
	private Button mMyFragment_LoginBtn;
	private ImageView mMyFragment_SetImg;
	private ImageLoad load = new ImageLoad();

	@Override
	public View initView() {
		view = View.inflate(getActivity(), R.layout.fragment_my, null);
		mMyFragment_UserHead = (CyclerImg) view.findViewById(R.id.MyFragment_UserHead);
		mMyFragment_UserName = (TextView) view.findViewById(R.id.MyFragment_UserName);
		mMyFragment_LoginBtn = (Button) view.findViewById(R.id.MyFragment_LoginBtn);
		mMyFragment_SetImg = (ImageView) view.findViewById(R.id.MyFragment_SetImg);
		return view;
	}

	@Override
	protected void setClick() {
		super.setClick();
		mMyFragment_LoginBtn.setOnClickListener(this);
		mMyFragment_SetImg.setOnClickListener(this);
		mMyFragment_UserName.setOnClickListener(this);
	}

	private void updateUserView(){
		//区分用户是否登录
		if(((MyApplication)getActivity().
				getApplicationContext()).userInfo.id.equals("")){
			//用户没有登录->隐藏头像以及昵称空间
			mMyFragment_UserHead.setVisibility(View.GONE);
			mMyFragment_UserName.setVisibility(View.GONE);
			mMyFragment_LoginBtn.setVisibility(View.VISIBLE);
		}else{
			//隐藏登录按钮
			mMyFragment_UserHead.setVisibility(View.VISIBLE);
			mMyFragment_UserName.setVisibility(View.VISIBLE);
			mMyFragment_LoginBtn.setVisibility(View.GONE);
			//设置用户名以及用户头像
			mMyFragment_UserName.setText(((MyApplication)getActivity()
					.getApplicationContext()).userInfo.NickName);
			load.LoadImg(((MyApplication)getActivity()
					.getApplicationContext()).userInfo.UserHead, mMyFragment_UserHead);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUserView();
	}

	@Override
	public void onClick(View arg0) {
		int ID = arg0.getId();
		if(R.id.MyFragment_LoginBtn == ID){
			//跳转登录界面
			Intent intent = new Intent(getActivity(),SignActivity.class);
			startActivity(intent);
		} else if(R.id.MyFragment_SetImg == ID){
			Intent intent = new Intent(getActivity(),SettingActivity.class);
			startActivity(intent);
		}else if(R.id.MyFragment_UserName == ID){
			//跳转用户详细信息界面
			Intent intent = new Intent(getActivity(),UserActivity.class);
			startActivity(intent);
		}
	}


}
