package com.sxt.fragment;

import android.util.Log;

import com.sxt.base.BaseSeeFragment;
import com.sxt.model.HttpModel;
import com.sxt.view.PullListView.PullCall;

public class See6Fragment extends BaseSeeFragment implements PullCall{


	@Override
	public void onStart() {
		super.onStart();
		//{key:value}->{"key":"value"}\"="
		disAD();
		setPullCall(this);
		showDialog();
		createList(HttpModel.NEWSLISTURL, "parames={\"page\":\"" +
				""+getPage()+"\"" +
				",\"classes\":\"5\"}", false);
	}

	@Override
	public void RefrashCall() {
		//		showDialog();
		setPage(10);
		setClearFlag();
		createList(HttpModel.NEWSLISTURL, "parames={\"page\":\"" +
				""+getPage()+"\"" +
				",\"classes\":\"5\"}", false);
	}

	@Override
	public void LoadCall() {
		Log.e("","LoadCall()"+getPage());
		//		if(getPage()!=-1){
		//		showDialog();
		setPage(10);
		createList(HttpModel.NEWSLISTURL, "parames={\"page\":\"" +
				""+getPage()+"\"" +
				",\"classes\":\"5\"}", false);
		//		}
	}

}
