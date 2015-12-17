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
 * 具有上啦加载以及下拉刷新scrollview
 * */
public class PullToLoadScroll extends ScrollView{

	private View HeadView;
	private View FootView;
	private LinearLayout CenterView;
	private LinearLayout layout;//scrollview 默认 layout
	private Context ctx;
	private int height = 0;//headerview以及footview->释放刷新临界值
	//头部以及底部显示动画imageview
	private ImageView HeadImg;
	private ImageView FootImg;
	//头部以及底部ImageView使用的动画
	private FrameUtils headUtils;
	private FrameUtils footUtils;
	//下拉以及上啦各种状态
	private int state_mode = 0;//当前状态
	private static final int DONE = 0;//完成状态
	private static final int PULLTOREFRASH = 1;//下拉刷新态
	private static final int RELASEREFRASH = 2;//释放立即刷新
	private static final int REFRASHING = 3;//正在刷新
	private static final int UPTOLOAD= 4;//上啦加载
	private static final int RELASELOAD = 5;//释放加载
	private static final int LOADING = 6;//正在加载
	//手指按下点
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

	//设置下拉以及上啦监听
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

	//测量headview高度
	private void measureHead(){
		HeadView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		height = HeadView.getMeasuredHeight();
	}

	//根据当前状态改变iamgeview中动画开关
	private void selectMode(){
		switch(state_mode){
		case DONE:
			//停止头部以及底部imageview动画
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

	//touch事件处理
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			startPF.y = ev.getY();
		}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
			PointF nowPF = new PointF();
			nowPF.y = ev.getY();
			float dis = nowPF.y - startPF.y;//滑动距离
			if(getScrollY() == 0 && dis > 0){
				//scrollview 处于顶部 ->下拉刷新激活
				HeadView.setPadding(0, (int)(dis/3-height), 0, 0);
				if((dis/3-height)>0)
					state_mode = RELASEREFRASH;
				else
					state_mode = PULLTOREFRASH;
				return true;
			}else if(((getScrollY() + getHeight()) >= computeVerticalScrollRange()) 
					&& dis < 0){
				//scrollview 处于底部 ->上啦加载激活
				//computeVerticalScrollRange()->返回scrollview高度
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

	//设置scrollview中显示内容方法
	public void setContentView(View childView){
		CenterView.addView(childView);
	}

	//关闭下拉以及上啦方法
	public void commplate(){
		state_mode = DONE;
		selectMode();
	}

	//测量包含的listview的高度
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
