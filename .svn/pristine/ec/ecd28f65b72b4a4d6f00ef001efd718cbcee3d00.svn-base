package com.sxt.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.sxt.base.BaseActivity;

public class WebActivity extends BaseActivity{

	private ImageView img;
	private WebView mWeb;
	private String url;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		create(R.layout.activity_web);
		url = getIntent().getStringExtra("u");
		img = (ImageView) f(R.id.WebAct_BackImg);
		mWeb = (WebView) f(R.id.Web);
		mWeb.getSettings().setJavaScriptEnabled(true);
		mWeb.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//url->webview要打开地址
				mWeb.loadUrl(url);
				return true;
			}
		});
		mWeb.loadUrl(url);
		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();				
			}
		});

	}

	@Override
	protected void initList() {
		// TODO Auto-generated method stub
		super.initList();
	}

}
