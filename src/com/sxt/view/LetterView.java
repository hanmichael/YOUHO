package com.sxt.view;

import java.util.ArrayList;
import java.util.List;

import com.sxt.callback.LetterLissener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 字母索引界面
 * */
public class LetterView extends View{

	//自定义view的宽与高
	private int Width = 0;
	private int Height = 0;
	private int textsize = 20;//文字大小
	private int textspec = 5;//文字间距
	private int topY = 0;//当前要绘制文字y轴位置
	private List<String>list = new ArrayList<String>();//索引字母数据源
	private int index = -1;//选中list、数据源的索引
	private Paint paint;
	private LetterLissener call;

	public LetterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	//获取控件宽高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Width = getMeasuredWidth();
		Height = getMeasuredHeight();
	}
	//绘制文字
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//		textsize = Width/2;
		//遍历数据源实现绘制功能
		for(int i = 0;i < list.size();i ++){
			paint.reset();
			textsize = Width/2;
			paint.setTextSize(textsize);
			paint.setTextAlign(Align.CENTER);
			//区分文字高亮显示->index
			if(index == i)
				paint.setColor(Color.BLACK);
			else
				paint.setColor(Color.GRAY);
			//绘制文字的y轴位置获取
			topY = (textsize + textspec)*(i+1);
			canvas.drawText(list.get(i), Width/2, topY, paint);
		}
	}

	//手指按下以及滑动
	//当前手指所在y坐标/(文字大小+分个高度)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int y = (int) event.getY();//手指按下点
			index = y/(textsize+textspec);//手指选中的字母
			//刷新试图
			invalidate();
			//传递回fragment我们选中那个字母
			call.call(index);
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			//手指长按锁
			int y  = (int) event.getY();
			int i = y/(textsize+textspec);//当前索引
			if(i!=index){
				index = i;
				call.call(index);
				invalidate();
			}
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			index = -1;
			invalidate();
		}
		return true;
	}

	//设置数据源方法
	public void setList(List<String>list){
		this.list = list;
		//刷新界面
		invalidate();
	}

	//设置监听
	public void setCall(LetterLissener call){
		this.call = call;
	}
}
