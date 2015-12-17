package com.sxt.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxt.adapter.GoodsListAdapter;
import com.sxt.adapter.GoodsListPagerAdapter;
import com.sxt.base.BaseActivity;
import com.sxt.base.BaseHandler;
import com.sxt.bean.BrandValueInfo;
import com.sxt.bean.GoodsInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.model.HttpModel;
import com.sxt.model.WindowModel;
import com.sxt.thread.HttpThread;
import com.sxt.view.SlidingRightUi;
/**
 * 商品列表界面
 * */
public class GoodsListActivity extends BaseActivity 
implements OnClickListener,OnPageChangeListener,AnimationListener,
OnItemClickListener{

	private SlidingRightUi mRightUI;
	private View mTopView;
	private View mBottemView;
	//topview下控件
	private RelativeLayout mGoodsList_TopRelative;
	private ViewPager mVp;
	private ImageView mBackImg;
	private ImageView mArrowImg;
	private TextView mNameTv;
	private TextView mCategoryTv;
	private RelativeLayout mLeft,mCenter,mRight;
	private TextView mLeftTv,mCenterTv,mRightTv;
	private ImageView mLeftImg,mCenterImg,mRightImg;
	private LinearLayout mLineLinear;
	private TextView mChildLine;
	private TextView mGoodsList_ValueTv;
	//bottemview下控件
	private String BrandName;
	private String BrandValue;
	private int page;
	private String id;
	//数据源
	private List<BrandValueInfo>list;
	//适配器
	private GoodsListAdapter adapter;
	//切换顶部价格以及折扣箭头方向标志
	private boolean priceFlag = false;//false向上true向下
	private boolean scaleFlag = false;
	//viewpager索引在多次选择同一页卡切换imageview箭头
	private int index = 0;
	//viewpager中使用gridview数据源
	List<GridView>gvList = new ArrayList<GridView>();
	//Viewpager使用适配器
	private GoodsListPagerAdapter pagerAdapter;
	private TranslateAnimation in;
	private TranslateAnimation out;

	@Override
	protected void initView() {
		super.initView();
		in = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.goodslist_in);
		out = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.goodslist_out);
		out.setAnimationListener(this);
		BrandName = getIntent().getStringExtra("name");
		id = getIntent().getStringExtra("id");
		//		BrandValue = getIntent().getStringExtra("value");
		create(R.layout.activity_goodslist);
		mRightUI = (SlidingRightUi) f(R.id.GoodsList_Sl);
		mTopView = View.inflate(this, R.layout.view_goodstop, null);
		mBottemView = View.inflate(this, R.layout.view_bottemgoods, null);
		//初始化topview控件
		mBackImg = (ImageView) mTopView.findViewById(R.id.GoodsList_MyBackImg);
		mGoodsList_ValueTv = (TextView) mTopView.findViewById(R.id.GoodsList_ValueTv);
		mGoodsList_TopRelative = (RelativeLayout) mTopView.findViewById(R.id.GoodsList_TopRelative);
		mNameTv = (TextView) mTopView.findViewById(R.id.GoodsList_NameTv);
		mArrowImg = (ImageView) mTopView.findViewById(R.id.GoodsList_ArrImg);
		mCategoryTv = (TextView) mTopView.findViewById(R.id.GoodsList_CadegoryTv);
		mLeft = (RelativeLayout) mTopView.findViewById(R.id.GoodsList_LeftRelative);
		mCenter = (RelativeLayout) mTopView.findViewById(R.id.GoodsList_CenterRelative);
		mRight = (RelativeLayout) mTopView.findViewById(R.id.GoodsList_RightRelative);
		mLeftTv = (TextView) mTopView.findViewById(R.id.GoodsList_LeftTv);
		mCenterTv = (TextView) mTopView.findViewById(R.id.GoodsList_CenterTv);
		mRightTv = (TextView) mTopView.findViewById(R.id.GoodsList_RightTv);
		mLeftImg = (ImageView) mTopView.findViewById(R.id.GoodsList_LeftImg);
		mCenterImg = (ImageView) mTopView.findViewById(R.id.GoodsList_CenterImg);
		mRightImg = (ImageView) mTopView.findViewById(R.id.GoodsList_RightImg);
		mLineLinear = (LinearLayout) mTopView.findViewById(R.id.GoodsList_Line);
		mVp = (ViewPager) mTopView.findViewById(R.id.GoodsList_Vp);
		mChildLine = new TextView(this);
		mChildLine.setLayoutParams
		(new LinearLayout.LayoutParams
				(WindowModel.WIDTH/3, LinearLayout.LayoutParams.MATCH_PARENT));
		mChildLine.setBackgroundColor(Color.BLACK);
		mLineLinear.addView(mChildLine);
		mVp.setOnPageChangeListener(this);
		//初始化bottemview控件
		//TopView下控件添加监听
		mNameTv.setText(BrandName);
		mBackImg.setOnClickListener(this);
		mNameTv.setOnClickListener(this);
		mCategoryTv.setOnClickListener(this);
		mLeft.setOnClickListener(this);
		mCenter.setOnClickListener(this);
		mRight.setOnClickListener(this);
		mRightUI.setTopView(mTopView);
		mRightUI.setBottemView(mBottemView);
		mRightUI.setVPFlag(false);
		mGoodsList_TopRelative.measure(0, 0);
		Log.e("","mGoodsList_TopRelative.getMeasuredHeight():"+mGoodsList_TopRelative.getMeasuredHeight());
		mRightUI.setTopHeight(mGoodsList_TopRelative.getMeasuredHeight());
	}

	@Override
	protected void initList() {
		super.initList();

		//初始化viewpager中gridview
		for(int i = 0;i < 3;i ++){

			GridView gv = (GridView) View.inflate(this, R.layout.view_goodslist, null);
			gv.setId(i);
			gv.setOnItemClickListener(this);
			gv.setLayoutParams
			(new ViewGroup.LayoutParams
					(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT));
			//			if(i == 1)
			//				gv.setBackgroundColor(Color.WHITE);
			gvList.add(gv);
		}
		pagerAdapter = new GoodsListPagerAdapter(gvList);
		mVp.setAdapter(pagerAdapter);
		//发起网络请求
		NetStrInfo info = new NetStrInfo();
		info.arg1 = 0;
		info.ctx = this;
		info.hand = hand;
		info.interfaceStr = HttpModel.BRANDVALUEURL;
		info.netFlag = 1;
		info.pramase = "parames={\"page\":\""+page+"\"," +
				"\"id\":\""+id+"\"}";
		((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what == 200){
				if(msg.arg1 == 0){
					list = (List<BrandValueInfo>) msg.obj;
					if(list==null){
						return;
					}
					LOGE(""+list.size());
					//设置品牌详情内容
					mGoodsList_ValueTv.setText(list.get(0).value);
					adapter = new GoodsListAdapter(list.get(0).list, GoodsListActivity.this);
					for(GridView gv:gvList){
						gv.setAdapter(adapter);
					}
				}
			}
		};
	};


	@Override
	public void onClick(View v) {

		int ID = v.getId();
		switch(ID){
		case R.id.GoodsList_MyBackImg:
			finish();
			break;
		case R.id.GoodsList_NameTv:
			if(mGoodsList_ValueTv.getVisibility() == View.VISIBLE){
				//				mGoodsList_ValueTv.setVisibility(View.GONE);
				mArrowImg.setImageResource(R.drawable.arrow_gray_down);
				mGoodsList_ValueTv.startAnimation(out);
			}else{
				mGoodsList_ValueTv.setVisibility(View.VISIBLE);
				mGoodsList_ValueTv.startAnimation(in);
				mArrowImg.setImageResource(R.drawable.shared_segmentedcontrol_up_normal);
			}
			break;
		case R.id.GoodsList_CadegoryTv:
			if(mRightUI.IsSliding()){
				mRightUI.stopSliding();
			}else{
				mRightUI.startSliding();
			}
			break;
		case R.id.GoodsList_LeftRelative:
			index = 0;
			mVp.setCurrentItem(0);
			break;
		case R.id.GoodsList_CenterRelative:
			if(index == 1){
				//二次点击->imageview箭头标志
				priceFlag=!priceFlag;
				if(!priceFlag)
					mCenterImg.setImageResource(R.drawable.shared_segmentedcontrol_2_up);
				else
					mCenterImg.setImageResource(R.drawable.shared_segmentedcontrol_2_down);
			}
			index = 1;
			mVp.setCurrentItem(1);
			break;
		case R.id.GoodsList_RightRelative:
			if(index == 2){
				scaleFlag=!scaleFlag;
				if(!scaleFlag)
					mRightImg.setImageResource(R.drawable.shared_segmentedcontrol_2_up);
				else
					mRightImg.setImageResource(R.drawable.shared_segmentedcontrol_2_down);
			}
			index = 2;
			mVp.setCurrentItem(2);
			break;
		}

	}


	@Override
	public void onPageScrollStateChanged(int arg0) {

	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Log.e("","arg0:"+arg0);
		Log.e("","arg1:"+arg1);
		if(arg0 == (gvList.size()-1)&& arg1 == 0){
			//ViewPager滑动到最后一页并且持续从右向左滑动
			mRightUI.setVPFlag(true);
		}else{
			mRightUI.setVPFlag(false);
		}
		LinearLayout.LayoutParams lp = (LayoutParams) mChildLine.getLayoutParams();
		lp.leftMargin = WindowModel.WIDTH/3*arg0 +(int) ((WindowModel.WIDTH/3)*arg1);
		mChildLine.setLayoutParams(lp);
	}


	@Override
	public void onPageSelected(int arg0) {
		Log.e("","arg0:"+arg0);
		clearTopStyle();
		LinearLayout.LayoutParams lp = (LayoutParams) mChildLine.getLayoutParams();
		switch(arg0){
		case 0:
			mLeftTv.setTextColor(Color.BLACK);
			mLeftImg.setImageResource(R.drawable.shared_segmentedcontrol_1_down);
			lp.leftMargin = 0;
			break;
		case 1:
			mCenterTv.setTextColor(Color.BLACK);
			lp.leftMargin = WindowModel.WIDTH/3;
			if(!priceFlag)
				mCenterImg.setImageResource(R.drawable.shared_segmentedcontrol_2_up);
			else
				mCenterImg.setImageResource(R.drawable.shared_segmentedcontrol_2_down);
			break;
		case 2:
			mRightTv.setTextColor(Color.BLACK);
			if(!scaleFlag)
				mRightImg.setImageResource(R.drawable.shared_segmentedcontrol_2_up);
			else
				mRightImg.setImageResource(R.drawable.shared_segmentedcontrol_2_down);
			lp.leftMargin = WindowModel.WIDTH/3*2;
			break;
		}
		mChildLine.setLayoutParams(lp);
	}

	//清理顶部最新，价格，折扣样式方法
	private void clearTopStyle(){
		mLeftTv.setTextColor(Color.GRAY);
		mCenterTv.setTextColor(Color.GRAY);
		mRightTv.setTextColor(Color.GRAY);
		mLeftImg.setImageResource(R.drawable.shared_segmentedcontrol_1_normal);
		mCenterImg.setImageResource(R.drawable.shared_segmentedcontrol_2_normal);
		mRightImg.setImageResource(R.drawable.shared_segmentedcontrol_2_normal);
	}


	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		mGoodsList_ValueTv.setVisibility(View.GONE);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListAdapter adapter = (ListAdapter) parent.getAdapter();
		GoodsInfo info = (GoodsInfo) adapter.getItem(position);
		Intent intent = new Intent(this,GoodsValueActivity.class);
		intent.putExtra("goods", info._id);
		intent.putExtra("name", BrandName);
		intent.putExtra("img", info.goodsimg);
		startActivity(intent);
	}

}
