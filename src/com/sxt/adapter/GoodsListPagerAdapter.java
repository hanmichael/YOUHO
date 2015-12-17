package com.sxt.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Goodslistœ¬viewpager π”√  ≈‰∆˜
 * */
public class GoodsListPagerAdapter extends PagerAdapter{

	private List<GridView>list;

	public GoodsListPagerAdapter(List<GridView>list){
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		GridView gv = list.get(position);
		container.addView(gv);
		return gv;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}
}
