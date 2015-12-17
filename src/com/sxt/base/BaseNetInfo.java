package com.sxt.base;

import android.content.Context;

public class BaseNetInfo {

	public String interfaceStr;//接口名称呢个
	public String pramase;//参数
	public Context ctx;//判断网络状态使用上下文
	public BaseHandler hand;//网络线程通信handler
	public int arg1;//同一界面中区分接口标识
	public int netFlag = 0;//区分post请求字符串数据(1)与图片上传(2)
}
