package com.sxt.activity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import com.sxt.bean.UserInfo;
import com.sxt.thread.HttpThreadPool;
import com.sxt.utils.SharesUtils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;

public class MyApplication extends Application{

	public static Context ctx;
	public HttpThreadPool Thread_Pool;
	public DialogActivity DialogAct;//�Ի�����ʽ��activity 
	public int ShopID = 0;//���˵�ѡ������
	public List<DialogActivity>dialogList = new ArrayList<DialogActivity>();
	public UserInfo userInfo=null;
	public static int versionCode = 1;//Ӧ�ó���汾
	public SharesUtils share;
	public Bitmap mheadBitmap = null;

	@Override
	public void onCreate() {
		super.onCreate();
		share = new SharesUtils(this);
		PackageManager pm = this.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(
					this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionCode =  info.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ctx = this;
		Thread_Pool = new HttpThreadPool();
		//��ѯ���ػ����û���Ϣ->��ס�û���¼״̬->�����������
		userInfo = new UserInfo();
		//����xml�л�ȡ�û�������Ϣ
		userInfo.id = share.readXML("id");
		userInfo.NickName = share.readXML("NickName");
		userInfo.PassWod = share.readXML("PassWod");
		userInfo.UserBirthday = share.readXML("UserBirthday");
		userInfo.UserHead = share.readXML("UserHead");
		userInfo.UserName = share.readXML("UserName");
		userInfo.UserSex = share.readXML("UserSex");
		JPushInterface.setDebugMode(true); 	// ���ÿ�����־,����ʱ��ر���־
		JPushInterface.init(getApplicationContext());
	}

}
