package com.sxt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 确认订单中使用scrollview
 * */
public class OrderScrollView extends ScrollView{

	private ExpandableListView mExpand;
	private ListView mLv;
	private LinearLayout mTopLinear;
	private LinearLayout mBotemLinear;
	private LinearLayout mCenter;
	private LinearLayout mLinear;
	//expand开启时条目索引
	private int possion = -1;

	public OrderScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView(){
		mLinear = new LinearLayout(getContext());
		mLinear.setLayoutParams
		(new FrameLayout.LayoutParams
				(FrameLayout.LayoutParams.MATCH_PARENT, 
						FrameLayout.LayoutParams.WRAP_CONTENT));
		mLinear.setOrientation(LinearLayout.VERTICAL);
		addView(mLinear);
		mTopLinear = new LinearLayout(getContext());
		mTopLinear.setLayoutParams
		(new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		mTopLinear.setOrientation(LinearLayout.VERTICAL);
		mCenter = new LinearLayout(getContext());
		mCenter.setLayoutParams
		(new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		mCenter.setOrientation(LinearLayout.VERTICAL);
		mBotemLinear = new LinearLayout(getContext());
		mBotemLinear.setLayoutParams
		(new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		mBotemLinear.setOrientation(LinearLayout.VERTICAL);
		mLinear.addView(mTopLinear);
		mLinear.addView(mCenter);
		mLinear.addView(mBotemLinear);
	}

	//设置top
	public void setTopView(View v){
		mTopLinear.addView(v);
	}
	//设置bottem
	public void setBottemView(View v){
		mBotemLinear.addView(v);
	}

	//添加expandablelistview
	public void setExpand(ExpandableListView mExpand){
		this.mExpand = mExpand;
		measureExpand();
		mCenter.addView(mExpand);
		requestLayout();
		//groupitem点击监听
		this.mExpand.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
					long arg3) {
				//区分当前点击开启或关闭
				if(possion != arg2){
					//开启操作
					arg0.expandGroup(arg2);
					possion = arg2;
				}else{
					//关闭操作
					arg0.collapseGroup(arg2);
					possion = -1;
				}
				//计算expandablelistview高度
				expandMeasure();
				return true;
			}
		});
		//groupitem开关时监听
		this.mExpand.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int arg0) {
				//arg0->展开条目索引
				//关闭除了当前打开的expandablelistview 
				ExpandableListAdapter adapter = OrderScrollView.this.mExpand.getExpandableListAdapter();
				for(int i = 0;i < adapter.getGroupCount();i++){
					if(i!= arg0){
						//关闭其他条目
						OrderScrollView.this.mExpand.collapseGroup(i);
					}
				}
			}
		});
	}

	//在expandablelistview开启时计算
	//所有grouview高度以及开启的group下的childview的高度
	private void expandMeasure(){
		int height = 0;
		ExpandableListAdapter adapter = mExpand.getExpandableListAdapter();
		for(int i = 0;i < adapter.getGroupCount();i ++){
			View groupView = adapter.getGroupView(i, false, null,null);
			groupView.measure(0, 0);
			height+=groupView.getMeasuredHeight();
			height+=mExpand.getDividerHeight();
			//当条groupview处于开启状态->获取其childview->测量高度
			if(i == possion){
				for(int j = 0;j < adapter.getChildrenCount(possion);j ++){
					View childView = adapter.getChildView(i, j,
							false, null, null);
					childView.measure(0,0);
					height+=childView.getMeasuredHeight();
					height+=mExpand.getDividerHeight();
				}
			}
		}
		height = height + mExpand.getPaddingBottom() + mExpand.getPaddingTop();
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mExpand.getLayoutParams();
		lp.height = height;
		mExpand.setLayoutParams(lp);
		requestLayout();
	}

	//添加listview
	public void setLv(ListView lv){
		this.mLv = lv;
		measureLv();
		mCenter.addView(mLv);
		requestLayout();
	}
	//计算ExpandableListView
	private void measureExpand(){
		int height = 0;
		//		ListAdapter a = mExpand.getAdapter();
		ExpandableListAdapter adapter = (ExpandableListAdapter) mExpand.getExpandableListAdapter();
		for(int i = 0;i < adapter.getGroupCount();i ++){
			View groupView = adapter.getGroupView(i,false, null, null);
			groupView.measure(0, 0);
			height += groupView.getMeasuredHeight();
		}
		height=height+mExpand.getPaddingTop()+mExpand.getPaddingBottom()
				+mExpand.getDividerHeight()*adapter.getGroupCount();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				height);
		lp.leftMargin = 20;
		mExpand.setLayoutParams(lp);
	}
	//计算ListView
	private void measureLv(){
		ListAdapter adapter = mLv.getAdapter();
		int height = 0;
		for(int i = 0;i < adapter.getCount();i ++){
			View itemView = adapter.getView(i, null, null);
			itemView.measure(0,0);
			height+=itemView.getMeasuredHeight();
		}
		height = height + mLv.getPaddingBottom()+
				mLv.getPaddingTop()+mLv.getDividerHeight()*adapter.getCount();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
				(LinearLayout.LayoutParams.MATCH_PARENT, height);
		lp.leftMargin = 20;
		mLv.setLayoutParams(lp);
	}

}
