package com.sxt.base;

import com.sxt.activity.MyApplication;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BaseHandler extends Handler{

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if(msg.what != 200){
			String result = (String) msg.obj;
			Toast.makeText(MyApplication.ctx, result, 0).show();
		}
	}

}
