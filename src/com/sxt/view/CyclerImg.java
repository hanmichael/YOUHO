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
 * ����Բ��ͼƬ������
 * */

public class CyclerImg extends ImageView{

	public CyclerImg(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private Paint paint;
	private Bitmap MaskBit;//�ɰ�ͼƬ

	@Override
	protected void onDraw(Canvas canvas) {
		//��ȡimageviewsrc��ӦͼƬ
		Drawable oldDrawable = getDrawable();
		if(oldDrawable == null)
			return;
		//��ʼ������
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);//�����
		paint.setFilterBitmap(false);//�������˲�����
		PorterDuffXfermode mode = new PorterDuffXfermode(Mode.DST_IN);
		//��ͼ���ȡ������������ʾ�²�ͼ������
		paint.setXfermode(mode);//���û���ģʽ
		//canvas�ֲ�
		//ALL_SAVE_FLAG->ͼ��ظ�ʱ�ָ���������
		canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
		oldDrawable.setBounds(0, 0, getWidth(), getHeight());
		oldDrawable.draw(canvas);//����������ͼƬ���Ƶ�canvas
		//�����ϲ�Բ���ɰ�
		// ��һ�ν�����ɰ�ͼƬ��ϵͳ����->�������ɰ�
		if(MaskBit == null||MaskBit.isRecycled()){
			MaskBit = createMask();
		}
		//�����ϲ�ͼƬ
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
		//��ȡ��ɫ��ԲͼƬ
		return bitmap;
	}

}
