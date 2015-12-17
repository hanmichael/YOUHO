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
 * �����������fragment����
 * */
public class BaseSeeFragment extends BaseFragment 
implements AdversetCallBack,OnItemClickListener{

	private List seeList;
	private PullListView mLv;
	private AdverstView mAd;
	private List<AdvertInfo>adList;//��淵������
	private List<View>adViewList = new ArrayList<View>();
	private List<NewsInfo>newsList = new ArrayList<NewsInfo>();//���б�����
	private int pageindex = 10;//ҳ������
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

	//���ƹ���ֲ�view���ط���
	public void disAD(){
		mLv.disADView();
	}

	public int getPage(){
		return pageindex;
	}

	//�����Ƴ��б�����
	public void setClearFlag(){
		PullFlag = true;
	}

	//����ҳ����������
	public void setPage(int pageindex){
		this.pageindex = pageindex;
	}

	//����PullListView����ˢ���Լ��������ط���
	public void setPullCall(PullCall call){
		mLv.setCall(call);
	}

	//������������
	public void createList(String interfaceName,String parames,boolean ADFlag){
		if(ADFlag){
			//�������������
			NetStrInfo info = new NetStrInfo();
			info.arg1 = 0;
			info.ctx = getActivity();
			info.hand = hand;
			info.interfaceStr = HttpModel.ADVERTURL;
			info.pramase = "";
			info.netFlag = 1;
			((MyApplication)getActivity().getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
		}
		//�����ӿ�
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
					//���չ�淵������
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
					//���չ䷵������
					List<NewsInfo> list = (List<NewsInfo>) msg.obj;
					mLv.complate();//�ر�������������Ч��
					if(list!=null){
						//���������Լ�������ʶ->������ˢ��ʱ���ԭ������
						if(PullFlag)
							newsList.clear();
						PullFlag = false;
						newsList.addAll(list);
						adapter.notifyDataSetChanged();
						if(list.size() == 10){
							//ÿ��10��ҳ��+10
							pageindex +=10;
						}else{
							//���һҳ״̬
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
