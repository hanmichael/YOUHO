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
 * 图片上传工具类
 * */
public class UpBitmapUtils {

	private BitToStrUtils utils = new BitToStrUtils();

	/**
	 * 图片上传方法
	 * interfaceStr->上传图片接口
	 * pramase->上传参数
	 * bitmap->要上传图片
	 * */
	public String upBitmap(NetBitmapInfo netInfo){
		String result = null;
		HttpPost post = new HttpPost(HttpModel.HTTPURL+netInfo.interfaceStr);
		HttpClient client = (HttpClient) new DefaultClientConnection();
		client.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,
				60*1000);
		client.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 60*1000);
		List<BasicNameValuePair>list = new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair(netInfo.Strkey, netInfo.pramase));//请求参数接口封装
		list.add(new BasicNameValuePair(netInfo.Bitkey, utils.toStr(netInfo.bitmap)));//请求个图片接口封装
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
			}else{
				result = "请求失败";
			}
		} catch (UnsupportedEncodingException e) {
			result = "参数生成失败";
		} catch (ClientProtocolException e) {
			result = "连接失败";
		} catch (IOException e) {
			result = "传输失败";
		}

		return result;
	}

}
