package com.sxt.base;

import com.sxt.activity.DialogActivity;
import com.sxt.activity.MyApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 * */
public class BaseFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = initView();
		initList();
		setClick();
		return v;
	}

	public View initView() {
		return null;
	}

	//初始化数据方法
	protected void initList(){

	}

	//设置监听方法
	protected void setClick(){

	}

	//调试处理
	public void LOGE(String msg){
		Log.e("",msg);
	}

	//弹出Toast
	public void Toast(String msg){
		android.widget.Toast.makeText(BaseFragment.this.getActivity(), msg, 0).show();
	}

	//查找控件方法
	public View f(View v ,int ViewId){
		return v.findViewById(ViewId);
	}

	//网络请求对话框显示方法
	public void showDialog(){
		Intent intent = new Intent(getActivity(),DialogActivity.class);
		startActivity(intent);
	}

	//关闭网络请求对话框方法
	public void disDiaLog(){
		if(((MyApplication)getActivity().getApplicationContext()).DialogAct!= null)
			((MyApplication)getActivity().getApplicationContext()).DialogAct.finish();
		for(DialogActivity dia : ((MyApplication)getActivity().getApplicationContext())
				.dialogList){
			dia.finish();
		}
		((MyApplication)getActivity().getApplicationContext()).dialogList.clear();
	}

}
