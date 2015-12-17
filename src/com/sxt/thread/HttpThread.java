package com.sxt.thread;

import android.os.Message;
import android.util.Log;

import com.sxt.base.BaseNetInfo;
import com.sxt.bean.NetBitmapInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.net.str.PostUtils;
import com.sxt.net.str.UpBitmapUtils;
import com.sxt.utils.FileUtils;
import com.sxt.utils.IsNetUtils;
import com.sxt.utils.JSONUtils;

/**
 * ��������߳�
 * */
public class HttpThread implements Runnable{

	private BaseNetInfo info;
	private IsNetUtils isnet;
	private JSONUtils utils = new JSONUtils();//json���ݽ���ʹ��
	private FileUtils fileutils;

	public HttpThread(BaseNetInfo info){
		this.info = info;
		isnet = new IsNetUtils(this.info.ctx);
		fileutils = new FileUtils(this.info.ctx);
	}

	@Override
	public void run() {
		Message msg = info.hand.obtainMessage();
		msg.arg1 = info.arg1;
		//����״̬�ж�(��)
		if(!isnet.IsNet()){
			//������Ϣ��ui�߳�->��������ʾ
			msg.what = 201;
			msg.obj = "����������";
			info.hand.sendMessage(msg);
			return;
		}
		String result = null;//�����������ֵ
		//������->��������ʽ
		if(info.netFlag == 1){
			//����JSON����
			result = new PostUtils().PostStr((NetStrInfo)info);
			//�����Ƿ�����ɹ��ж�
		}else if(info.netFlag == 2){
			//�ϴ�ͼƬ����
			result = new UpBitmapUtils().upBitmap((NetBitmapInfo)info);
		}
		if(result.length() < 10){
			msg.what = 201;
			msg.obj = result;
		}else{
			Log.e("","result1:"+result);
			//����ɹ����������ݱ��浽����
			fileutils.WriteStr(
					info.interfaceStr.substring(0, info.interfaceStr.lastIndexOf(".")), result);
			msg.what = 200;
			//����������json���սӿ����ֽ�������
			msg.obj = utils.BuidList(info.interfaceStr, result);
		}
		info.hand.sendMessage(msg);
	}

}