package com.sxt.adapter;

import java.util.List;

import com.sxt.base.BaseSeeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SeeAdapter extends FragmentPagerAdapter{

	public SeeAdapter(FragmentManager fm,List<BaseSeeFragment>list) {
		super(fm);
		this.list = list;
	}

	private List<BaseSeeFragment>list;


	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
