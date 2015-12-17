package com.sxt.utils.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 创建程序中使用的frame动画
 * */
public class FrameUtils {

	private Context ctx;//上下文获取资源中图片使用
	private int[]arr;//动画中图片资源
	private ImageView img;//显示动画的窗口
	private int time = 50;//每一帧显示时间
	private AnimationDrawable frame = new AnimationDrawable();

	//创建动画方法
	public void create(Context ctx,int[]arr,ImageView img){
		this.arr = arr;
		this.ctx = ctx;
		this.img = img;
		//循环遍历图片资源添加到frame动画中
		for(int i = 0;i < this.arr.length;i ++){
			Drawable d =  ctx.getResources().getDrawable(arr[i]);
			frame.addFrame(d, time);
		} 
		frame.setOneShot(false);
		this.img.setImageDrawable(frame);
	}
	//开始动画方法
	public void startFrame(){
		frame.start();
	}
	//停止动画方法
	public void stopFrame(){
		frame.stop();
	}
}
