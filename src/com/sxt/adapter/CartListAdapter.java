package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.CartListInfo;
import com.sxt.callback.AdapterCallBack;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CartListAdapter extends BaseAdapter{

	private List<CartListInfo>list;
	private Context ctx;
	private ImageLoad load;
	private AdapterCallBack call;

	public CartListAdapter(List<CartListInfo>list,Context ctx,AdapterCallBack call){
		this.list = list;
		this.ctx = ctx;
		this.call = call;
		load = new ImageLoad();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder hold;
		if(arg1 == null){
			hold = new ViewHolder();
			arg1 = View.inflate(ctx, R.layout.item_cartlist, null);
			hold.goodsImg = (ImageView) arg1.findViewById(R.id.CartListItem_Img);
			hold.numTv = (TextView) arg1.findViewById(R.id.CartListItem_NumTv);
			hold.priceTv = (TextView) arg1.findViewById(R.id.CartListItem_PriceTv);
			hold.scTv = (TextView) arg1.findViewById(R.id.CartListItem_SCTv);
			hold.selectImg = (ImageView) arg1.findViewById(R.id.CartListItem_SelectImg);
			hold.titleTv = (TextView) arg1.findViewById(R.id.CartListItem_TitleTv);
			arg1.setTag(hold);
		}else{
			hold = (ViewHolder) arg1.getTag();
		}
		if(list.get(arg0).flag){
			hold.selectImg.setImageResource(R.drawable.cart_selent_highlighted);
		}else{
			hold.selectImg.setImageResource(R.drawable.cart_selent_nromal);
		}
		hold.scTv.setText("ÑÕÉ«:"+list.get(arg0).color+" ³ß´ç:"
				+list.get(arg0).size);
		hold.numTv.setText("x"+list.get(arg0).num);
		hold.priceTv.setText("£¤"+list.get(arg0).price);
		hold.titleTv.setText(list.get(arg0).title);
		load.LoadImg(list.get(arg0).imgpath, hold.goodsImg);
		hold.selectImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg1) {
				CartListInfo info = list.get(arg0);
				info.flag = !info.flag;
				list.set(arg0, info);
				notifyDataSetChanged();
				call.RefrashPrice();
			}
		});
		return arg1;
	}

	private class ViewHolder{
		ImageView selectImg;
		ImageView goodsImg;
		TextView titleTv;
		TextView scTv;
		TextView priceTv;
		TextView numTv;
	}

}
