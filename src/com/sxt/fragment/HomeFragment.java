package com.sxt.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sxt.activity.GoodsListActivity;
import com.sxt.activity.MyApplication;
import com.sxt.activity.R;
import com.sxt.activity.WebActivity;
import com.sxt.adapter.HomeItemAdapter;
import com.sxt.base.BaseFragment;
import com.sxt.base.BaseHandler;
import com.sxt.base.BaseNetInfo;
import com.sxt.bean.AdvertInfo;
import com.sxt.bean.HomeInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.callback.AdversetCallBack;
import com.sxt.callback.PullToLoadScrollLissener;
import com.sxt.model.ActionModel;
import com.sxt.model.HttpModel;
import com.sxt.thread.HttpThread;
import com.sxt.view.AdverstView;
import com.sxt.view.IconView;
import com.sxt.view.PullToLoadScroll;

/**
 * 首页fragment
 * */
public class HomeFragment extends BaseFragment implements 
PullToLoadScrollLissener,OnClickListener,AdversetCallBack,
OnItemClickListener{

	private View view;//与当前fragment关联的view->带下拉刷新以及上啦加载更多的scrollview->fragment_home->1
	private PullToLoadScroll mSC;//在fragment_home下->自定义scrollview->上啦下拉作用-不能直接添加xml布局->2
	private AdverstView aView = new AdverstView();//自定义广告界面->方便服用性->5(最上层)
	private View homeView;//当前界面要显示的内容->在自定义Scrollview中显示->view_home->3
	private LinearLayout mAdverstLinear;//装载滚动广告的容器->在homeview下的linearLayout->4
	//首页轴8模块图标
	private IconView mHomeView_NewImg;
	private IconView mHomeView_PlusImg;
	private IconView mHomeView_StarImg;
	private IconView mHomeView_CategoryImg;
	private IconView mHomeView_TrendImg;
	private IconView mHomeView_CollocationImg;
	private IconView mHomeView_BeatImg;
	private IconView mHomeView_SaleImg;
	private Bitmap PointN;
	private Bitmap PointP;
	private List<View>list = new ArrayList<View>();
	private List<AdvertInfo> advertList;//广告数据列表
	private List<HomeInfo>homeList;//首页列表数据
	private HomeItemAdapter adapter;
	private ListView mLv;
	private int page = 10;//首页推荐商品列表标签->10(1-10);20(11-20);

	@Override
	public View initView() {
		view = View.inflate(getActivity(), R.layout.fragment_home, null);
		mSC = (PullToLoadScroll) view.findViewById(R.id.HomeFragmentSC);
		mSC.setCall(this);//设置下拉以及上啦监听
		homeView = View.inflate(getActivity(), R.layout.view_home, null);
		mSC.setContentView(homeView);
		mAdverstLinear = (LinearLayout) homeView.findViewById(R.id.HomeFragment_AdverstLinear);
		mHomeView_NewImg = (IconView) homeView.findViewById(R.id.HomeView_NewImg);
		mHomeView_PlusImg = (IconView) homeView.findViewById(R.id.HomeView_PlusImg);
		mHomeView_StarImg = (IconView) homeView.findViewById(R.id.HomeView_StarImg);
		mHomeView_CategoryImg = (IconView) homeView.findViewById(R.id.HomeView_CategoryImg);
		mHomeView_TrendImg = (IconView) homeView.findViewById(R.id.HomeView_TrendImg);
		mHomeView_CollocationImg = (IconView) homeView.findViewById(R.id.HomeView_CollocationImg);
		mHomeView_BeatImg = (IconView) homeView.findViewById(R.id.HomeView_BeatImg);
		mHomeView_SaleImg = (IconView) homeView.findViewById(R.id.HomeView_SaleImg);
		mLv = (ListView) homeView.findViewById(R.id.HomeView_Lv);
		mLv.setOnItemClickListener(this);
		updateIcon();
		return view;
	}

	//更新软件图标方法
	private void updateIcon(){
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inSampleSize = 2;
		mHomeView_NewImg.setText("新品到着");
		mHomeView_NewImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_xpdz_n,op));
		mHomeView_PlusImg.setText("潮流优选");
		mHomeView_PlusImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_qqyx_n,op));
		mHomeView_StarImg.setText("明星原创");
		mHomeView_StarImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_cptj,op));
		mHomeView_CategoryImg.setText("全部分类");
		mHomeView_CategoryImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_qbpl_n,op));
		mHomeView_TrendImg.setText("潮流话题");
		mHomeView_TrendImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_mxcp_n,op));
		mHomeView_CollocationImg.setText("人气搭配");
		mHomeView_CollocationImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_dpzn_n,op));
		mHomeView_BeatImg.setText("潮人街拍");
		mHomeView_BeatImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_qxsc_n,op));
		mHomeView_SaleImg.setText("折扣专区");
		mHomeView_SaleImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_zkjx_n,op));
	}

	@Override
	public void onStart() {
		super.onStart();
		aView.startTime();
	}

	@Override
	protected void initList() {
		super.initList();
		PointN = BitmapFactory.decodeResource(getResources(), R.drawable.home_banner_dot_down_black);
		PointP = BitmapFactory.decodeResource(getResources(), R.drawable.home_banner_dot_down);
		//发起网络请求->广告列表获取
		NetStrInfo netInfo = new NetStrInfo();
		netInfo.arg1 = 0;//与basehandler中arg1值相同
		netInfo.ctx = getActivity();
		netInfo.hand = hand;
		netInfo.interfaceStr = HttpModel.ADVERTURL;
		netInfo.pramase = "";
		netInfo.netFlag = 1;
		((MyApplication)(getActivity().
				getApplicationContext())).Thread_Pool.execute(new HttpThread(netInfo));
		//获取首页内容接口
		NetStrInfo FirstInfo = new NetStrInfo();
		FirstInfo.arg1 = 1;
		FirstInfo.ctx = getActivity();
		FirstInfo.hand = hand;
		FirstInfo.interfaceStr = HttpModel.FIRSTHOMEURL;
		FirstInfo.netFlag = 1;
		FirstInfo.pramase = "parames={\"shop\":\""
				+((MyApplication)getActivity().getApplicationContext()).ShopID+"\"}";
		((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(FirstInfo));
		//获取首页推荐商品接口
		NetStrInfo commendInfo = new NetStrInfo();
		commendInfo.arg1 = 2;
		commendInfo.ctx = getActivity();
		commendInfo.hand = hand;
		commendInfo.interfaceStr = HttpModel.RECOMMENDURL;
		commendInfo.netFlag = 1;
		commendInfo.pramase = "parames={\"page\":\""+page+"\"}";
		((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(commendInfo));
	}

	@Override
	protected void setClick() {
		super.setClick();
		mHomeView_NewImg.setOnClickListener(this);
		mHomeView_PlusImg.setOnClickListener(this);
		mHomeView_StarImg.setOnClickListener(this);
		mHomeView_CategoryImg.setOnClickListener(this);
		mHomeView_TrendImg.setOnClickListener(this);
		mHomeView_CollocationImg.setOnClickListener(this);
		mHomeView_BeatImg.setOnClickListener(this);
		mHomeView_SaleImg.setOnClickListener(this);
	}

	@Override
	public void RefrashCall() {

		new Thread(){

			public void run(){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = hand.obtainMessage();
				msg.what = 200;
				msg.arg1 = 3;
				hand.sendMessage(msg);
			}

		}.start();

	}

	@Override
	public void LoadCall() {
		new Thread(){

			public void run(){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = hand.obtainMessage();
				msg.what = 200;
				msg.arg1 = 3;
				hand.sendMessage(msg);
			}

		}.start();
	}

	public void onDestroy() {
		super.onDestroy();
		//销毁时关闭广告轮播操作
		aView.stopTime();
	};

	//处理所有网络相关handler
	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			HomeFragment.this.disDiaLog();//隐藏进度条操作
			super.handleMessage(msg);
			//			mSC.commplate();
			if(msg.what!=200)
				return;
			if(msg.arg1 == 0){
				//广告回调进入
				advertList = (List<AdvertInfo>) msg.obj;
				//创建动态广告界面
				createAdvertView();
				aView.create(getActivity(), PointN, PointP,
						list,mAdverstLinear.getMeasuredHeight(),
						HomeFragment.this);
				mAdverstLinear.addView(aView.getView());
			}else if(msg.arg1 == 1){
				homeList = (List<HomeInfo>) msg.obj;
				adapter = new HomeItemAdapter(homeList, getActivity());
				mLv.setAdapter(adapter);
				mSC.measureLv(mLv, adapter);//测量包含的listview的高度
			}else if(msg.arg1 == 3){
				mSC.commplate();
			}
		};
	};

	//广告数据二次封装
	private void createAdvertView(){
		if(advertList == null)
			return;
		for(AdvertInfo info : advertList){
			ImageView img = new ImageView(getActivity());
			img.setImageResource(R.drawable.homepage_background);
			img.setLayoutParams(
					new ViewGroup.LayoutParams
					(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT));
			img.setScaleType(ScaleType.FIT_XY);
			img.setTag(info.imgpath);
			list.add(img);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch(v.getId()){
		case R.id.HomeView_NewImg:
			break;
		case R.id.HomeView_PlusImg:
			break;
		case R.id.HomeView_StarImg:
			break;
		case R.id.HomeView_CategoryImg:
			//回调frameactivity切换到全部分类
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 1);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_TrendImg:
			//回调frameactivity切换到逛下的话题
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 2);
			intent.putExtra("child", 1);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_CollocationImg:
			//回调frameactivity切换到逛下的搭配
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 2);
			intent.putExtra("child", 2);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_BeatImg:
			//回调frameactivity切换到逛下的潮人
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 2);
			intent.putExtra("child", 3);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_SaleImg:
			break;
		}
	}

	@Override
	public void adverstcall(int possion) {
		Intent intent = new Intent(HomeFragment.this.getActivity(),WebActivity.class);
		intent.putExtra("u", advertList.get(possion).Url);
		HomeFragment.this.getActivity().startActivity(intent);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(),GoodsListActivity.class);
		intent.putExtra("name",homeList.get(arg2).Title);
		intent.putExtra("id", "1");
		startActivity(intent);
	}


}
