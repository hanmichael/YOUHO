package com.sxt.net.str;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.sxt.bean.NetStrInfo;
import com.sxt.model.HttpModel;

/**
 * Post请求工具类
 * */
public class PostUtils {

	//Post请求方法
	public String PostStr(NetStrInfo netInfo){
		String result = null;
		try {
			URL url = new URL(HttpModel.HTTPURL+netInfo.interfaceStr);
			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			connect.setConnectTimeout(60*1000);
			connect.setReadTimeout(60*1000);
			connect.setRequestMethod("POST");
			connect.setDoInput(true);
			connect.setDoOutput(true);
			OutputStream out = connect.getOutputStream();
			out.write(netInfo.pramase.getBytes());
			out.flush();
			out.close();
			if(connect.getResponseCode() == 200){
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
								connect.getInputStream()));
				String line; 
				StringBuffer buffer = new StringBuffer();
				while((line = br.readLine())!=null){
					buffer.append(line);
				}
				br.close();
				result = buffer.toString();
				result.trim();
				result = result.replaceAll(" ", "");
			}else{
				result = "请求失败";
			}

		} catch (MalformedURLException e) {
			result = "url地址错误";
		} catch (IOException e) {
			result = "连接出错";
		}
		return result;
	}

}
