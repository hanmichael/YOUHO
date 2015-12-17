package com.sxt.adapter;

import java.util.List;
import com.sxt.activity.R;
import com.sxt.bean.ColorAndSizeInfo;
import com.sxt.callback.RecyclerViewCallBack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * —’…´ªÚ≥ﬂ¥Á  ≈‰∆˜
 * */
public class ColorOrSizeAdapter extends RecyclerView.Adapter<ColorOrSizeAdapter.MyVH>{

	private List<ColorAndSizeInfo>list;
	private Context ctx;
	private RecyclerViewCallBack callback;
	private int GroupId = 0;

	public ColorOrSizeAdapter(List<ColorAndSizeInfo>list,
			Context ctx,RecyclerViewCallBack callback,
			int GroupId){
		this.list = list;
		this.ctx = ctx;
		this.callback = callback;
		this.GroupId = GroupId;
	}

	class MyVH extends RecyclerView.ViewHolder{

		TextView tv;

		public MyVH(View arg0) {
			super(arg0);
			tv = (TextView) arg0.findViewById(R.id.ColorOrSizeItem_Tv);
			tv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					callback.call((Integer)tv.getTag(), GroupId);
				}
			});
		}

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(MyVH arg0, int arg1) {
		if(list.get(arg1).flag){
			arg0.tv.setBackgroundResource(R.drawable.shopping_selecte_red_box);
		}else{
			arg0.tv.setBackgroundResource(R.drawable.shopping_select_white_box);
		}
		arg0.tv.setText(list.get(arg1).str);
		arg0.tv.setTag(arg1);
	}

	@Override
	public MyVH onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = View.inflate(ctx, R.layout.item_colororsize, null);
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		return new MyVH(view);
	}

}
