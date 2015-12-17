package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.GoodsListActivity;
import com.sxt.activity.R;
import com.sxt.bean.FollowInfo;
import com.sxt.bean.GoodsInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 关注界面适配器
 * */
public class FollowAdapter extends BaseAdapter{

	private List<FollowInfo> list;
	private Context ctx;
	private ImageLoad load;

	public FollowAdapter(List<FollowInfo> list,Context ctx){
		this.list = list;
		this.ctx = ctx;
		load = new ImageLoad();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder hold;
		if(convertView == null){
			hold = new ViewHolder();
			convertView = View.inflate(ctx, R.layout.item_follow, null);
			hold.BrandImg = (ImageView) convertView.findViewById(R.id.FollowItem_BrandImg);
			hold.BrandTv = (TextView) convertView.findViewById(R.id.FollowItem_NameTv);
			hold.LikeImg = (ImageView) convertView.findViewById(R.id.FollowItem_LikeImg);
			hold.distance1 = (TextView) convertView.findViewById(R.id.FollowItem_DistanceTv);
			hold.distance2 = (TextView) convertView.findViewById(R.id.FollowItem_DistanceTv1);
			hold.distance3 = (TextView) convertView.findViewById(R.id.FollowItem_DistanceTv2);
			hold.img1 = (ImageView) convertView.findViewById(R.id.FollowItem_Img);
			hold.img2 = (ImageView) convertView.findViewById(R.id.FollowItem_Img1);
			hold.img3 = (ImageView) convertView.findViewById(R.id.FollowItem_Img2);
			hold.price1 = (TextView) convertView.findViewById(R.id.FollowItem_PriceTv);
			hold.price2 = (TextView) convertView.findViewById(R.id.FollowItem_PriceTv1);
			hold.price3 = (TextView) convertView.findViewById(R.id.FollowItem_PriceTv2);
			convertView.setTag(hold);
		}else{
			hold = (ViewHolder) convertView.getTag();
		}

		hold.BrandTv.setText(list.get(position).brandname);
		hold.BrandTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx,GoodsListActivity.class);
				intent.putExtra("name", list.get(position).brandname);
				intent.putExtra("id", "1");//1->服务器品牌id
				ctx.startActivity(intent);
			}
		});
		load.LoadImg(list.get(position).brandimg, hold.BrandImg);
		List<GoodsInfo>gList = list.get(position).goodsList;
		hold.distance1.setVisibility(View.GONE);
		hold.distance2.setVisibility(View.GONE);
		hold.distance3.setVisibility(View.GONE);
		hold.price1.setVisibility(View.GONE);
		hold.price2.setVisibility(View.GONE);
		hold.price3.setVisibility(View.GONE);
		hold.img1.setVisibility(View.GONE);
		hold.img2.setVisibility(View.GONE);
		hold.img3.setVisibility(View.GONE);
		if(gList.size() > 0){
			hold.price1.setVisibility(View.VISIBLE);
			hold.img1.setVisibility(View.VISIBLE);
			hold.price1.setText(gList.get(0).price);
			if(!gList.get(0).price.equals(gList.get(0).distance)){
				hold.distance1.setVisibility(View.VISIBLE);
				hold.distance1.setText(gList.get(0).distance);
			}
			load.LoadImg(gList.get(0).goodsimg, hold.img1);
		}
		if(gList.size() > 1){
			hold.price2.setVisibility(View.VISIBLE);
			hold.img2.setVisibility(View.VISIBLE);
			hold.price2.setText(gList.get(0).price);
			if(!gList.get(1).price.equals(gList.get(1).distance)){
				hold.distance2.setVisibility(View.VISIBLE);
				hold.distance2.setText(gList.get(1).distance);
			}
			load.LoadImg(gList.get(1).goodsimg, hold.img2);
		}
		if(gList.size() > 2){
			hold.price3.setVisibility(View.VISIBLE);
			hold.img3.setVisibility(View.VISIBLE);
			hold.price3.setText(gList.get(2).price);
			if(!gList.get(2).price.equals(gList.get(2).distance)){
				hold.distance3.setVisibility(View.VISIBLE);
				hold.distance3.setText(gList.get(2).distance);
			}
			load.LoadImg(gList.get(2).goodsimg, hold.img3);
		}
		return convertView;
	}

	private class ViewHolder{
		ImageView BrandImg;
		ImageView LikeImg;
		TextView BrandTv;
		ImageView img1;
		ImageView img2;
		ImageView img3;
		TextView price1;
		TextView price2;
		TextView price3;
		TextView distance1;
		TextView distance2;
		TextView distance3;
	}

}
