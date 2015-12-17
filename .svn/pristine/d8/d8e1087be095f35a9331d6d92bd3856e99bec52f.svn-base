package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.BrandInfo;
import com.sxt.net.img.ImageLoad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 热门品牌适配器
 * 1>RecyclerView.Adapter;
 * 2>HotBrandAdapte->内部类(ViewHolder)->强制必须编写viewholder优化
 * 3>内部类(ViewHolder)->初始化itemview中控件
 * 
 * */
public class HotBrandAdapter extends RecyclerView.Adapter<HotBrandAdapter.VH>{

	private List<BrandInfo>list;
	private Context ctx;
	private ImageLoad load;

	public HotBrandAdapter(List<BrandInfo>list,Context ctx){
		this.list = list;
		this.ctx = ctx;
		load = new ImageLoad();
	}

	class VH extends RecyclerView.ViewHolder{

		TextView tv;
		ImageView img;

		//arg0->itemview
		public VH(View arg0) {
			super(arg0);
			tv = (TextView) arg0.findViewById(R.id.HotBrandItem_TitleTv);
			img = (ImageView) arg0.findViewById(R.id.HotBrandItem_Img);
		}

	}

	//要显示条数
	@Override
	public int getItemCount() {
		return list.size();
	}

	//关联viewholder下view方法
	@Override
	public void onBindViewHolder(VH arg0, int arg1) {
		arg0.tv.setText(list.get(arg1).name);
		load.LoadImg(list.get(arg1).imgpath, arg0.img);
	}

	//创建viewholder
	@Override
	public VH onCreateViewHolder(ViewGroup arg0, int arg1) {
		View itemView = View.inflate(ctx, R.layout.item_hotbrand, null);
		VH vh = new VH(itemView);
		return vh;
	}

}