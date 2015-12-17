package com.sxt.adapter;

import java.util.List;

import com.sxt.activity.R;
import com.sxt.bean.OrderChildInfo;
import com.sxt.bean.OrderInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 确认订单中expand关联适配器
 * */
public class OrderExpandAdapter extends BaseExpandableListAdapter{

	private List<OrderInfo>list;
	private Context ctx;

	public OrderExpandAdapter(List<OrderInfo>list,Context ctx){
		this.list = list;
		this.ctx = ctx;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return list.get(arg0).list.get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public View getChildView(final int arg0, final int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		if(arg3 == null)
			arg3 = View.inflate(ctx, R.layout.item_orderchild, null);
		TextView tv = (TextView) arg3.findViewById(R.id.OrderChild_Tv);
		ImageView img = (ImageView) arg3.findViewById(R.id.OrderChild_Img);
		tv.setText(list.get(arg0).list.get(arg1).str);
		if(list.get(arg0).list.get(arg1).flag){
			img.setImageResource(R.drawable.cart_selent_highlighted);
		}else{
			img.setImageResource(R.drawable.cart_selent_nromal);
		}
		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg11) {
				for(OrderChildInfo info:list.get(arg0).list){
					info.flag = false;
				}
				String str;
				//改变childlist中数据
				OrderChildInfo info = list.get(arg0).list.get(arg1);
				info.flag = true;
				str = info.str;
				list.get(arg0).list.set(arg1, info);
				//改变grouplist中数据
				OrderInfo in = list.get(arg0);
				in.value = str;
				list.set(arg0, in);
				notifyDataSetChanged();
			}
		});
		return arg3;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0).list.size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		if(arg2 == null)
			arg2 = View.inflate(ctx, R.layout.item_ordergroup, null);
		TextView title = (TextView) arg2.findViewById(R.id.OrderGroup_Title);
		TextView value = (TextView) arg2.findViewById(R.id.OrderGroup_Value);
		ImageView img = (ImageView) arg2.findViewById(R.id.OrderGroup_Arrow);
		title.setText(list.get(arg0).title);
		value.setText(list.get(arg0).value);
		if(arg1){
			img.setImageResource(R.drawable.goodslist_screen_up_icon);
		}else{
			img.setImageResource(R.drawable.goodslist_screen_down_icon);
		}
		return arg2;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

}
