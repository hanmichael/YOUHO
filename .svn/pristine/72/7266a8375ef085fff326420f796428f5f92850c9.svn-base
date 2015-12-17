package com.sxt.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxt.adapter.ColorOrSizeAdapter;
import com.sxt.base.BaseActivity;
import com.sxt.base.BaseHandler;
import com.sxt.bean.ColorAndSizeInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.callback.RecyclerViewCallBack;
import com.sxt.model.HttpModel;
import com.sxt.net.img.ImageLoad;
import com.sxt.thread.HttpThread;

/**
 * 加入购物车界面
 * */

public class AddCartActivity extends BaseActivity implements 
OnClickListener,AnimationListener,RecyclerViewCallBack{

	private ImageView mImg;
	private TextView mTitleTv;
	private TextView mDiscountTv;
	private TextView mPriceTv;
	private RelativeLayout mAddCartAct_Relative;
	private LinearLayout mColorLinear;
	private LinearLayout mSizeLinear;
	private TextView mLeftTv;
	private TextView mCenterTv;
	private TextView mRightTv;
	private Button mOkBtn;
	private String title,price,discount,img,goodsID;
	private ImageLoad load = new ImageLoad();
	private TranslateAnimation in,out;
	private int num = 1;
	private List<ColorAndSizeInfo>colorList = new ArrayList<ColorAndSizeInfo>();
	private List<ColorAndSizeInfo>sizeList = new ArrayList<ColorAndSizeInfo>();
	private RecyclerView mColorView;
	private RecyclerView mSizeView;
	private ColorOrSizeAdapter colorAdapter;
	private ColorOrSizeAdapter sizeAdapter;
	private int colorID=-1,sizeID=-1;

	@Override
	protected void initView() {
		super.initView();
		title = getIntent().getStringExtra("title");
		price = getIntent().getStringExtra("price");
		discount = getIntent().getStringExtra("discount");
		img = getIntent().getStringExtra("img");
		goodsID = getIntent().getStringExtra("goodsID");
		create(R.layout.activity_addcart);
		mAddCartAct_Relative = (RelativeLayout) f(R.id.AddCartAct_Relative);
		mImg = (ImageView) f(R.id.AddCartAct_Img);
		mTitleTv = (TextView) f(R.id.AddCartAct_TitleTv);
		mDiscountTv = (TextView) f(R.id.AddCartAct_DiscountTv);
		mPriceTv = (TextView) f(R.id.AddCartAct_PriceTv);
		mColorLinear = (LinearLayout) f(R.id.AddCartAct_ColorLinear);
		mSizeLinear = (LinearLayout) f(R.id.AddCartAct_SizeLinear);
		mLeftTv = (TextView) f(R.id.AddCartAct_MinuteTv);
		mCenterTv = (TextView) f(R.id.AddCartAct_NumberTv);
		mRightTv = (TextView) f(R.id.AddCartAct_PlusTv);
		mOkBtn = (Button) f(R.id.AddCartAct_OkBtn);
		mTitleTv.setText(title);
		mPriceTv.setText(price);
		mDiscountTv.setText(discount);
		load.LoadImg(img, mImg);
		in = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.addcart_in);
		out = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.addcart_out);
		mAddCartAct_Relative.startAnimation(in);
		initRecycler();
		out.setAnimationListener(this);
		mOkBtn.setOnClickListener(this);
		mLeftTv.setOnClickListener(this);
		mRightTv.setOnClickListener(this);
	}

	private void initRecycler(){
		mColorView = new RecyclerView(this);
		mColorView.setLayoutParams(
				new LinearLayout.LayoutParams
				( LinearLayout.LayoutParams.MATCH_PARENT,  
						LinearLayout.LayoutParams.MATCH_PARENT));
		LinearLayoutManager lm = new LinearLayoutManager(this);
		lm.setOrientation(LinearLayoutManager.HORIZONTAL);
		mColorView.setLayoutManager(lm);
		mColorView.setItemAnimator(new DefaultItemAnimator());
		mColorView.setHasFixedSize(true);
		mColorLinear.addView(mColorView);
		mSizeView = new RecyclerView(this);
		mSizeView.setLayoutParams(
				new LinearLayout.LayoutParams
				( LinearLayout.LayoutParams.MATCH_PARENT,  
						LinearLayout.LayoutParams.MATCH_PARENT));
		LinearLayoutManager lm1 = new LinearLayoutManager(this);
		lm1.setOrientation(LinearLayoutManager.HORIZONTAL);
		mSizeView.setLayoutManager(lm1);
		mSizeView.setItemAnimator(new DefaultItemAnimator());
		mSizeView.setHasFixedSize(true);
		mSizeLinear.addView(mSizeView);
	}

	@Override
	protected void initList() {
		super.initList();
		//创建颜色以及尺寸虚拟数据
		for(int i = 0;i < 10;i ++){
			ColorAndSizeInfo info = new ColorAndSizeInfo();
			info.id = i;
			info.flag = false;
			info.str = "颜色"+i;
			colorList.add(info);
		}
		for(int i = 0;i < 10;i ++){
			ColorAndSizeInfo info = new ColorAndSizeInfo();
			info.id = i;
			info.flag = false;
			info.str = "尺寸"+i;
			sizeList.add(info);
		}
		//关联颜色以及尺寸适配器
		colorAdapter = new ColorOrSizeAdapter(colorList, this,this,1);
		sizeAdapter = new ColorOrSizeAdapter(sizeList, this,this,2);
		mColorView.setAdapter(colorAdapter);
		mSizeView.setAdapter(sizeAdapter);

	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what != 200)
				return;
			if(msg.arg1 == 0){
				List<String>l = (List<String>) msg.obj;
				if(l.get(0).equals("ok")){
					Toast("添加购物车成功");
					finish();
				}else{
					Toast("添加购物车失败");
				}
			}
		};
	};


	@Override
	public void onClick(View arg0) {
		int ID = arg0.getId();
		switch(ID){
		case R.id.AddCartAct_OkBtn:
			if(colorID == -1){
				Toast("选择购买颜色");
				break;
			}
			if(sizeID == -1){
				Toast("选择购买尺寸");
				break;
			}

			NetStrInfo info = new NetStrInfo();
			info.arg1 = 0;
			info.ctx = this;
			info.hand = hand;
			info.interfaceStr = HttpModel.ADDCARTURL;
			info.netFlag = 1;
			info.pramase = "parames={\"goodsId\":\""+goodsID+"\"," +
					"\"userId\":\""+((MyApplication)getApplicationContext()).userInfo.id+"\"," +
					"\"colorId\":\""+colorID+"\"," +
					"\"sizeId\":\""+sizeID+"\"}";
			((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
			break;
		case R.id.AddCartAct_MinuteTv:
			num-=1;
			if(num < 1){
				num = 1;
			}
			mCenterTv.setText(""+num);
			break;
		case R.id.AddCartAct_PlusTv:
			num+=1;
			mCenterTv.setText(""+num);
			break;
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mAddCartAct_Relative.startAnimation(out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void call(int possion, int ID) {

		if(1 == ID){
			ColorAndSizeInfo info = colorList.get(possion);
			if(!info.flag){
				colorID = info.id;
				//颜色recyclerview被点击
				for(ColorAndSizeInfo info1:colorList){
					info1.flag = false;
				}
			}else{
				colorID = -1;
			}

			info.flag = !info.flag;
			colorList.set(possion, info);
			colorAdapter.notifyDataSetChanged();
		}else{
			ColorAndSizeInfo info = sizeList.get(possion);
			if(!info.flag){
				sizeID = info.id;
				//尺寸recyclerview被点击
				for(ColorAndSizeInfo info1 : sizeList){
					info1.flag = false;
				}
			}else{
				sizeID = -1;
			}
			info.flag = !info.flag;
			sizeList.set(possion, info);
			sizeAdapter.notifyDataSetChanged();
		}
	}


}
