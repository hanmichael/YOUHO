package com.sxt.utils.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ����������ʹ�õ�frame����
 * */
public class FrameUtils {

	private Context ctx;//�����Ļ�ȡ��Դ��ͼƬʹ��
	private int[]arr;//������ͼƬ��Դ
	private ImageView img;//��ʾ�����Ĵ���
	private int time = 50;//ÿһ֡��ʾʱ��
	private AnimationDrawable frame = new AnimationDrawable();

	//������������
	public void create(Context ctx,int[]arr,ImageView img){
		this.arr = arr;
		this.ctx = ctx;
		this.img = img;
		//ѭ������ͼƬ��Դ��ӵ�frame������
		for(int i = 0;i < this.arr.length;i ++){
			Drawable d =  ctx.getResources().getDrawable(arr[i]);
			frame.addFrame(d, time);
		} 
		frame.setOneShot(false);
		this.img.setImageDrawable(frame);
	}
	//��ʼ��������
	public void startFrame(){
		frame.start();
	}
	//ֹͣ��������
	public void stopFrame(){
		frame.stop();
	}
}
