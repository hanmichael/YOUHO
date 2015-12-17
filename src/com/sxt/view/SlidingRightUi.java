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
 * �Ҳ����Ч��
 * */
public class SlidingRightUi extends FrameLayout{

	private LinearLayout mBottemView;
	private LinearLayout mTopView;
	private int maxWidth = WindowModel.WIDTH/5*4;//���������
	private boolean TouchFlag = false;//���뿪��״̬
	private PointF startPF = new PointF();
	private int TopHeight = 0;
	private boolean VPFlag = false;//viewpager���������һҳ��־

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

	//����topview����
	public void setTopView(View view){
		view.setLayoutParams
		(new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		mTopView.addView(view);
	}

	//����bottemview����
	public void setBottemView(View view){
		view.setLayoutParams
		(new LinearLayout.LayoutParams
				(maxWidth,
						LinearLayout.LayoutParams.MATCH_PARENT));
		mBottemView.addView(view);
	}

	//��������
	public void startSliding(){
		FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
		lp.rightMargin = maxWidth;
		lp.leftMargin = -maxWidth;
		mTopView.setLayoutParams(lp);
		requestLayout();
		TouchFlag = true;
	}

	//�رճ���
	public void stopSliding(){
		FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
		lp.rightMargin = 0;
		lp.leftMargin = 0;
		mTopView.setLayoutParams(lp);
		requestLayout();
		TouchFlag = false;
		VPFlag = false;
	}

	//����״̬
	public boolean IsSliding(){
		return TouchFlag;
	}

	//���ö���������touch���ظ߶�
	public void setTopHeight(int TopHeight){
		this.TopHeight = TopHeight;
	}

	//����viewpager������
	public void setVPFlag(boolean VPFlag){
		this.VPFlag = VPFlag;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			//��ȡ���
			startPF.x = ev.getX();
			startPF.y = ev.getY();
			//touch�·�����
			if(startPF.y<TopHeight){
				return super.dispatchTouchEvent(ev);
			}
		}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
			//��ǰ��ָ���ڵĵ�
			PointF nowPF = new PointF();
			nowPF.x = ev.getX();
			nowPF.y = ev.getY();
			//������ʰ����Ļ���Ǵ�ֱ������ˮƽ����
			if((Math.abs((nowPF.x - startPF.x)/2)
					-Math.abs((nowPF.y-startPF.y)))<10){
				if(TouchFlag){
					//�ڲ˵�����ʱ��ֱ����->����touch�¼�
					return true;
				}else{
					return super.dispatchTouchEvent(ev);
				}
			}else{
				//ˮƽ����->���ֻ�������
				float dis = nowPF.x - startPF.x;//��������
				if(dis<0 && VPFlag && !TouchFlag){
					//��������
					FrameLayout.LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
					lp.rightMargin = (int)Math.abs(dis);
					lp.leftMargin = -lp.rightMargin;
					//��󻬶������ж�
					if(lp.rightMargin > maxWidth){
						lp.rightMargin = maxWidth;
						lp.leftMargin = -maxWidth;
						//						TouchFlag = true;
					}
					mTopView.setLayoutParams(lp);
					requestLayout();
					return true;
				}else if(dis > 0 && TouchFlag){
					//��������
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
			//���жϻ�����־����״̬->���ݻ��������ж��Ƿ񿪹�
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