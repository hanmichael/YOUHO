package com.sxt.base;

import com.sxt.activity.DialogActivity;
import com.sxt.activity.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
/**
 * ��������activity�����߼���
 * */
public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initList();
	}

	//����xml����
	public void create(int LayoutID){
		setContentView(LayoutID);
	}

	//�ؼ���ʼ������
	protected void initView(){}

	//��ʼ�����ݷ���
	protected void initList(){}

	//���Դ���
	public void LOGE(String msg){
		Log.e("",msg);
	}

	//����Toast
	public void Toast(String msg){
		android.widget.Toast.makeText(BaseActivity.this, msg, 0).show();
	}

	//���ҿؼ�����
	public View f(int ViewId){
		return findViewById(ViewId);
	}

	//��������Ի�����ʾ����
	public void showDialog(){
		Intent intent = new Intent(this,DialogActivity.class);
		startActivity(intent);
	}

	//�ر���������Ի��򷽷�
	public void disDiaLog(){
		if(((MyApplication)getApplicationContext()).DialogAct!=null){
			((MyApplication)getApplicationContext()).DialogAct.finish();
			((MyApplication)getApplicationContext()).DialogAct = null;
		}
	}

}