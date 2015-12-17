package com.sxt.fragment;

import android.view.View;

import com.sxt.activity.R;
import com.sxt.base.BaseFragment;

/**
 * ¹ºÎï³µfragment
 * */
public class CartFragment extends BaseFragment{

	private View view;

	@Override
	public View initView() {
		view = View.inflate(getActivity(), R.layout.fragment_cart, null);
		return view;
	}

}
