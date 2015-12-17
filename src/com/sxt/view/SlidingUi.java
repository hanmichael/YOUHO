package com.sxt.view;

import com.sxt.model.WindowModel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SlidingUi extends FrameLayout{

	private LinearLayout mTopLinear;//顶部布局
	private LinearLayout mBottemLinear;//底部布局
	private int max_width = WindowModel.WIDTH/5*4;//bottemLinear最大显示宽度
	private PointF startPF = new PointF();//记录手指按下点
	private boolean Flag = false;//抽屉开启标志锁false:关闭;true:开启
	private Context ctx;
	private View topView;

	public SlidingUi(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		initView();
	}

	private void initView(){
		Log.e("", "max_width:"+max_width);
		mTopLinear = new LinearLayout(ctx);
		mBottemLinear = new LinearLayout(ctx);
		//指定方向
		mTopLinear.setOrientation(LinearLayout.VERTICAL);
		mBottemLinear.setOrientation(LinearLayout.VERTICAL);
		//设置java创建top以及bottem对应宽高位置
		//LayoutParams->告诉父容器子控件想获取的宽高
		//子控件申请位置使用LayoutParams要与父容器同意
		mTopLinear.setLayoutParams(
				new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
		mBottemLinear.setLayoutParams(
				new FrameLayout.LayoutParams(max_width, 
						FrameLayout.LayoutParams.MATCH_PARENT));
		mTopLinear.setBackgroundColor(Color.WHITE);
		mBottemLinear.setBackgroundColor(Color.GREEN);
		//按照先后顺序添加给framelayout
		addView(mBottemLinear);
		addView(mTopLinear);
	}

	//获取当前抽屉开启状态方法
	public boolean IsSliding(){
		return Flag;
	}

	//开启抽屉方法
	public void startSliding(){
		FrameLayout.LayoutParams lp = 
				(android.widget.FrameLayout.LayoutParams) mTopLinear.getLayoutParams();
		lp.leftMargin = max_width;
		lp.rightMargin = -max_width;
		mTopLinear.setLayoutParams(lp);
		Flag = true;
	}
	//关闭抽屉方法
	public void stopSliding(){
		FrameLayout.LayoutParams lp = 
				(android.widget.FrameLayout.LayoutParams) mTopLinear.getLayoutParams();
		lp.leftMargin = 0;
		lp.rightMargin = 0;
		mTopLinear.setLayoutParams(lp);
		Flag = false;
	}

	//设置top显示view
	//view->mTop中显示的view
	public void setTop(View view){
		view.setLayoutParams(
				new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		topView = view;
		mTopLinear.addView(view);
	}
	//设置bottem显示view
	public void setBottem(View view){
		view.setLayoutParams(new LinearLayout.LayoutParams(max_width,
				LinearLayout.LayoutParams.MATCH_PARENT));
		mBottemLinear.addView(view);
	}

	public void getWH(){
		Log.e("","mTopLinear:"+mTopLinear.getWidth()+"&&&"+mTopLinear.getHeight());
		Log.e("","mTopView:"+topView.getWidth()+"&&&"+topView.getHeight());
	}

	//根据touch对toplinear响应处理
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			startPF.x = ev.getX();
			startPF.y = ev.getY();
			if(startPF.x>max_width && Flag && startPF.y >100){
				//当前处理
				return true;
			}else if(startPF.y < 100){
				return super.dispatchTouchEvent(ev);
			}
		}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
			//当前点获取
			PointF nowPF = new PointF();
			nowPF.x = ev.getX();
			nowPF.y = ev.getY();
			//X滑动距离
			float dis_X = nowPF.x - startPF.x;
			float dis_Y = nowPF.y - startPF.y;
			//Y滑动距离
			//区分上下滑动以及左右滑动
			if((Math.abs(dis_X)/2-Math.abs(dis_Y))<10){
				//垂直->开启状态touch不分发->listview无法滑动
				// 关闭状态分发
				if(!Flag)
					return super.dispatchTouchEvent(ev);
				else 
					return true;
			}else{
				//水平滑动
				//区分左右->滑动距离正负区分
				FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) mTopLinear.getLayoutParams();
				if(dis_X>=0&&!Flag){
					return super.dispatchTouchEvent(ev);
				}else if(dis_X<0 && Flag){
					//从右向左滑动->marginleft递减->marginRight 递增
					Log.e("", "nowPF.y:"+nowPF.y);
					lp.leftMargin = (int) (lp.leftMargin+dis_X);
					lp.rightMargin = -lp.leftMargin;
					if(lp.leftMargin<=0){
						lp.leftMargin = 0;
						lp.rightMargin = 0;
					}
					mTopLinear.setLayoutParams(lp);
					return true;
				}

			}
		}else if(ev.getAction() == MotionEvent.ACTION_UP){
			//判断x轴移动距离->大于最大边界一半->处于打开状态
			//->反之处于关闭状态
			float x = ev.getX();
			FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) mTopLinear.getLayoutParams();
			if(Flag){
				//开启时->从右向左->滑动距离大于一半关闭 反之 开启
				if((x - startPF.x)>0){
					return super.dispatchTouchEvent(ev);
				}
				if(Math.abs(x - startPF.x)>max_width/2){
					//关闭
					lp.leftMargin = 0;
					lp.rightMargin = 0;
					mTopLinear.setLayoutParams(lp);
					Flag = false;
					return super.dispatchTouchEvent(ev);
				}else{
					//开启
					lp.leftMargin = max_width;
					lp.rightMargin = -max_width;
					mTopLinear.setLayoutParams(lp);
					Flag = true;
					return super.dispatchTouchEvent(ev);
				}
			}
			//			else{
			//				//关闭状态->从左向右->滑动距离小于一般时关闭；反之大于一般开启
			//				if((x - startPF.x)<0){
			//					return super.dispatchTouchEvent(ev);
			//				}
			//				if(Math.abs(x - startPF.x)<max_width/2){
			//					lp.leftMargin = 0;
			//					lp.rightMargin = 0;
			//					mTopLinear.setLayoutParams(lp);
			//					Flag = false;
			//					return super.dispatchTouchEvent(ev);
			//				}else{
			//					lp.leftMargin = max_width;
			//					lp.rightMargin = -max_width;
			//					Flag = true;
			//					mTopLinear.setLayoutParams(lp);
			//					return super.dispatchTouchEvent(ev);
			//				}
			//			}
		}
		return super.dispatchTouchEvent(ev);
	}
}
