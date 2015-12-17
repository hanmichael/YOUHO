package com.sxt.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
/**
 * fragmentActivity父类
 * */
public class BaseFragmentActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	//关联xml方法
	public void create(int LayoutID){
		setContentView(LayoutID);
	}

	//控件初始化方法
	protected void initView(){}

	//调试处理
	public void LOGE(String msg){
		Log.e("",msg);
	}

	//弹出Toast
	public void Toast(String msg){
		android.widget.Toast.makeText(this, msg, 0).show();
	}

	//查找控件方法
	public View f(int ViewId){
		return findViewById(ViewId);
	}

	//网络请求对话框显示方法
	public void showDialog(){

	}

	//关闭网络请求对话框方法
	public void disDiaLog(){

	}

}
