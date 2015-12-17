package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.NewsInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * π‰¡–±Ì  ≈‰∆˜
 * */
public class SeeListAdapter extends BaseAdapter{

	private List<NewsInfo>list;
	private Context ctx;
	private ImageLoad load;

	public SeeListAdapter(List<NewsInfo>list,Context ctx){
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
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hold;
		if(convertView == null){
			hold = new ViewHolder();
			convertView = View.inflate(ctx, R.layout.item_seefragment,null);
			hold.bigImg = (ImageView) convertView.findViewById(R.id.SeeItem_Img);
			hold.LikeImg = (ImageView) convertView.findViewById(R.id.SeeItem_LikeImg);
			hold.ShareImg = (ImageView) convertView.findViewById(R.id.SeeItem_ShareImg);
			hold.titleTv = (TextView) convertView.findViewById(R.id.SeeItem_TitleTv);
			hold.valueTv = (TextView) convertView.findViewById(R.id.SeeItem_ValueTv);
			hold.timeTv = (TextView) convertView.findViewById(R.id.SeeItem_TimeTv);
			convertView.setTag(hold);
		}else{
			hold = (ViewHolder) convertView.getTag();
		}
		hold.titleTv.setText(list.get(position).title);
		hold.valueTv.setText(list.get(position).value);
		load.LoadImg(list.get(position).imgpath, hold.bigImg);
		return convertView;
	}

	private class ViewHolder{

		ImageView bigImg;
		ImageView LikeImg;
		ImageView ShareImg;
		TextView titleTv;
		TextView valueTv;
		TextView timeTv;
	}

}
