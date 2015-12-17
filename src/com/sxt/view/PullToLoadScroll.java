package com.sxt.view;

import com.sxt.activity.R;
import com.sxt.callback.PullToLoadScrollLissener;
import com.sxt.model.ResModel;
import com.sxt.model.WindowModel;
import com.sxt.utils.animation.FrameUtils;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * �������������Լ�����ˢ��scrollview
 * */
public class PullToLoadScroll extends ScrollView{

	private View HeadView;
	private View FootView;
	private LinearLayout CenterView;
	private LinearLayout layout;//scrollview Ĭ�� layout
	private Context ctx;
	private int height = 0;//headerview�Լ�footview->�ͷ�ˢ���ٽ�ֵ
	//ͷ���Լ��ײ���ʾ����imageview
	private ImageView HeadImg;
	private ImageView FootImg;
	//ͷ���Լ��ײ�ImageViewʹ�õĶ���
	private FrameUtils headUtils;
	private FrameUtils footUtils;
	//�����Լ���������״̬
	private int state_mode = 0;//��ǰ״̬
	private static final int DONE = 0;//���״̬
	private static final int PULLTOREFRASH = 1;//����ˢ��̬
	private static final int RELASEREFRASH = 2;//�ͷ�����ˢ��
	private static final int REFRASHING = 3;//����ˢ��
	private static final int UPTOLOAD= 4;//��������
	private static final int RELASELOAD = 5;//�ͷż���
	private static final int LOADING = 6;//���ڼ���
	//��ָ���µ�
	private PointF startPF = new PointF();
	private PullToLoadScrollLissener call;

	public PullToLoadScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		initView();
		headUtils = new FrameUtils();
		headUtils.create(this.ctx, ResModel.FRAMEDRAWABLE, HeadImg);
		footUtils = new FrameUtils();
		footUtils.create(this.ctx, ResModel.FRAMEDRAWABLE, FootImg);
	}

	//���������Լ���������
	public void setCall(PullToLoadScrollLissener call){
		this.call = call;
	}

	private void initView(){
		layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new FrameLayout.LayoutParams
				(WindowModel.WIDTH,
						FrameLayout.LayoutParams.WRAP_CONTENT));
		addView(layout);
		HeadView = View.inflate(ctx, R.layout.view_headandfoot, null);
		FootView = View.inflate(ctx, R.layout.view_headandfoot, null);
		CenterView = new LinearLayout(ctx);
		CenterView.setOrientation(LinearLayout.VERTICAL);
		CenterView.setLayoutParams(new LinearLayout.LayoutParams
				(WindowModel.WIDTH,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		HeadImg = (ImageView) HeadView.findViewById(R.id.HeadFootImg);
		FootImg = (ImageView) FootView.findViewById(R.id.HeadFootImg);
		measureHead();
		HeadView.setPadding(0, -height, 0, 0);
		FootView.setPadding(0, 0, 0, -height);
		layout.addView(HeadView);
		layout.addView(CenterView);
		layout.addView(FootView);
	}

	//����headview�߶�
	private void measureHead(){
		HeadView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		height = HeadView.getMeasuredHeight();
	}

	//���ݵ�ǰ״̬�ı�iamgeview�ж�������
	private void selectMode(){
		switch(state_mode){
		case DONE:
			//ֹͣͷ���Լ��ײ�imageview����
			headUtils.stopFrame();
			footUtils.stopFrame();
			HeadView.setPadding(0, -height, 0, 0);
			FootView.setPadding(0, 0, 0, -height);
			break;
		case REFRASHING:
			HeadView.setPadding(0, 0, 0, 0);
			headUtils.startFrame();
			call.RefrashCall();
			break;
		case LOADING:
			footUtils.startFrame();
			FootView.setPadding(0, 0, 0, 0);
			call.LoadCall();
			break;
		}
	}

	//touch�¼�����
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			startPF.y = ev.getY();
		}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
			PointF nowPF = new PointF();
			nowPF.y = ev.getY();
			float dis = nowPF.y - startPF.y;//��������
			if(getScrollY() == 0 && dis > 0){
				//scrollview ���ڶ��� ->����ˢ�¼���
				HeadView.setPadding(0, (int)(dis/3-height), 0, 0);
				if((dis/3-height)>0)
					state_mode = RELASEREFRASH;
				else
					state_mode = PULLTOREFRASH;
				return true;
			}else if(((getScrollY() + getHeight()) >= computeVerticalScrollRange()) 
					&& dis < 0){
				//scrollview ���ڵײ� ->�������ؼ���
				//computeVerticalScrollRange()->����scrollview�߶�
				FootView.setPadding(0, 0, 0, (int)(Math.abs(dis)/3-height));
				if((Math.abs(dis)/3-height)>0){
					state_mode = RELASELOAD;
				}else{
					state_mode = UPTOLOAD;
				}
				return true;
			}
		}else if(ev.getAction() == MotionEvent.ACTION_UP){
			if(RELASEREFRASH == state_mode){
				state_mode = REFRASHING;
				selectMode();
			}
			else if(RELASELOAD == state_mode){
				state_mode = LOADING;
				selectMode();
			}else{
				state_mode = DONE;
				selectMode();
			}

		}
		return super.dispatchTouchEvent(ev);
	}

	//����scrollview����ʾ���ݷ���
	public void setContentView(View childView){
		CenterView.addView(childView);
	}

	//�ر������Լ���������
	public void commplate(){
		state_mode = DONE;
		selectMode();
	}

	//����������listview�ĸ߶�
	public void measureLv(ListView lv,ListAdapter adapter){
		int height = 0;
		for(int i = 0;i < adapter.getCount();i ++){
			View itemView = adapter.getView(i, null, null);
			itemView.measure
			(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			height += itemView.getMeasuredHeight();
		}
		height = height + 
				lv.getDividerHeight()*(adapter.getCount()-1) 
				+ lv.getPaddingTop() + lv.getPaddingBottom();
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) lv.getLayoutParams();
		lp.height = height;
		lv.setLayoutParams(lp);
		requestLayout();
	}

}
