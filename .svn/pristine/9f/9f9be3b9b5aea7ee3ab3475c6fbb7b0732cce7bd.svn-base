package com.sxt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
/*
 *Õ¯¬Á≈–∂œπ§æﬂ¿‡ 
 * */
public class IsNetUtils {

	private Context ctx;

	public IsNetUtils(Context ctx){
		this.ctx = ctx;
	}

	public boolean IsNet(){
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info;
		State state;
		info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info!= null){
			state = info.getState();
			if(state == State.CONNECTED){
				flag = true;
				return flag;
			}
		}
		info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(info!= null){
			state = info.getState();
			if(state == State.CONNECTED){
				flag = true;
				return flag;
			}
		}
		return flag;
	}

}
