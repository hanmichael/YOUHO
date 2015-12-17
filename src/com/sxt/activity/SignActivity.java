package com.sxt.activity;

import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sxt.base.BaseActivity;
import com.sxt.base.BaseHandler;
import com.sxt.bean.NetStrInfo;
import com.sxt.bean.UserInfo;
import com.sxt.model.HttpModel;
import com.sxt.thread.HttpThread;

/**
 * 登录activity
 * */
public class SignActivity extends BaseActivity{

	private EditText mSignActivity_NameEdt;
	private EditText mSignActivity_PwdEdt;
	private Button mSignBtn;

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_sign);
		mSignActivity_NameEdt = (EditText) f(R.id.SignActivity_NameEdt);
		mSignActivity_PwdEdt = (EditText) f(R.id.SignActivity_PwdEdt);
		mSignBtn = (Button) f(R.id.SignBtn);
		
		mSignBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				NetStrInfo info = new NetStrInfo();
				info.arg1 = 0;
				info.ctx = SignActivity.this;
				info.hand = hand;
				info.interfaceStr = HttpModel.SIGNURL;
				info.netFlag = 1;
				info.pramase = "parames={\"name\":\""+mSignActivity_NameEdt.getText().toString()+"\"," +
						"\"pwd\":\""+mSignActivity_PwdEdt.getText().toString()+"\"}";
				((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
			}
		});
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what != 200){
				return;
			}
			//登录返回值
			if(msg.arg1 == 0){

				List<UserInfo>list = (List<UserInfo>) msg.obj;
				if(list.size()==0){
					Toast("登录失败");	
				}else{
					//登录成功
					//保存用户信息到本地
					((MyApplication)getApplicationContext()).userInfo = list.get(0);
					((MyApplication)getApplicationContext()).share.writeXML("id", list.get(0).id);
					((MyApplication)getApplicationContext()).share.writeXML("NickName", list.get(0).NickName);
					((MyApplication)getApplicationContext()).share.writeXML("PassWod", list.get(0).PassWod);
					((MyApplication)getApplicationContext()).share.writeXML("UserBirthday", list.get(0).UserBirthday);
					((MyApplication)getApplicationContext()).share.writeXML("UserHead", list.get(0).UserHead);
					((MyApplication)getApplicationContext()).share.writeXML("UserName", list.get(0).UserName);
					((MyApplication)getApplicationContext()).share.writeXML("UserSex", list.get(0).UserSex);
					//关闭当前界面
					finish();
				}
			}

		};
	};

}
