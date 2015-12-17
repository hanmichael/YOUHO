package com.sxt.activity;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class CameraActivity extends Activity {

	File fial;
	String imgPath;
	Uri uri =null;
	private Bitmap bitmap;
	String sdStatus=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imgPath = this.getExternalCacheDir().getAbsolutePath()+"/hehe.jpg";
		sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(it,1);
		}else{
			File vFile = new File(imgPath);
			if(!vFile.exists())
			{
				File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
				vDirPath.mkdirs();
			}
			Uri uri = Uri.fromFile(vFile);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
			startActivityForResult(intent, 0);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK){
			fial=new File(imgPath);
			uri=Uri.fromFile(fial);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int degree = readPictureDegree(fial.getAbsolutePath()); 
			if (null != bitmap && bitmap.isRecycled() == false) {
				bitmap.recycle();//制空bitmap C引用区域
				bitmap=null;
			}
			if (uri != null) {
				bitmap = BitmapFactory.decodeFile(uri.getPath(),
						options);
				bitmap=rotaingImageView(degree,bitmap);
			}
			if(bitmap != null){
				Log.e("",""+bitmap.getWidth());
				((MyApplication)getApplicationContext()).mheadBitmap = bitmap;
			}
		}else{
			if (data != null) {
				Uri uri = data.getData();
				if (null == uri || uri.getPath() != null) {
					try {
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 4;
						options.inPreferredConfig = Bitmap.Config.RGB_565;
						options.inPurgeable = true;
						options.inInputShareable = true;
						if (null != bitmap && bitmap.isRecycled() == false) {
							bitmap.recycle();
						}
						if (uri != null) {
							int degree = readPictureDegree(fial.getAbsolutePath()); 
							bitmap = BitmapFactory.decodeFile(uri.getPath(),
									options);
							bitmap=rotaingImageView(degree,bitmap);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (bitmap == null) {
						Bundle bundle = data.getExtras();
						if (bundle != null) {
							bitmap = (Bitmap) bundle.get("data");
						}
					}
					if(bitmap != null)
					{
						Log.e("",""+bitmap.getWidth());
						((MyApplication)getApplicationContext()).mheadBitmap = bitmap;
					}
				}
			}
		}
	}

	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {  
		//旋转图片 动作  
		Matrix matrix = new Matrix();;  
		matrix.postRotate(angle);  
		// 创建新的图片  
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
		return resizedBitmap;  
	}

	/** 
	 * 读取图片属性：旋转的角度 
	 * @param path 图片绝对路径 
	 * @return degree旋转的角度 
	 */  
	public static int readPictureDegree(String path) {  
		int degree  = 0;  
		try {  
			ExifInterface exifInterface = new ExifInterface(path);  
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
			switch (orientation) {  
			case ExifInterface.ORIENTATION_ROTATE_90:  
				degree = 90;  
				break;  
			case ExifInterface.ORIENTATION_ROTATE_180:  
				degree = 180;  
				break;  
			case ExifInterface.ORIENTATION_ROTATE_270:  
				degree = 270;  
				break;  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return degree;  
	} 

}
