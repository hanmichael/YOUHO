package com.sxt.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

/**
 * 针对界面中顶部图标底部文字设置
 * */
public class IconView extends View{

	private String str;//显示的底部文字
	private Bitmap bitmap;//顶部显示图片
	private int TextSize = 16;
	private int TextColor = Color.BLACK;
	private int Width = 0;
	private int Height = 0;
	private Paint paint;

	public IconView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//获取控件宽高
		Width = getMeasuredWidth();
		Height = getMeasuredHeight();
	}

	//设置显示内容方法
	public void setText(String str){
		this.str = str;
		invalidate();
	}

	public void setImg(Bitmap bitmap){
		this.bitmap = bitmap;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(str == null){
			return;			
		}
		if(bitmap == null){
			return;
		}
		//绘制文字
		paint.setColor(TextColor);
		paint.setTextSize(TextSize);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(str,Width/2, (float)((Height-TextSize)), paint);
		canvas.drawBitmap(bitmap, (Width-bitmap.getWidth())/2, 0, null);
	}
}
