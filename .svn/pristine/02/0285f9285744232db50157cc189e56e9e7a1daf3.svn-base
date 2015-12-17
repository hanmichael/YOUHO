package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.ImgvaleInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GoodsValueLvAdapter extends BaseAdapter{

	private List<ImgvaleInfo>list;
	private Context ctx;
	private ImageLoad load;

	public GoodsValueLvAdapter(List<ImgvaleInfo>list,Context ctx){
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1 == null)
			arg1 = View.inflate(ctx, R.layout.item_goodsvaluelv,null);
		ImageView img = (ImageView) arg1.findViewById(R.id.GoodsValueItem_LvImg);
		load.LoadImg(list.get(arg0).imgpath, img);
		return arg1;
	}

}
