package com.sxt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ���ػ��棨xml��������
 * */

public class SharesUtils {

	private Context ctx;
	private String name = "yoho";

	public SharesUtils(Context ctx){
		this.ctx = ctx;
	}

	public void writeXML(String key,String value){
		SharedPreferences share = ctx.getSharedPreferences(name,0);
		Editor edt = share.edit();
		edt.putString(key, value);
		edt.commit();
	}

	public String readXML(String key){
		SharedPreferences share = ctx.getSharedPreferences(name,0);
		String result = share.getString(key, "");
		return result;
	}

	public void CleadXml(){
		SharedPreferences share = ctx.getSharedPreferences(name,0);
		Editor edt = share.edit();
		edt.putString("id", "");
		edt.commit();
	}
}