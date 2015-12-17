package com.sxt.base;

import java.util.ArrayList;
import java.util.List;

import com.sxt.activity.MyApplication;
import com.sxt.activity.R;
import com.sxt.activity.WebActivity;
import com.sxt.adapter.SeeListAdapter;
import com.sxt.bean.AdvertInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.bean.NewsInfo;
import com.sxt.callback.AdversetCallBack;
import com.sxt.model.HttpModel;
import com.sxt.thread.HttpThread;
import com.sxt.view.AdverstView;
import com.sxt.view.PullListView;
import com.sxt.view.PullListView.PullCall;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

/**
 * 逛界面下所有fragment基类
 * */
public class BaseSeeFragment extends BaseFragment 
implements AdversetCallBack,OnItemClickListener{

	private List seeList;
	private PullListView mLv;
	private AdverstView mAd;
	private List<AdvertInfo>adList;//广告返回数据
	private List<View>adViewList = new ArrayList<View>();
	private List<NewsInfo>newsList = new ArrayList<NewsInfo>();//逛列表数据
	private int pageindex = 10;//页面索引
	private SeeListAdapter adapter;
	private boolean PullFlag = false;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.fragment_basesee, null);
		mLv = (PullListView) view.findViewById(R.id.BaseSeeFragment_Lv);
		adapter = new SeeListAdapter(newsList, getActivity());
		mLv.setAdapter(adapter);
		mLv.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onDestroyView() {
		if(mAd!=null)
			mAd.stopTime();
		super.onDestroyView();
	}

	//控制广告轮播view隐藏方法
	public void disAD(){
		mLv.disADView();
	}

	public int getPage(){
		return pageindex;
	}

	//设置移除列表数据
	public void setClearFlag(){
		PullFlag = true;
	}

	//设置页面索引方法
	public void setPage(int pageindex){
		this.pageindex = pageindex;
	}

	//设置PullListView下拉刷新以及上啦加载方法
	public void setPullCall(PullCall call){
		mLv.setCall(call);
	}

	//发送网络请求
	public void createList(String interfaceName,String parames,boolean ADFlag){
		if(ADFlag){
			//发起广告请求操作
			NetStrInfo info = new NetStrInfo();
			info.arg1 = 0;
			info.ctx = getActivity();
			info.hand = hand;
			info.interfaceStr = HttpModel.ADVERTURL;
			info.pramase = "";
			info.netFlag = 1;
			((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
		}
		//发起逛接口
		NetStrInfo seeInfo = new NetStrInfo();
		seeInfo.arg1 = 1;
		seeInfo.ctx = getActivity();
		seeInfo.hand = hand;
		seeInfo.interfaceStr = interfaceName;
		seeInfo.netFlag = 1;
		seeInfo.pramase = parames;
		((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(seeInfo));
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			disDiaLog();
			super.handleMessage(msg);
			if(msg.what == 200){
				if(msg.arg1 == 0){
					//接收广告返回数据
					adList = (List<AdvertInfo>) msg.obj;
					mLv.settClearADView();
					mAd = new AdverstView();
					adViewList = null;
					adViewList = new ArrayList<View>();
					for(AdvertInfo info:adList){
						ImageView img = new ImageView(getActivity());
						img.setScaleType(ScaleType.FIT_XY);
						img.setLayoutParams
						(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
								ViewGroup.LayoutParams.MATCH_PARENT));
						img.setTag(info.imgpath);
						adViewList.add(img);
					}
					mAd.create(getActivity(),
							BitmapFactory.decodeResource(getResources(), 
									R.drawable.home_banner_dot_down_black),
									BitmapFactory.decodeResource(getResources(), R.drawable.home_banner_dot_down)
									, adViewList, mLv.getADHeight(),BaseSeeFragment.this);
					mLv.setADView(mAd.getView());
					mAd.startTime();
				}else if(msg.arg1 == 1){
					//接收逛返回数据
					List<NewsInfo> list = (List<NewsInfo>) msg.obj;
					mLv.complate();//关闭上啦或者下拉效果
					if(list!=null){
						//区分下拉以及上拉标识->当下拉刷新时清除原有数据
						if(PullFlag)
							newsList.clear();
						PullFlag = false;
						newsList.addAll(list);
						adapter.notifyDataSetChanged();
						if(list.size() == 10){
							//每次10条页面+10
							pageindex +=10;
						}else{
							//最后一页状态
							pageindex = -1;
						}
					}
				}
			}
		};
	};

	@Override
	public void adverstcall(int possion) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BaseSeeFragment.this.getActivity(),WebActivity.class);
		intent.putExtra("u", adList.get(possion).Url);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BaseSeeFragment.this.getActivity(),WebActivity.class);
		intent.putExtra("u", newsList.get(arg2).url);
		startActivity(intent);
	}

}
