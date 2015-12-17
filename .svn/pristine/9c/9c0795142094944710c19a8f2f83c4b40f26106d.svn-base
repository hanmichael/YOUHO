package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.ClassesBottemInfo;
import com.sxt.bean.ClassesInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ClassesFragmentœ¬listviewπÿ¡™  ≈‰∆˜
 * */
public class ClassesAdapter extends BaseAdapter{

	private List<ClassesBottemInfo>list;
	private Context ctx;

	public ClassesAdapter(List<ClassesBottemInfo>list,Context ctx){
		this.list = list;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hold;
		if(convertView == null){
			hold = new ViewHolder();
			convertView = View.inflate(ctx, R.layout.item_classesbottem, null);
			hold.tv = (TextView) convertView.findViewById(R.id.ClassesItem_Tv);
			hold.img = (ImageView) convertView.findViewById(R.id.ClassesItem_Img);
			convertView.setTag(hold);
		}else{
			hold = (ViewHolder)convertView.getTag();
		}
		hold.tv.setText(list.get(position).title);
		if(list.get(position).flag){
			hold.img.setVisibility(View.VISIBLE);
		}else{
			hold.img.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class ViewHolder{
		TextView tv;
		ImageView img;
	}

}
