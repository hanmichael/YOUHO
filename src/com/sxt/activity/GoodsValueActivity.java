package com.sxt.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxt.adapter.GoodsValueAdvrstAdapter;
import com.sxt.adapter.GoodsValueLvAdapter;
import com.sxt.base.BaseActivity;
import com.sxt.base.BaseHandler;
import com.sxt.bean.GoodsInfo;
import com.sxt.bean.ImgInfo;
import com.sxt.bean.ImgvaleInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.model.HttpModel;
import com.sxt.net.img.ImageLoad;
import com.sxt.thread.HttpThread;
import com.sxt.view.GoodsScrollView;

/**
 * ��Ʒ�������
 * */
public class GoodsValueActivity extends BaseActivity 
implements OnClickListener,OnPageChangeListener{

	private String goodsId;//��Ʒid����һ����Ʒ�б��л�ȡ
	private String name;//��������
	private String img;//����ͼ��
	//��goodsValueactivity�����xml�¿ؼ�
	private RelativeLayout mCartBtn;
	private TextView mNumTv;
	private Button mAddCartBtn;
	private ImageView mLikeImg;
	private GoodsScrollView mSc;
	//�Զ�scrollview���Ϸ���ʾ
	private View UpView;
	private ViewPager mVp;
	private LinearLayout mPointLinear;
	private TextView mTitleTv;
	private TextView mGoodsValue_DisCountTv;
	private TextView mGoodsValue_PriceTv;
	private ImageView mGoodsValue_BrandImg;
	private TextView mGoodsValue_BrandName;
	private TextView mGoodsValue_GoShopTv;
	private GoodsValueAdvrstAdapter adverstAdater;
	//������ͼ��ͼ��
	private Bitmap pBit;
	private Bitmap nBit;
	//�Զ�scrollview���·���ʾview
	private ListView DownView;
	private GoodsValueLvAdapter adapter;
	private List<ImgvaleInfo>vList;
	private ImageView mBackImg;
	private ImageView mShareImg;
	private List<GoodsInfo>list;
	private List<ImgInfo>imgList;
	private ImageLoad load;

	@Override
	protected void initView() {
		super.initView();
		load = new ImageLoad();
		//		goodsId = getIntent().getStringExtra("goods");
		name = getIntent().getStringExtra("name");
		img = getIntent().getStringExtra("img");
		LOGE("name:"+name);
		LOGE("img:"+img);
		create(R.layout.activity_goodsvalue);
		mCartBtn = (RelativeLayout) f(R.id.GoodsValue_CartRelative);
		mBackImg = (ImageView) f(R.id.GoodsValue_BackImg);
		mShareImg = (ImageView) f(R.id.GoodsValue_ShareImg);
		mNumTv = (TextView) f(R.id.GoodsValue_NumTv);
		mAddCartBtn = (Button) f(R.id.GoodsValue_AddCartBtn);
		mLikeImg = (ImageView) f(R.id.GoodsValue_LikeImg);
		mSc = (GoodsScrollView) f(R.id.GoodsValue_Sc);
		//�����ϲ�����
		UpView = View.inflate(this, R.layout.view_goodsvalue_up, null);
		mVp = (ViewPager) UpView.findViewById(R.id.GoodsValue_Vp);
		mPointLinear = (LinearLayout) UpView.findViewById(R.id.GoodsValue_VpLinear);
		mTitleTv = (TextView) UpView.findViewById(R.id.GoodsValue_TitleTv);
		mGoodsValue_DisCountTv = (TextView) UpView.findViewById(R.id.GoodsValue_DisCountTv);
		mGoodsValue_PriceTv = (TextView) UpView.findViewById(R.id.GoodsValue_PriceTv);
		mGoodsValue_BrandImg = (ImageView) UpView.findViewById(R.id.GoodsValue_BrandImg);
		mGoodsValue_BrandName = (TextView) UpView.findViewById(R.id.GoodsValue_BrandName);
		mGoodsValue_GoShopTv = (TextView) UpView.findViewById(R.id.GoodsValue_GoShopTv);
		mVp.setOnPageChangeListener(this);
		load.LoadImg(img, mGoodsValue_BrandImg);
		mGoodsValue_BrandName.setText(name);
		//�����²�����
		DownView = (ListView) View.inflate(this, R.layout.view_goodsvalue_down, null);
		mBackImg.setOnClickListener(this);
		mShareImg.setOnClickListener(this);
		mAddCartBtn.setOnClickListener(this);
	}

	@Override
	protected void initList() {
		super.initList();
		pBit = BitmapFactory.decodeResource(getResources(), R.drawable.home_banner_dot_down);
		nBit = BitmapFactory.decodeResource(getResources(), R.drawable.home_banner_dot_down_black);
		NetStrInfo info = new NetStrInfo();
		info.arg1 = 0;
		info.ctx = this;
		info.hand = hand;
		info.interfaceStr = HttpModel.GOODSVALUEURL;
		info.netFlag = 1;
		info.pramase = "parames={\"goods_id\":\""+goodsId+"\"}";
		((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what != 200)
				return ;
			if(msg.arg1 == 0){
				//����������Ʒ���鷵��ֵ
				list = (List<GoodsInfo>) msg.obj;
				//��ȡ����ͼƬ����Դ�Լ�����
				imgList = list.get(0).imgList;
				vList = list.get(0).valueList;
				updateView();
			}
		};
	};

	//�޸���Ʒ���������Ʒ��Ϣ����
	private void updateView(){
		if(imgList.size() > 0){
			adverstAdater = new GoodsValueAdvrstAdapter(imgList, GoodsValueActivity.this);
			mVp.setAdapter(adverstAdater);
			//��̬����viewpager��������
			createPoint();
		}
		LOGE("vList.size():"+vList.size());
		if(vList.size() > 0){
			adapter = new GoodsValueLvAdapter(vList, this);
			DownView.setAdapter(adapter);
		}
		//up�Լ�down���ӽ���mSc
		mSc.setUpView(UpView);
		mSc.setDownView(DownView);
		//������Ʒ����۸�
		mTitleTv.setText(list.get(0).title);
		mGoodsValue_DisCountTv.setText(list.get(0).distance);
		mGoodsValue_PriceTv.setText(list.get(0).price);

	}

	//��̬����viewpager��������
	private void createPoint(){
		for(int i = 0;i < imgList.size();i ++){
			ImageView img = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
					(LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.rightMargin = 5;
			img.setLayoutParams
			(lp);
			if(i == 0)
				img.setImageBitmap(pBit);
			else 
				img.setImageBitmap(nBit);
			mPointLinear.addView(img, i);
		}
	}

	@Override
	public void onClick(View arg0) {

		if(R.id.GoodsValue_BackImg == arg0.getId()){
			finish();
		}else if(R.id.GoodsValue_ShareImg == arg0.getId()){

		}else if(R.id.GoodsValue_AddCartBtn == arg0.getId()){
			//			NetStrInfo info = new NetStrInfo();
			//			info.arg1 = 1;
			//			info.ctx = this;
			//			info.hand = hand;
			//			info.interfaceStr = HttpModel.ADDCARTURL;
			//			info.netFlag = 1;
			//			info.pramase = "";
			//�ж��û��Ƿ��¼
			if(!((MyApplication)getApplicationContext()).userInfo.id.equals("")){
				Intent intent = new Intent(GoodsValueActivity.this,AddCartActivity.class);
				intent.putExtra("title",list.get(0).title);
				intent.putExtra("price", list.get(0).price);
				intent.putExtra("discount", list.get(0).distance);
				intent.putExtra("img", list.get(0).imgList.get(0).imgpath);
				intent.putExtra("goodsID", goodsId);
				startActivity(intent);
			}else{
				//��ת�û���½����
				Intent intent = new Intent(this,CartListActivity.class);
				startActivity(intent);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		if(arg0 == imgList.size()){
			//�����鿴��Ʒ��Ϣ����->scrollview
			mVp.setCurrentItem(arg0-1);
			mSc.setScrollDown();
			return;
		}else{
			//���ҵ�����������imageview���������л���ɫ��ͼƬ
			for(int i = 0;i < imgList.size();i ++){
				ImageView img = (ImageView) mPointLinear.getChildAt(i);
				img.setImageBitmap(nBit);
				if(i == arg0){
					img.setImageBitmap(pBit);
				}
			}
		}
	}

}