package com.sxt.net.img;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sxt.activity.MyApplication;
import com.sxt.activity.R;
import com.sxt.model.HttpModel;
import com.sxt.utils.FileUtils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 图片异步加载工具类
 * */

public class ImageLoad implements OnScrollListener{

	//lrucatch-》内存中放置图片
	//线程池
	//Listview滑动锁->listview
	private int max_size = (int) (Runtime.getRuntime().maxMemory()/6);
	private BitHand hand = new BitHand();

	private LruCache<String, Bitmap>lru = new LruCache<String, Bitmap>(max_size){
		@SuppressLint("NewApi")
		protected int sizeOf(String key, Bitmap value) {
			return value.getByteCount();
		};
	};

	private static final int max_pool = 5; 
	private ExecutorService thread_pool;
	//异步加载图片listview
	private ListView mLv;
	private FileUtils fileUtils;
	private Set<ImageView>imgSet = new HashSet<ImageView>();
	//抛动标志
	private boolean FlingFlag = false;

	public void setLV(ListView lv){
		this.mLv = lv;

	}

	//载入图片方法
	/**
	 * name->图片名称
	 * img->显示图片控件
	 * */
	public void LoadImg(String name,ImageView img){
		//图片名称以及imageview不能为空
		if(name == null)
			return;
		if(img == null)
			return;
		img.setTag(name);
		img.setImageResource(R.drawable.mine_banner_girl);
		imgSet.add(img);
		//抛动状态不加载图片
		if(FlingFlag)
			return;
		//lru
		Bitmap bitmap = lru.get(name);
		if(bitmap != null){
			img.setImageBitmap(bitmap);
			return;
		}
		//本地->查找
		if(fileUtils == null)
			fileUtils = new FileUtils(MyApplication.ctx);
		bitmap = fileUtils.ReadBitmap(name);
		if(bitmap!=null){
			img.setImageBitmap(bitmap);
			return;
		}
		//网络
		if(thread_pool == null){
			thread_pool = Executors.newFixedThreadPool(max_pool);
		}
		//启动下载操作
		thread_pool.execute(new BitmapThread(name));
	}

	private class BitHand extends android.os.Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//获取下载成功bitmap显示界面中
			Bitmap bit = (Bitmap) msg.obj;
			String name = msg.getData().getString("bitname");
			Log.e("","hand:"+name);
			//根据图片名称获取对应imageview->遍历set->根据iamgeview中tag（图片名称）比较
			Iterator<ImageView>ite = imgSet.iterator();
			while(ite.hasNext()){
				ImageView img = ite.next();
				String imgName = (String) img.getTag();
				if(imgName.equals(name)){
					img.setImageBitmap(bit);
					break;
				}
			}
		}
	}

	//下载图片线程
	private class BitmapThread implements Runnable{

		String name;
		public BitmapThread(String name){
			this.name = name;
		}

		public void run(){

			try {
				Log.e("","HttpModel.IMGURL+name:"+(HttpModel.IMGURL+name));
				URL url = new URL(HttpModel.IMGURL+name);
				HttpURLConnection connect = (HttpURLConnection) url.openConnection();
				connect.setConnectTimeout(60*1000);
				connect.setReadTimeout(60*1000);
				connect.setDoInput(true);
				if(connect.getResponseCode() == 200){
					InputStream in = connect.getInputStream();
					Bitmap bit = BitmapFactory.decodeStream(in);
					in.close();
					if(bit!=null){
						//存入lru
						//本地
						lru.put(name, bit);
						fileUtils.WriteBitmap(name, bit);
						//下载成功图片传递回UI
						Message msg = hand.obtainMessage();
						msg.obj = bit;
						Bundle bund = new Bundle();
						bund.putString("bitname", name);
						msg.setData(bund);
						hand.sendMessage(msg);
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//跑动时不加载;滑动以及停止加载图片
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			if(FlingFlag){
				FlingFlag = false;
				//遍历set加载图片
				Iterator<ImageView>ite = imgSet.iterator();
				while(ite.hasNext()){
					ImageView img = ite.next();
					LoadImg(((String)img.getTag()), img);
				}
			}
		}else if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
			FlingFlag = false;
		}else if(scrollState == OnScrollListener.SCROLL_STATE_FLING){
			FlingFlag = true;
		}
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

}
