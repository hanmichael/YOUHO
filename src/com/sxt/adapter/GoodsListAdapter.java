package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.GoodsInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsListAdapter extends BaseAdapter{

	private List<GoodsInfo>list;
	private Context ctx;
	private ImageLoad load;

	public GoodsListAdapter(List<GoodsInfo>list,Context ctx){
		this.list = list;
		this.ctx = ctx;
		load = new ImageLoad();
	}

	@Override
	public int getCount() {
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
			convertView = View.inflate(ctx, R.layout.item_goodslist, null);
			hold.mImg = (ImageView) convertView.findViewById(R.id.GoodsList_Img);
			hold.mDis = (TextView) convertView.findViewById(R.id.GoodsList_DisTv);
			hold.mPrice = (TextView) convertView.findViewById(R.id.GoodsList_PriceTv);
			hold.mTitle = (TextView) convertView.findViewById(R.id.GoodsList_TitleTv);
			convertView.setTag(hold);
		}else {
			hold = (ViewHolder) convertView.getTag();
		}
		load.LoadImg(list.get(position).goodsimg, hold.mImg);
		hold.mTitle.setText(list.get(position).title);
		if(list.get(position).distance.equals(list.get(position).price)){
			hold.mPrice.setVisibility(View.GONE);
		}else {
			hold.mPrice.setVisibility(View.VISIBLE);
			hold.mPrice.setText("гд"+list.get(position).price);
		}
		hold.mDis.setText("гд"+list.get(position).distance);
		return convertView;
	}

	private class ViewHolder{
		TextView mTitle;
		TextView mPrice;
		TextView mDis;
		ImageView mImg;
	}

}
