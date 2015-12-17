package com.sxt.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

public class GoodsScrollView extends ScrollView {

	private LinearLayout linear;
	private LinearLayout UpLinear;
	private LinearLayout DownLinear;
	private int height = 0;
	private int index = 0;//当前页标志索引->0显示topview;1显示bottemview
	private boolean topFlag = false;//topview是否滑动到底部标志
	private boolean bottemFlag = false;//bottemView 是否滑动顶部标志
	int y = 0;
	public GoodsScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView(){
		//编写scrollview中嵌套linearlayout
		linear = new LinearLayout(getContext());
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.setLayoutParams
		(new FrameLayout.LayoutParams
				(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.WRAP_CONTENT));
		addView(linear);
		//根据上下关系初始化up以及down
		UpLinear = new LinearLayout(getContext());
		UpLinear.setOrientation(LinearLayout.VERTICAL);
		UpLinear.setLayoutParams
		(new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		linear.addView(UpLinear);
		DownLinear = new LinearLayout(getContext());
		DownLinear.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		//		lp.topMargin = 10;
		DownLinear.setLayoutParams
		(lp);
		linear.addView(DownLinear);
	}

	//设置uplinear显示内容方法
	public void setUpView(View view){
		UpLinear.addView(view);
	}

	//设置donwlinear显示内容方法
	public void setDownView(ListView view){
		measureLv(view);
		DownLinear.addView(view);
	} 

	//由于scrollview嵌套listview测量listview高度
	private void measureLv(ListView view){
		int height = 0;
		ListAdapter adapter = view.getAdapter();
		if(adapter == null)
			return;
		for(int i = 0;i < adapter.getCount();i ++){
			View itemView = adapter.getView(i, null, null);
			itemView.measure(0, 0);
			height+=itemView.getMeasuredHeight();
		}
		height = height + view.getDividerHeight()*adapter.getCount() + 
				view.getPaddingBottom() + view.getPaddingTop();
		LinearLayout.LayoutParams lp = 
				new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT, height);
		view.setLayoutParams(lp);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if(ev.getAction() == MotionEvent.ACTION_DOWN){

			y = (int) ev.getY();
		}
		else if(ev.getAction() == MotionEvent.ACTION_MOVE){

			if(index == 0){
				//显示topview
				if((getScrollY()+getHeight())>=
						UpLinear.getMeasuredHeight()&&!topFlag){
					topFlag = true;
					return true;
				}
			}else{
				//显示bottemview 
				if(getScrollY() <= UpLinear.getMeasuredHeight()
						&&!bottemFlag){
					bottemFlag = true;
					return true;
				}
			}
			//			return super.dispatchTouchEvent(ev);
		}else if(ev.getAction() == MotionEvent.ACTION_UP){
			Log.e("","index:"+index+"&&topFlag:"
					+topFlag+"&&bottemFlag:"+bottemFlag);
			int nowY = (int) ev.getY();
			//自动滚动判断
			if(index == 0){
				//自动从当前位置滚动到UpLinear.getMeasuredHeight()
				if((getScrollY()+getHeight()) >= 
						(UpLinear.getMeasuredHeight() + 300)&&topFlag){
					scrollTo(0,UpLinear.getMeasuredHeight());
					index = 1;
				}else if((getScrollY()+getHeight())>
				UpLinear.getMeasuredHeight()
				&&(getScrollY()+getHeight()) < 
				(UpLinear.getMeasuredHeight() + 300)&&topFlag
						){
					//自动从当前位置回滚到->UpLinear.getMeasuredHeight()-getheight()
					scrollTo(0, (UpLinear.getMeasuredHeight()-getHeight()));
				}
			}else{
				if((getScrollY() + getHeight())<=(UpLinear.getMeasuredHeight()+300)&&bottemFlag){
					scrollTo(0, (UpLinear.getMeasuredHeight()-getHeight()));
					index = 0;
				}else if((getScrollY() + getHeight())>(UpLinear.getMeasuredHeight()+300)&&bottemFlag){
					scrollTo(0, UpLinear.getMeasuredHeight());
				}
			}
			topFlag = false;
			bottemFlag = false;
		}
		return super.dispatchTouchEvent(ev);
	}

	//scrollView滑动监听
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(index == 0 &&!topFlag&&t >= 
				(UpLinear.getMeasuredHeight()-getHeight()-100)
				&&(t-oldt)>0){
			Log.e("","UpLinear卡住了");
			scrollTo(0,UpLinear.getMeasuredHeight()-getHeight());
		}
		if(index == 1&&!bottemFlag&&
				t<=(UpLinear.getMeasuredHeight()+100)&&
				(t-oldt)<0){
			scrollTo(0,UpLinear.getMeasuredHeight());
			Log.e("","Linear卡住了");
		}
	}

	//设置直接进入商品详情(DownLinear)
	public void setScrollDown(){
		index = 1;
		topFlag = false;
		bottemFlag = false;
		scrollTo(0, UpLinear.getMeasuredHeight());
	}

}
