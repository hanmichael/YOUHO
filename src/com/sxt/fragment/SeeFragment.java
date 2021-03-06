package com.sxt.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sxt.activity.R;
import com.sxt.adapter.SeeAdapter;
import com.sxt.base.BaseFragment;
import com.sxt.base.BaseSeeFragment;
import com.sxt.model.ActionModel;

/**
 * 逛fragment
 * */
public class SeeFragment extends BaseFragment implements OnClickListener,OnPageChangeListener{

	private View view;
	private ViewPager mVp;
	private ImageView mTabImg;
	private TextView mTv1,mTv2,mTv3,mTv4,mTv5,mTv6;
	private List<BaseSeeFragment>fragmentList = new ArrayList<BaseSeeFragment>();
	private SeeAdapter adapter;

	@Override
	public View initView() {
		view = View.inflate(getActivity(), R.layout.fragment_see, null);
		view.setLayoutParams(
				new RelativeLayout.LayoutParams
				(RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT));
		mVp = (ViewPager) view.findViewById(R.id.SeeFragment_VP);
		mTabImg = (ImageView) view.findViewById(R.id.SeeFragment_TabMenuImg);
		mTv1 = (TextView) view.findViewById(R.id.SeeFragment_Tv1);
		mTv2 = (TextView) view.findViewById(R.id.SeeFragment_Tv2);
		mTv3 = (TextView) view.findViewById(R.id.SeeFragment_Tv3);
		mTv4 = (TextView) view.findViewById(R.id.SeeFragment_Tv4);
		mTv5 = (TextView) view.findViewById(R.id.SeeFragment_Tv5);
		mTv6 = (TextView) view.findViewById(R.id.SeeFragment_Tv6);
		mVp.setOnPageChangeListener(this);
		return view;
	}

	@Override
	protected void initList() {
		super.initList();
		fragmentList.add(new See1Fragment());
		fragmentList.add(new See2Fragment());
		fragmentList.add(new See3Fragment());
		fragmentList.add(new See4Fragment());
		fragmentList.add(new See5Fragment());
		fragmentList.add(new See6Fragment());
		adapter = new SeeAdapter(getFragmentManager(), fragmentList);
		mVp.setAdapter(adapter);
	}

	@Override
	protected void setClick() {
		super.setClick();
		mTv1.setOnClickListener(this);
		mTv2.setOnClickListener(this);
		mTv3.setOnClickListener(this);
		mTv4.setOnClickListener(this);
		mTv5.setOnClickListener(this);
		mTv6.setOnClickListener(this);
		mTabImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if(R.id.SeeFragment_TabMenuImg == v.getId()){
			//点击抽屉imageview监听->向FrameActivity发送广播切换抽屉效果
			Intent intent = new Intent();
			intent.setAction(ActionModel.FrameUiAction);
			getActivity().sendBroadcast(intent);
		}else{
			clearTvColor();
			switch(v.getId()){
			case R.id.SeeFragment_Tv1:
				mTv1.setTextColor(Color.BLACK);
				mVp.setCurrentItem(0);
				break;
			case R.id.SeeFragment_Tv2:
				mTv2.setTextColor(Color.BLACK);
				mVp.setCurrentItem(1);
				break;
			case R.id.SeeFragment_Tv3:
				mTv3.setTextColor(Color.BLACK);
				mVp.setCurrentItem(2);
				break;
			case R.id.SeeFragment_Tv4:
				mTv4.setTextColor(Color.BLACK);
				mVp.setCurrentItem(3);
				break;
			case R.id.SeeFragment_Tv5:
				mTv5.setTextColor(Color.BLACK);
				mVp.setCurrentItem(4);
				break;
			case R.id.SeeFragment_Tv6:
				mTv6.setTextColor(Color.BLACK);
				mVp.setCurrentItem(5);
				break;
			}
		}

	}

	//清理顶部导航textview文字颜色方法
	private void clearTvColor(){
		mTv1.setTextColor(Color.GRAY);
		mTv2.setTextColor(Color.GRAY);
		mTv3.setTextColor(Color.GRAY);
		mTv4.setTextColor(Color.GRAY);
		mTv5.setTextColor(Color.GRAY);
		mTv6.setTextColor(Color.GRAY);
	}

	//从frameactivity->切换seefragment下viewpager方法
	public void selectFragment(final int index){
		new Thread(){
			boolean flag = true;
			public void run(){
				while(flag){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(mVp!=null){
						flag = false;
						hand.sendEmptyMessage(index);		
					}

				}
			}

		}.start();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		clearTvColor();
		switch(arg0){
		case 0:
			mTv1.setTextColor(Color.BLACK);
			break;
		case 1:
			mTv2.setTextColor(Color.BLACK);
			break;
		case 2:
			mTv3.setTextColor(Color.BLACK);
			break;
		case 3:
			mTv4.setTextColor(Color.BLACK);
			break;
		case 4:
			mTv5.setTextColor(Color.BLACK);
			break;
		case 5:
			mTv6.setTextColor(Color.BLACK);
			break;
		}
	}

	//切换fragment
	Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			//切换fragment
			mVp.setCurrentItem(msg.what);
		};
	};

}
