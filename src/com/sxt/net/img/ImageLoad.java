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
 * ͼƬ�첽���ع�����
 * */

public class ImageLoad implements OnScrollListener{

	//lrucatch-���ڴ��з���ͼƬ
	//�̳߳�
	//Listview������->listview
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
	//�첽����ͼƬlistview
	private ListView mLv;
	private FileUtils fileUtils;
	private Set<ImageView>imgSet = new HashSet<ImageView>();
	//�׶���־
	private boolean FlingFlag = false;

	public void setLV(ListView lv){
		this.mLv = lv;

	}

	//����ͼƬ����
	/**
	 * name->ͼƬ����
	 * img->��ʾͼƬ�ؼ�
	 * */
	public void LoadImg(String name,ImageView img){
		//ͼƬ�����Լ�imageview����Ϊ��
		if(name == null)
			return;
		if(img == null)
			return;
		img.setTag(name);
		img.setImageResource(R.drawable.mine_banner_girl);
		imgSet.add(img);
		//�׶�״̬������ͼƬ
		if(FlingFlag)
			return;
		//lru
		Bitmap bitmap = lru.get(name);
		if(bitmap != null){
			img.setImageBitmap(bitmap);
			return;
		}
		//����->����
		if(fileUtils == null)
			fileUtils = new FileUtils(MyApplication.ctx);
		bitmap = fileUtils.ReadBitmap(name);
		if(bitmap!=null){
			img.setImageBitmap(bitmap);
			return;
		}
		//����
		if(thread_pool == null){
			thread_pool = Executors.newFixedThreadPool(max_pool);
		}
		//�������ز���
		thread_pool.execute(new BitmapThread(name));
	}

	private class BitHand extends android.os.Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//��ȡ���سɹ�bitmap��ʾ������
			Bitmap bit = (Bitmap) msg.obj;
			String name = msg.getData().getString("bitname");
			Log.e("","hand:"+name);
			//����ͼƬ���ƻ�ȡ��Ӧimageview->����set->����iamgeview��tag��ͼƬ���ƣ��Ƚ�
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

	//����ͼƬ�߳�
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
						//����lru
						//����
						lru.put(name, bit);
						fileUtils.WriteBitmap(name, bit);
						//���سɹ�ͼƬ���ݻ�UI
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
		//�ܶ�ʱ������;�����Լ�ֹͣ����ͼƬ
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			if(FlingFlag){
				FlingFlag = false;
				//����set����ͼƬ
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