package com.sxt.utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

/**
 * ͼƬת��Ϊ�ַ���������
 * */
public class BitToStrUtils {

	/**
	 * ͼƬת�ַ�������
	 * */
	public String toStr(Bitmap bitmap){
		String result = null;
		//ͼƬת��Ϊ�ֽ�����
		byte[] buffer;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, out);
		buffer = out.toByteArray();
		result = Base64.encodeToString(buffer, Base64.DEFAULT);
		return result;
	}

}
