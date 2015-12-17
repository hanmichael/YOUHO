package com.sxt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 本地缓存（xml）工具类
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
