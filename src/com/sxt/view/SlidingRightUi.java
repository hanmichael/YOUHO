package com.sxt.view;

import com.sxt.model.WindowModel;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 右侧抽屉效果
 * */
public class SlidingRightUi extends FrameLayout{

	private LinearLayout mBottemView;
	private LinearLayout mTopView;
	private int maxWidth = WindowModel.WIDTH/5*4;//抽出最大宽度
	private boolean TouchFlag = false;//抽屉开启状态
	private PointF startPF = new PointF();
	private int TopHeight = 0;
	private boolean VPFlag = false;//viewpager滑动到最后一页标志

	public SlidingRightUi(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView(){

		mTopView = new LinearLayout(getContext());
		mTopView.setOrientation(LinearLayout.VERTICAL);
		mTopView.setLayoutParams
		(new FrameLayout.LayoutParams
				(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
		mBottemView = new LinearLayout(getContext());
		mBottemView.setOrientation(LinearLayout.VERTICAL);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
				(maxWidth,
						FrameLayout.LayoutParams.MATCH_PARENT);
		lp.leftMargin = WindowModel.WIDTH/5;
		mBottemView.setLayoutParams(lp);
		addView(mBottemView);
		addView(mTopView);
	}

	//设置topview方法
	public void setTopView(View view){
		view.setLayoutParams
		(new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		mTopView.addView(view);
	}

	//设置bottemview方法
	public void setBottemView(View view){
		view.setLayoutParams
		(new LinearLayout.LayoutParams
				(maxWidth,
						LinearLayout.LayoutParams.MATCH_PARENT));
		mBottemView.addView(view);
	}

	//开启抽屉
	public void startSliding(){
		FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
		lp.rightMargin = maxWidth;
		lp.leftMargin = -maxWidth;
		mTopView.setLayoutParams(lp);
		requestLayout();
		TouchFlag = true;
	}

	//关闭抽屉
	public void stopSliding(){
		FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
		lp.rightMargin = 0;
		lp.leftMargin = 0;
		mTopView.setLayoutParams(lp);
		requestLayout();
		TouchFlag = false;
		VPFlag = false;
	}

	//抽屉状态
	public boolean IsSliding(){
		return TouchFlag;
	}

	//设置顶部不处于touch拦截高度
	public void setTopHeight(int TopHeight){
		this.TopHeight = TopHeight;
	}

	//设置viewpager滑动锁
	public void setVPFlag(boolean VPFlag){
		this.VPFlag = VPFlag;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			//获取起点
			startPF.x = ev.getX();
			startPF.y = ev.getY();
			//touch下发处理
			if(startPF.y<TopHeight){
				return super.dispatchTouchEvent(ev);
			}
		}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
			//当前手指所在的点
			PointF nowPF = new PointF();
			nowPF.x = ev.getX();
			nowPF.y = ev.getY();
			//区分收拾在屏幕中是垂直滑动或水平滑动
			if((Math.abs((nowPF.x - startPF.x)/2)
					-Math.abs((nowPF.y-startPF.y)))<10){
				if(TouchFlag){
					//在菜单开启时垂直滑动->拦截touch事件
					return true;
				}else{
					return super.dispatchTouchEvent(ev);
				}
			}else{
				//水平滑动->区分滑动方向
				float dis = nowPF.x - startPF.x;//滑动距离
				if(dis<0 && VPFlag && !TouchFlag){
					//从右向左
					FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
					lp.rightMargin = (int)Math.abs(dis);
					lp.leftMargin = -lp.rightMargin;
					//最大滑动距离判断
					if(lp.rightMargin > maxWidth){
						lp.rightMargin = maxWidth;
						lp.leftMargin = -maxWidth;
						//						TouchFlag = true;
					}
					mTopView.setLayoutParams(lp);
					requestLayout();
					return true;
				}else if(dis > 0 && TouchFlag){
					//从左向右
					FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
					lp.rightMargin = (int) (maxWidth-Math.abs(dis));
					lp.leftMargin = -lp.rightMargin;
					if(lp.rightMargin<0){
						lp.rightMargin = 0;
						lp.leftMargin = 0;
						//						TouchFlag = false;
					}
					mTopView.setLayoutParams(lp);
					requestLayout();
					return true;
				}else {
					return super.dispatchTouchEvent(ev);
				}
			}
		}else if(ev.getAction() == MotionEvent.ACTION_UP){
			if(!VPFlag){
				return super.dispatchTouchEvent(ev);
			}
			if(startPF.y<TopHeight){
				return super.dispatchTouchEvent(ev);
			}
			float dis = Math.abs(ev.getX() - startPF.x);
			FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
			//先判断滑动标志开关状态->根据滑动距离判断是否开关
			if(TouchFlag){
				if(dis > maxWidth/2){
					lp.rightMargin = 0;
					lp.leftMargin = 0;
					mTopView.setLayoutParams(lp);
					TouchFlag = false;
					VPFlag = false;
					return true;
				}else{
					lp.rightMargin = maxWidth;
					lp.leftMargin = -maxWidth;
					mTopView.setLayoutParams(lp);
					TouchFlag = true;
					return true;
				}
			}else{
				if(dis > maxWidth/2){
					lp.rightMargin = maxWidth;
					lp.leftMargin = -maxWidth;
					mTopView.setLayoutParams(lp);
					TouchFlag = true;
					return true;
				}else{
					lp.rightMargin = 0;
					lp.leftMargin = 0;
					mTopView.setLayoutParams(lp);
					TouchFlag = false;
					VPFlag = false;
					return true;
				}
			}
		}
		return super.dispatchTouchEvent(ev);
	}

}
