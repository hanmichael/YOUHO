package com.sxt.utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

/**
 * 图片转换为字符串工具类
 * */
public class BitToStrUtils {

	/**
	 * 图片转字符串方法
	 * */
	public String toStr(Bitmap bitmap){
		String result = null;
		//图片转化为字节数组
		byte[] buffer;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, out);
		buffer = out.toByteArray();
		result = Base64.encodeToString(buffer, Base64.DEFAULT);
		return result;
	}

}
