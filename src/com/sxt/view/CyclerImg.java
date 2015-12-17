package com.sxt.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 处理圆角图片工具类
 * */

public class CyclerImg extends ImageView{

	public CyclerImg(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private Paint paint;
	private Bitmap MaskBit;//蒙版图片

	@Override
	protected void onDraw(Canvas canvas) {
		//获取imageviewsrc对应图片
		Drawable oldDrawable = getDrawable();
		if(oldDrawable == null)
			return;
		//初始化画笔
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
		paint.setFilterBitmap(false);//不进行滤波处理
		PorterDuffXfermode mode = new PorterDuffXfermode(Mode.DST_IN);
		//多图层获取公共部分切显示下层图层内容
		paint.setXfermode(mode);//设置画笔模式
		//canvas分层
		//ALL_SAVE_FLAG->图层回复时恢复所有属性
		canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
		oldDrawable.setBounds(0, 0, getWidth(), getHeight());
		oldDrawable.draw(canvas);//背景正方形图片绘制到canvas
		//绘制上层圆形蒙版
		// 第一次进入或蒙版图片被系统回收->创建新蒙版
		if(MaskBit == null||MaskBit.isRecycled()){
			MaskBit = createMask();
		}
		//绘制上层图片
		canvas.drawBitmap(MaskBit, 0, 0, paint);
		//		canvas.restoreToCount(i);
	}

	private Bitmap createMask(){
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		RectF rf = new RectF(0, 0, getWidth(), getHeight());
		canvas.drawOval(rf, paint);//
		//获取白色椭圆图片
		return bitmap;
	}

}
