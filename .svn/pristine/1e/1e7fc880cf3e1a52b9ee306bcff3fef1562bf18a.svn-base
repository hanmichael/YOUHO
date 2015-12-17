package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.HomeInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 首页推荐品牌等相关适配器
 * */

public class HomeItemAdapter extends BaseAdapter{

	private List<HomeInfo>list;
	private Context ctx;
	private ImageLoad load;

	public HomeItemAdapter(List<HomeInfo>list,Context ctx){
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
			convertView = View.inflate(ctx, R.layout.item_homefragment, null);
			hold = new ViewHolder();
			hold.mTitleTv = (TextView) convertView.findViewById(R.id.HomeItem_Title);
			hold.mMoreImg = (ImageView) convertView.findViewById(R.id.HomeItem_MoreImg);
			hold.mBigImg = (ImageView) convertView.findViewById(R.id.HomeItem_Big);
			hold.mLeftImg = (ImageView) convertView.findViewById(R.id.HomeItem_LeftImg);
			hold.mLeftTopImg = (ImageView) convertView.findViewById(R.id.HomeItem_LeftTopImg);
			hold.mRightTopImg = (ImageView) convertView.findViewById(R.id.HomeItem_RightTopImg);
			hold.mLeftBottemImg = (ImageView) convertView.findViewById(R.id.HomeItem_LeftBottemImg);
			hold.mRightBottemImg = (ImageView) convertView.findViewById(R.id.HomeItem_RightBottemImg);
			convertView.setTag(hold);
		}else{
			hold = (ViewHolder) convertView.getTag();
		}
		hold.mTitleTv.setText(list.get(position).Title);
		load.LoadImg(list.get(position).brandList.get(0).imgpath, hold.mBigImg);
		load.LoadImg(list.get(position).brandList.get(1).imgpath, hold.mLeftImg);
		load.LoadImg(list.get(position).brandList.get(2).imgpath, hold.mLeftTopImg);
		load.LoadImg(list.get(position).brandList.get(3).imgpath, hold.mRightTopImg);
		load.LoadImg(list.get(position).brandList.get(4).imgpath, hold.mLeftBottemImg);
		load.LoadImg(list.get(position).brandList.get(5).imgpath, hold.mRightBottemImg);
		return convertView;
	}

	private class ViewHolder{
		TextView mTitleTv;
		ImageView mMoreImg;
		ImageView mBigImg;
		ImageView mLeftImg;
		ImageView mLeftTopImg;
		ImageView mRightTopImg;
		ImageView mLeftBottemImg;
		ImageView mRightBottemImg;
	}

}
