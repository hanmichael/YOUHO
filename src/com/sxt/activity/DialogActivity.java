package com.sxt.activity;

import android.view.KeyEvent;
import android.widget.ImageView;
import com.sxt.base.BaseActivity;
import com.sxt.model.ResModel;
import com.sxt.utils.animation.FrameUtils;

public class DialogActivity extends BaseActivity{

	private ImageView mImg;
	FrameUtils utils = new FrameUtils();

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_dialog);
		((MyApplication)getApplicationContext()).DialogAct = this;
		((MyApplication)getApplicationContext()).dialogList.add(this);
		mImg = (ImageView) f(R.id.DialogAct_Img);
		utils.create(this, ResModel.FRAMEDRAWABLE, mImg);
		utils.startFrame();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK){
			finish();
			((MyApplication)getApplicationContext()).DialogAct = null;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
