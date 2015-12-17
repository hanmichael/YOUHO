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
 * ��ĸ��������
 * */
public class LetterView extends View{

	//�Զ���view�Ŀ����
	private int Width = 0;
	private int Height = 0;
	private int textsize = 20;//���ִ�С
	private int textspec = 5;//���ּ��
	private int topY = 0;//��ǰҪ��������y��λ��
	private List<String>list = new ArrayList<String>();//������ĸ����Դ
	private int index = -1;//ѡ��list������Դ������
	private Paint paint;
	private LetterLissener call;

	public LetterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	//��ȡ�ؼ����
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Width = getMeasuredWidth();
		Height = getMeasuredHeight();
	}
	//��������
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//		textsize = Width/2;
		//��������Դʵ�ֻ��ƹ���
		for(int i = 0;i < list.size();i ++){
			paint.reset();
			textsize = Width/2;
			paint.setTextSize(textsize);
			paint.setTextAlign(Align.CENTER);
			//�������ָ�����ʾ->index
			if(index == i)
				paint.setColor(Color.BLACK);
			else
				paint.setColor(Color.GRAY);
			//�������ֵ�y��λ�û�ȡ
			topY = (textsize + textspec)*(i+1);
			canvas.drawText(list.get(i), Width/2, topY, paint);
		}
	}

	//��ָ�����Լ�����
	//��ǰ��ָ����y����/(���ִ�С+�ָ��߶�)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int y = (int) event.getY();//��ָ���µ�
			index = y/(textsize+textspec);//��ָѡ�е���ĸ
			//ˢ����ͼ
			invalidate();
			//���ݻ�fragment����ѡ���Ǹ���ĸ
			call.call(index);
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			//��ָ������
			int y  = (int) event.getY();
			int i = y/(textsize+textspec);//��ǰ����
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

	//��������Դ����
	public void setList(List<String>list){
		this.list = list;
		//ˢ�½���
		invalidate();
	}

	//���ü���
	public void setCall(LetterLissener call){
		this.call = call;
	}
}
