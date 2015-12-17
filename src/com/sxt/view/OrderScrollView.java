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
 * ȷ�϶�����ʹ��scrollview
 * */
public class OrderScrollView extends ScrollView{

	private ExpandableListView mExpand;
	private ListView mLv;
	private LinearLayout mTopLinear;
	private LinearLayout mBotemLinear;
	private LinearLayout mCenter;
	private LinearLayout mLinear;
	//expand����ʱ��Ŀ����
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

	//����top
	public void setTopView(View v){
		mTopLinear.addView(v);
	}
	//����bottem
	public void setBottemView(View v){
		mBotemLinear.addView(v);
	}

	//���expandablelistview
	public void setExpand(ExpandableListView mExpand){
		this.mExpand = mExpand;
		measureExpand();
		mCenter.addView(mExpand);
		requestLayout();
		//groupitem�������
		this.mExpand.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
					long arg3) {
				//���ֵ�ǰ���������ر�
				if(possion != arg2){
					//��������
					arg0.expandGroup(arg2);
					possion = arg2;
				}else{
					//�رղ���
					arg0.collapseGroup(arg2);
					possion = -1;
				}
				//����expandablelistview�߶�
				expandMeasure();
				return true;
			}
		});
		//groupitem����ʱ����
		this.mExpand.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int arg0) {
				//arg0->չ����Ŀ����
				//�رճ��˵�ǰ�򿪵�expandablelistview 
				ExpandableListAdapter adapter = OrderScrollView.this.mExpand.getExpandableListAdapter();
				for(int i = 0;i < adapter.getGroupCount();i++){
					if(i!= arg0){
						//�ر�������Ŀ
						OrderScrollView.this.mExpand.collapseGroup(i);
					}
				}
			}
		});
	}

	//��expandablelistview����ʱ����
	//����grouview�߶��Լ�������group�µ�childview�ĸ߶�
	private void expandMeasure(){
		int height = 0;
		ExpandableListAdapter adapter = mExpand.getExpandableListAdapter();
		for(int i = 0;i < adapter.getGroupCount();i ++){
			View groupView = adapter.getGroupView(i, false, null,null);
			groupView.measure(0, 0);
			height+=groupView.getMeasuredHeight();
			height+=mExpand.getDividerHeight();
			//����groupview���ڿ���״̬->��ȡ��childview->�����߶�
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

	//���listview
	public void setLv(ListView lv){
		this.mLv = lv;
		measureLv();
		mCenter.addView(mLv);
		requestLayout();
	}
	//����ExpandableListView
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
	//����ListView
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
