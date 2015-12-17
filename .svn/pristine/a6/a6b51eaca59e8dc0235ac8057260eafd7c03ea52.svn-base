package com.sxt.reciver;

import com.sxt.activity.MyApplication;
import com.sxt.activity.VerssionActivity;

import cn.jpush.android.api.JPushInterface;
import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyPushReceiver extends BroadcastReceiver{

	int code = 1;

	@Override
	public void onReceive(Context arg0, Intent intent) {
		Bundle bundle = intent.getExtras();
		//接收自定义消息
		if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Log.e("","message:"+message);
			Log.e("","extras:"+extras);
			//接收到新版推送消息->与本地版本比较是否更新
			code = Integer.valueOf(message);
			if(code > MyApplication.versionCode){
				Intent intent1 = new Intent(MyApplication.ctx,
						VerssionActivity.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				MyApplication.ctx.startActivity(intent1);
			}
		}
	}

}
