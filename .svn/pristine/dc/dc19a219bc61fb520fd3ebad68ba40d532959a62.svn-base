package com.sxt.adapter;

import java.util.List;
import com.sxt.activity.R;
import com.sxt.bean.AllBrandInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * ∆∑≈∆œ¬expandablelistview  ≈‰∆˜
 * */
public class BrandExpandAdapter extends BaseExpandableListAdapter{

	private List<AllBrandInfo>list;
	private Context ctx;

	public BrandExpandAdapter(List<AllBrandInfo>list,Context ctx){
		this.list = list;
		this.ctx = ctx;
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).list.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).list.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = View.inflate(ctx, R.layout.item_allbrandmum, null);
		TextView tv = (TextView) convertView.findViewById(R.id.AllBrandItem_MumTv);
		tv.setText(list.get(groupPosition).letter);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = View.inflate(ctx, R.layout.item_allbrandchild, null);
		TextView tv = (TextView) convertView.findViewById(R.id.AllBrandItem_ChildTv);
		TextView hotTv = (TextView) convertView.findViewById(R.id.AllBrandItem_ChildHotTv);
		if(list.get(groupPosition).list.get(childPosition).hotflag.equals("0")){
			hotTv.setVisibility(View.GONE);
		}else
			hotTv.setVisibility(View.VISIBLE);
		tv.setText(list.get(groupPosition).list.get(childPosition).name);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
