package com.sxt.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 品牌下自定义scrollview 
 * */
public class BrandScrollUi extends ScrollView{

	private LinearLayout Linear;//Scrollview中嵌套linearLayout
	private ExpandableListView expand;//嵌套的ExpandableListView
	private int ExpandHeight = 0;//ExpandableListView高度
	private int TopHeight = 0;//ExpandableListView向上所有空间高度
	private List<Integer>expandGroupList = new ArrayList<Integer>();//expand中groupview在y位置list

	public BrandScrollUi(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView(){
		Linear = new LinearLayout(getContext());
		Linear.setOrientation(LinearLayout.VERTICAL);
		Linear.setLayoutParams
		(new FrameLayout.LayoutParams
				(FrameLayout.LayoutParams.MATCH_PARENT, 
						FrameLayout.LayoutParams.WRAP_CONTENT));
		addView(Linear);
	}

	//设置自定义scrollview要显示的view方法
	public void setShowView(View view){
		Linear.addView(view);
	}

	//测量expand高度->设置给expand
	public void measureExpandableListView(ExpandableListView el){
		this.expand = el;
		ExpandableListAdapter adapter = expand.getExpandableListAdapter();
		//循环获取groupview高度
		for(int i = 0;i < adapter.getGroupCount();i++){
			//在计算expandablelistview高度时计算groupview在Y轴位置
			int itemHeight = TopHeight;
			itemHeight += ExpandHeight;
			expandGroupList.add(itemHeight);
			View GroupView= adapter.getGroupView(i, true, null, null);
			GroupView.measure(0, 0);
			ExpandHeight += GroupView.getMeasuredHeight();
			ExpandHeight += expand.getDividerHeight();
			//循环获取childview高度
			for(int j = 0;j < adapter.getChildrenCount(i);j ++){
				View ChildView = adapter.getChildView(i, j, true, null, null);
				ChildView.measure(0, 0);
				ExpandHeight+=ChildView.getMeasuredHeight();
				ExpandHeight += expand.getDividerHeight();
			}

		}
		ExpandHeight = ExpandHeight + expand.getPaddingBottom() + expand.getPaddingTop();
		//设置给expandablelistview
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) expand.getLayoutParams();
		lp.height = ExpandHeight;
		expand.setLayoutParams(lp);
		requestLayout();
	}

	//设置topheight
	public void setTopHeight(int search,int adverset,int tv,int hot){
		TopHeight = search + adverset + tv + hot;
	}

	//根据字母索引滑动scrollview 
	public void setLetterIndex(int index){
		if(index >= 0&& index< expandGroupList.size()){
			scrollTo(0, expandGroupList.get(index));
		}
	}

}
