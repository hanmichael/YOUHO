package com.sxt.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;


/**
 * ���ش洢������
 *           ->mnt/sdcard/android/data/data/����/
 * */
public class FileUtils {

	private Context ctx;

	public FileUtils(Context ctx){
		this.ctx = ctx;
	}

	//SD���ж�->sd�����ط���true��֮����false
	private boolean IsSDCard(){
		boolean flag = false;
		String state = Environment.getExternalStorageState();//sd��״̬
		if(state.equals(Environment.MEDIA_MOUNTED)){
			flag = true;
			return flag;
		}
		return flag;
	}

	//��ȡ���뱾�ص�ַ
	private String getSDFile(){
		String result = null;
		if(IsSDCard()){
			result = ctx.getExternalCacheDir().getAbsolutePath();
		}
		return result;
	}

	//д�뱾��(string)
	//����ļ����Լ�����
	public void WriteStr(String name,String value){
		if(!IsSDCard()){
			return;
		}
		try {
			FileOutputStream out = new FileOutputStream(getSDFile()+"/"+name+".txt");
			out.write(value.getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//���ض�ȡ(string)
	public String ReadStr(String name){
		String result = null;
		if(!IsSDCard()){
			return null;
		}
		try {
			FileInputStream in = new FileInputStream(getSDFile()+"/"+name+".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			StringBuffer buffer = new StringBuffer();
			while((line = br.readLine())!=null){
				buffer.append(line);
			}
			br.close();
			result = buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	//ͼƬ���ر���
	public void WriteBitmap(String name,Bitmap bitmap){
		if(!IsSDCard()){
			return;
		}
		try {
			FileOutputStream out = new FileOutputStream(getSDFile()+"/"+name);
			ByteArrayOutputStream byOut = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, byOut);
			out.write(byOut.toByteArray());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//ͼƬ��ȡ
	public Bitmap ReadBitmap(String name){
		if(!IsSDCard()){
			return null;
		}
		Bitmap bit = null;
		bit = BitmapFactory.decodeFile(getSDFile()+"/"+name);
		return bit;
	}
}

