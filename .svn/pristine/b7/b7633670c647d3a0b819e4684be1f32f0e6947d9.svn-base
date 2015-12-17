package com.sxt.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.sxt.activity.MyApplication;
import com.sxt.activity.R;
import com.sxt.adapter.FollowAdapter;
import com.sxt.base.BaseFragment;
import com.sxt.base.BaseHandler;
import com.sxt.bean.FollowInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.model.HttpModel;
import com.sxt.thread.HttpThread;
import com.sxt.view.PullListView;
import com.sxt.view.PullListView.PullCall;

/**
 * πÿ◊¢fragment->classesfragmentœ¬ÃÌº”
 * */
public class FollowFragment extends BaseFragment implements PullCall{

	PullListView mLv;
	private List<FollowInfo>list = new ArrayList<FollowInfo>();
	private FollowAdapter adapter;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.fragment_focus, null);
		mLv = (PullListView) view.findViewById(R.id.FocusFragmentLv);
		mLv.disADView();
		mLv.setCall(this);
		return view;
	}

	@Override
	protected void initList() {
		super.initList();
		NetStrInfo info = new NetStrInfo();
		info.arg1 = 0;
		info.ctx = getActivity();
		info.hand = hand;
		info.interfaceStr = HttpModel.FOLLOWURL;
		info.netFlag = 1;
		info.pramase = "";
		((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(info));

	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what == 200){
				mLv.complate();
				list.clear();
				if(adapter!=null)
					adapter.notifyDataSetChanged();
				adapter = null;
				list = (List<FollowInfo>) msg.obj;
				adapter = new FollowAdapter(list, getActivity());
				mLv.setAdapter(adapter);
			}
		};
	};

	@Override
	public void RefrashCall() {
		NetStrInfo info = new NetStrInfo();
		info.arg1 = 0;
		info.ctx = getActivity();
		info.hand = hand;
		info.interfaceStr = HttpModel.FOLLOWURL;
		info.netFlag = 1;
		info.pramase = "";
		((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
	}

	@Override
	public void LoadCall() {
		mLv.complate();
	}

}
