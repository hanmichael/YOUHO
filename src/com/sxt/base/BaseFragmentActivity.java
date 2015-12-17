package com.sxt.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
/**
 * fragmentActivity����
 * */
public class BaseFragmentActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	//����xml����
	public void create(int LayoutID){
		setContentView(LayoutID);
	}

	//�ؼ���ʼ������
	protected void initView(){}

	//���Դ���
	public void LOGE(String msg){
		Log.e("",msg);
	}

	//����Toast
	public void Toast(String msg){
		android.widget.Toast.makeText(this, msg, 0).show();
	}

	//���ҿؼ�����
	public View f(int ViewId){
		return findViewById(ViewId);
	}

	//��������Ի�����ʾ����
	public void showDialog(){

	}

	//�ر���������Ի��򷽷�
	public void disDiaLog(){

	}

}
