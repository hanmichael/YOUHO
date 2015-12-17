package com.sxt.net.str;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.sxt.bean.NetBitmapInfo;
import com.sxt.model.HttpModel;
import com.sxt.utils.BitToStrUtils;

import android.graphics.Bitmap;

/**
 * ͼƬ�ϴ�������
 * */
public class UpBitmapUtils {

	private BitToStrUtils utils = new BitToStrUtils();

	/**
	 * ͼƬ�ϴ�����
	 * interfaceStr->�ϴ�ͼƬ�ӿ�
	 * pramase->�ϴ�����
	 * bitmap->Ҫ�ϴ�ͼƬ
	 * */
	public String upBitmap(NetBitmapInfo netInfo){
		String result = null;
		HttpPost post = new HttpPost(HttpModel.HTTPURL+netInfo.interfaceStr);
		HttpClient client = (HttpClient) new DefaultClientConnection();
		client.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,
				60*1000);
		client.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 60*1000);
		List<BasicNameValuePair>list = new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair(netInfo.Strkey, netInfo.pramase));//��������ӿڷ�װ
		list.add(new BasicNameValuePair(netInfo.Bitkey, utils.toStr(netInfo.bitmap)));//�����ͼƬ�ӿڷ�װ
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
			}else{
				result = "����ʧ��";
			}
		} catch (UnsupportedEncodingException e) {
			result = "��������ʧ��";
		} catch (ClientProtocolException e) {
			result = "����ʧ��";
		} catch (IOException e) {
			result = "����ʧ��";
		}

		return result;
	}

}
