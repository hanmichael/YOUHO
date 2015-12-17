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
 * Fragment����
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

	//��ʼ�����ݷ���
	protected void initList(){

	}

	//���ü�������
	protected void setClick(){

	}

	//���Դ���
	public void LOGE(String msg){
		Log.e("",msg);
	}

	//����Toast
	public void Toast(String msg){
		android.widget.Toast.makeText(BaseFragment.this.getActivity(), msg, 0).show();
	}

	//���ҿؼ�����
	public View f(View v ,int ViewId){
		return v.findViewById(ViewId);
	}

	//��������Ի�����ʾ����
	public void showDialog(){
		Intent intent = new Intent(getActivity(),DialogActivity.class);
		startActivity(intent);
	}

	//�ر���������Ի��򷽷�
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