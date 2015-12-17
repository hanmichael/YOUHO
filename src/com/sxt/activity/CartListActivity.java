package com.sxt.activity;

import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxt.adapter.CartListAdapter;
import com.sxt.base.BaseActivity;
import com.sxt.base.BaseHandler;
import com.sxt.bean.CartListInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.callback.AdapterCallBack;
import com.sxt.model.HttpModel;
import com.sxt.thread.HttpThread;

/**
 * 购物车列表界面
 * */
public class CartListActivity extends BaseActivity implements 
OnClickListener,AdapterCallBack,OnItemClickListener{

	private ImageView mBackImg;
	private TextView mEdtTv;
	private ListView mLv;
	private ImageView mAllImg;
	private Button mPayBtn;
	private Button mLikeBtn;
	private TextView mAllTv;
	private TextView mTv;
	private float allPrice = 0f;
	private List<CartListInfo>list;
	private CartListAdapter adapter;

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_cartlist);
		mBackImg = (ImageView) f(R.id.CartListAct_BackImg);
		mEdtTv = (TextView) f(R.id.CartListAct_EdtTv);
		mLv = (ListView) f(R.id.CartListAct_Lv);
		mAllImg = (ImageView) f(R.id.CartListAct_SelectImg);
		mPayBtn = (Button) f(R.id.CartListAct_PayBtn);
		mLikeBtn = (Button) f(R.id.CartListAct_LikeBtn);
		mAllTv = (TextView) f(R.id.CartListAct_AllTv);
		mTv = (TextView) f(R.id.CartListAct_Tv);
		mLv.setOnItemClickListener(this);
		mBackImg.setOnClickListener(this);
		mEdtTv.setOnClickListener(this);
		mAllImg.setOnClickListener(this);
		mPayBtn.setOnClickListener(this);
		mLikeBtn.setOnClickListener(this);
	}

	@Override
	protected void initList() {
		super.initList();
		NetStrInfo info = new NetStrInfo();
		info.arg1 = 0;
		info.ctx = this;
		info.hand = hand;
		info.interfaceStr = HttpModel.CARTLISTURL;
		info.netFlag = 1;
		info.pramase = "parames={\"userId\":\""+((MyApplication)getApplicationContext()).userInfo.id+"\"}";
		((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
	}

	//计算总金额
	private void plusPrice(){
		allPrice = 0f;
		boolean flag = true;
		for(CartListInfo info : list){
			if(info.flag){
				float p = Float.valueOf(info.price);
				allPrice+=p;
			}
			if(!info.flag)
				flag = false;
		}
		mAllTv.setText("商品总计￥"+allPrice+"元");
		if(flag)
			mAllImg.setImageResource(R.drawable.cart_selent_highlighted);
		else
			mAllImg.setImageResource(R.drawable.cart_selent_nromal);
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what != 200){
				return;
			}
			if(msg.arg1 == 0){
				list = (List<CartListInfo>) msg.obj;
				plusPrice();
				adapter = new CartListAdapter(list,
						CartListActivity.this, CartListActivity.this);
				mLv.setAdapter(adapter);
			}else if(msg.arg1 == 1){
				List<String>strList = (List<String>) msg.obj;
				if(strList.get(0).equals("ok")){
					//订单创建成功->确认订单界面
					Toast("订单提交成功");
					Intent intent = new Intent(CartListActivity.this,OkOrderActivity.class);
					startActivity(intent);
				}
			}
		};
	};

	@Override
	public void onClick(View arg0) {
		int ID = arg0.getId();
		switch(ID){
		case R.id.CartListAct_BackImg:
			finish();
			break;
		case R.id.CartListAct_EdtTv:

			break;
		case R.id.CartListAct_SelectImg:
			if(adapter == null)
				break;
			for(CartListInfo info : list){
				info.flag = true;
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.CartListAct_PayBtn:
			NetStrInfo info = new NetStrInfo();
			info.arg1 = 1;
			info.ctx = this;
			info.hand = hand;
			info.interfaceStr = HttpModel.ADDORDER;
			info.netFlag = 1;
			/**
			 * userid->那个用户要购买商品
			 * 商品id->用户要购买的商品
			 * 数量->用户购买的商品件数
			 * 颜色->用户购买商品的颜色
			 * 尺寸->用户购买商品的尺寸
			 * {"arr":
			 * [{"goodsid":"1",color:red,size:m,num:1},
			 * {"goodsid":"1",color:blue,size:x,num:2}]}
			 * */
			info.pramase = "parames={\"userid\":\""
					+((MyApplication)getApplicationContext()).userInfo.id+"\"," +
					"\"goods\":[";
			for(CartListInfo in : list){
				//要花钱
				if(in.flag){
					info.pramase+="{\"goodsid\":\"1\"," +
							"\"color\":\""+in.color+"\"," +
							"\"size\":\""+in.size+"\"," +
							"\"num\":\""+in.num+"\"},";
				}
			}
			info.pramase = info.pramase.substring(0, info.pramase.length()-1);
			info.pramase +="]}";
			LOGE("order:"+info.pramase);
			((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
			break;
		case R.id.CartListAct_LikeBtn:
			break;
		}
	}


	@Override
	public void RefrashPrice() {
		plusPrice();
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this,GoodsValueActivity.class);
		intent.putExtra("goods","1");
		intent.putExtra("name",list.get(arg2).title);
		intent.putExtra("img",list.get(arg2).imgpath);
		startActivity(intent);
	}

}
