package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.ImgInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsValueAdvrstAdapter extends PagerAdapter{

	private List<ImgInfo>list;
	private Context ctx;
	private ImageLoad load;
	public GoodsValueAdvrstAdapter(List<ImgInfo>list,Context ctx){
		this.list = list;
		this.ctx = ctx;
		load = new ImageLoad();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size()+1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(ctx, R.layout.item_goodsvaluetop, null);
		TextView tv = (TextView) view.findViewById(R.id.GoodsValueItem_Tv);
		ImageView img = (ImageView) view.findViewById(R.id.GoodsValueItem_Img);
		//区分最后一页
		if(position == (list.size())){
			//显示textview,隐藏imageview
			tv.setVisibility(View.VISIBLE);
			img.setVisibility(View.GONE);
		}else{
			//显示imageview,隐藏textview
			img.setVisibility(View.VISIBLE);
			tv.setVisibility(View.GONE);
			load.LoadImg(list.get(position).imgpath, img);
		}
		container.addView(view);
		return view;
	}
}
