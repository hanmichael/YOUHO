package com.sxt.activity;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoAct  extends Activity{

	private Bitmap bm = null;
	private Intent date = null;
	Uri uri =null;
	String sdStatus=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 0);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
		if (requestCode == 0) {
			date = data;
			if (date != null) {
				Uri originalUri = data.getData(); // 获得图片的uri
				if (originalUri.getPath().toString() != null) {
					try {
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 1;
						options.inPreferredConfig = Bitmap.Config.RGB_565;
						options.inPurgeable = true;
						options.inInputShareable = true;

						if (null != bm && bm.isRecycled() == false) {
							bm.recycle();
						}
						bm = BitmapFactory.decodeStream(resolver
								.openInputStream(Uri.parse(originalUri
										.toString())), null, options);
					} catch (Exception e) {
						e.printStackTrace();
					} // 显得到bitmap图片
					if (bm != null) {
						((MyApplication)getApplicationContext()).mheadBitmap = bm;
					} else {
						Toast.makeText(PhotoAct.this, "图片获取失败", 1).show();
					}
				} else {
					Toast.makeText(PhotoAct.this, "图片获取失败", 1).show();
				}
			} else {
				PhotoAct.this.finish();
			}
		}
	}

}
