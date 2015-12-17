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
 * ��ҳfragment
 * */
public class HomeFragment extends BaseFragment implements 
PullToLoadScrollLissener,OnClickListener,AdversetCallBack,
OnItemClickListener{

	private View view;//�뵱ǰfragment������view->������ˢ���Լ��������ظ����scrollview->fragment_home->1
	private PullToLoadScroll mSC;//��fragment_home��->�Զ���scrollview->������������-����ֱ������xml����->2
	private AdverstView aView = new AdverstView();//�Զ��������->���������->5(���ϲ�)
	private View homeView;//��ǰ����Ҫ��ʾ������->���Զ���Scrollview����ʾ->view_home->3
	private LinearLayout mAdverstLinear;//װ�ع�����������->��homeview�µ�linearLayout->4
	//��ҳ��8ģ��ͼ��
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
	private List<AdvertInfo> advertList;//��������б�
	private List<HomeInfo>homeList;//��ҳ�б�����
	private HomeItemAdapter adapter;
	private ListView mLv;
	private int page = 10;//��ҳ�Ƽ���Ʒ�б���ǩ->10(1-10);20(11-20);

	@Override
	public View initView() {
		view = View.inflate(getActivity(), R.layout.fragment_home, null);
		mSC = (PullToLoadScroll) view.findViewById(R.id.HomeFragmentSC);
		mSC.setCall(this);//���������Լ���������
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

	//��������ͼ�귽��
	private void updateIcon(){
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inSampleSize = 2;
		mHomeView_NewImg.setText("��Ʒ����");
		mHomeView_NewImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_xpdz_n,op));
		mHomeView_PlusImg.setText("������ѡ");
		mHomeView_PlusImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_qqyx_n,op));
		mHomeView_StarImg.setText("����ԭ��");
		mHomeView_StarImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_cptj,op));
		mHomeView_CategoryImg.setText("ȫ������");
		mHomeView_CategoryImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_qbpl_n,op));
		mHomeView_TrendImg.setText("��������");
		mHomeView_TrendImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_mxcp_n,op));
		mHomeView_CollocationImg.setText("��������");
		mHomeView_CollocationImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_dpzn_n,op));
		mHomeView_BeatImg.setText("���˽���");
		mHomeView_BeatImg.setImg(BitmapFactory.decodeResource(getResources(), R.drawable.btn_qxsc_n,op));
		mHomeView_SaleImg.setText("�ۿ�ר��");
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
		//������������->����б���ȡ
		NetStrInfo netInfo = new NetStrInfo();
		netInfo.arg1 = 0;//��basehandler��arg1ֵ��ͬ
		netInfo.ctx = getActivity();
		netInfo.hand = hand;
		netInfo.interfaceStr = HttpModel.ADVERTURL;
		netInfo.pramase = "";
		netInfo.netFlag = 1;
		((MyApplication)(getActivity().
				getApplicationContext())).Thread_Pool.execute(new HttpThread(netInfo));
		//��ȡ��ҳ���ݽӿ�
		NetStrInfo FirstInfo = new NetStrInfo();
		FirstInfo.arg1 = 1;
		FirstInfo.ctx = getActivity();
		FirstInfo.hand = hand;
		FirstInfo.interfaceStr = HttpModel.FIRSTHOMEURL;
		FirstInfo.netFlag = 1;
		FirstInfo.pramase = "parames={\"shop\":\""
				+((MyApplication)getActivity().getApplicationContext()).ShopID+"\"}";
		((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(FirstInfo));
		//��ȡ��ҳ�Ƽ���Ʒ�ӿ�
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
		//����ʱ�رչ���ֲ�����
		aView.stopTime();
	};

	//���������������handler
	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			HomeFragment.this.disDiaLog();//���ؽ���������
			super.handleMessage(msg);
			//			mSC.commplate();
			if(msg.what!=200)
				return;
			if(msg.arg1 == 0){
				//���ص�����
				advertList = (List<AdvertInfo>) msg.obj;
				//������̬������
				createAdvertView();
				aView.create(getActivity(), PointN, PointP,
						list,mAdverstLinear.getMeasuredHeight(),
						HomeFragment.this);
				mAdverstLinear.addView(aView.getView());
			}else if(msg.arg1 == 1){
				homeList = (List<HomeInfo>) msg.obj;
				adapter = new HomeItemAdapter(homeList, getActivity());
				mLv.setAdapter(adapter);
				mSC.measureLv(mLv, adapter);//����������listview�ĸ߶�
			}else if(msg.arg1 == 3){
				mSC.commplate();
			}
		};
	};

	//������ݶ��η�װ
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
			//�ص�frameactivity�л���ȫ������
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 1);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_TrendImg:
			//�ص�frameactivity�л������µĻ���
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 2);
			intent.putExtra("child", 1);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_CollocationImg:
			//�ص�frameactivity�л������µĴ���
			intent.setAction(ActionModel.FragmentSelect);
			intent.putExtra("FID", 2);
			intent.putExtra("child", 2);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.HomeView_BeatImg:
			//�ص�frameactivity�л������µĳ���
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