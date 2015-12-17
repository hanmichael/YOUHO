package com.sxt.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.sxt.adapter.OkOrderAdapter;
import com.sxt.adapter.OrderExpandAdapter;
import com.sxt.base.BaseActivity;
import com.sxt.base.BaseHandler;
import com.sxt.bean.CartListInfo;
import com.sxt.bean.NetStrInfo;
import com.sxt.bean.OrderChildInfo;
import com.sxt.bean.OrderInfo;
import com.sxt.model.HttpModel;
import com.sxt.thread.HttpThread;
import com.sxt.utils.pay.PayResult;
import com.sxt.utils.pay.SignUtils;
import com.sxt.view.OrderScrollView;

public class OkOrderActivity extends BaseActivity implements 
OnClickListener{

	private ImageView img;
	private OrderScrollView msc;
	//toplinear中使用view
	private View topView;
	private TextView mAddress;
	//cenlinear使用的view
	private ExpandableListView mexpand;
	private ListView mLv;
	//bottemlinear中使用view
	private View bottemView;
	private TextView mPrice;
	private Button mBtn;
	//mLv数据源
	private List<CartListInfo>list;
	private OkOrderAdapter adapter;
	//mexpand数据源
	private List<OrderInfo>orderList = new ArrayList<OrderInfo>();
	private OrderExpandAdapter expandAdapter;
	private String sn;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	private void initLocation(){
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener(myListener);    //注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy
				);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=0;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	protected void onDestroy() {
		mLocationClient.stop();
		super.onDestroy();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			LOGE("定位结果:"+arg0.getAddrStr());
			mAddress.setText(arg0.getAddrStr());
		}

	}

	@Override
	protected void initView() {
		super.initView();
		create(R.layout.activity_okorder);
		img = (ImageView) f(R.id.OkOrderAct_BackImg);
		msc = (OrderScrollView) f(R.id.OkOrderAct_Sc);
		//top
		topView = View.inflate(this, R.layout.view_ordertop, null);
		mAddress = (TextView) topView.findViewById(R.id.OrderTop_AddressTv);
		//bottem
		bottemView = View.inflate(this, R.layout.view_orderbottem, null);
		mPrice = (TextView) bottemView.findViewById(R.id.OrderBottem_Money);
		mBtn = (Button) bottemView.findViewById(R.id.OrderBottem_OkBtn);
		//center
		mexpand = new ExpandableListView(this);
		mexpand.setGroupIndicator(null);
		mLv = new ListView(this);
		img.setOnClickListener(this);
		mBtn.setOnClickListener(this);
		msc.setTopView(topView);
		msc.setBottemView(bottemView);
	}

	@Override
	protected void initList() {
		super.initList();
		initLocation();
		NetStrInfo info = new NetStrInfo();
		info.arg1 = 0;
		info.ctx = this;
		info.hand = hand;
		info.interfaceStr = HttpModel.OKORDER;
		info.netFlag = 1;
		info.pramase = "parames={\"userid\":\""+((MyApplication)getApplicationContext()).userInfo.id+"\"}";
		((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
		//创建虚拟支付方式等内容
		OrderInfo in1 = new OrderInfo();
		in1.title="支付方式";
		in1.value = "在线支付";
		List<OrderChildInfo>in1list = new ArrayList<OrderChildInfo>();
		in1list.add(new OrderChildInfo("在线支付", true));
		in1list.add(new OrderChildInfo("货到付款", false));
		in1.list = in1list;
		orderList.add(in1);
		//-------------------------------
		OrderInfo in2 = new OrderInfo();
		in2.title="配送方式";
		in2.value = "普通快递";
		List<OrderChildInfo>in2list = new ArrayList<OrderChildInfo>();
		in2list.add(new OrderChildInfo("普通快递", true));
		in2list.add(new OrderChildInfo("顺风", false));
		in2.list = in2list;
		orderList.add(in2);
		//--------------------------------------
		OrderInfo in3 = new OrderInfo();
		in3.title="送货时间";
		in3.value = "工作日";
		List<OrderChildInfo>in3list = new ArrayList<OrderChildInfo>();
		in3list.add(new OrderChildInfo("工作日", true));
		in3list.add(new OrderChildInfo("假期", false));
		in3list.add(new OrderChildInfo("全天", false));
		in3.list = in3list;
		orderList.add(in3);
		expandAdapter = new OrderExpandAdapter(orderList, this);
		mexpand.setAdapter(expandAdapter);
		//添加给OrderScrollView
		msc.setExpand(mexpand);
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what!=200){
				return;
			}
			//确认订单借口返回值
			if(msg.arg1 == 0){
				list = (List<CartListInfo>) msg.obj;
				adapter = new OkOrderAdapter(list,
						OkOrderActivity.this);
				mLv.setAdapter(adapter);
				msc.setLv(mLv);
			}else if(msg.arg1 == 1){
				List<String> l = (List<String>) msg.obj;
				if(!l.get(0).equals("ok")){
					Toast("订单生成失败");
					return;
				}else{
					Toast("订单生成"+l.get(1));
					sn = l.get(1);
					pay();
				}
			}

		};
	};

	@Override
	public void onClick(View arg0) {
		int ID = arg0.getId();
		switch(ID){
		case R.id.OkOrderAct_BackImg:
			finish();
			break;
		case R.id.OrderBottem_OkBtn:
			//用户确认订单->产生付款订单号->支付宝支付条件
			NetStrInfo info = new NetStrInfo();
			info.arg1 = 1;
			info.ctx = this;
			info.hand = hand;
			info.interfaceStr = HttpModel.CONFIRM;
			info.netFlag = 1;
			info.pramase = "parames=111";//确认后要购买商品->商品id,颜色,尺寸,数量,用户id
			((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
			break;
		}
	}


	// 商户PID
	public static final String PARTNER = "2088121049994081";
	// 商户收款账号
	public static final String SELLER = "handonghai@hmxy.cc";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPCkBd5PV/blpUiVK8kffvXAxYp+GHBt+e4v1hcHnXczBMt7ZTxgrwSC3UE6eKq4JJyd69JyxVpqQT646DX1+Z+7n+29wNo6DzS2aGgrn5hMVDD1i2/808mMNkf4DFu3iZkR/+ReNX1T0xXpaXKHuLzIsFvHf9PuTL8lasLRl533AgMBAAECgYAQfm2Yiz+wQJVPoEncAzikh44CrBnouuECnmzCL1dvsCOH/vE1bjsQyBd6PRPAP9bilUBwsEUuqVHHhs0DxdRSKHezJJXQYP6qkM6s7C+JPmK39uL18FGveWpE4si0zBsr9PS8nXrfSlnxRIn8JmIC38piluin+Nbx3MfGfij7QQJBAPkBffmd/up7TkZm67nJtQ3X4gg7Bpb+z/jW1ftSYAWRU3KJBK6AT8uS9wg7eQ6VEdDZ9B47+8ZZMiSsqYY5xokCQQD3ZmFjbR3xe/1De5MT47Y57bLpC0rkXyGFI9D8osFKejS/HIMCB+I8RO30XvJirQ57+6ek+gyAdj8OU6659SB/AkAxG685lIRawDi+v7uLp5EyiroEP4fcLaLcg5ot96ACWRfpBcbLl3ilQHXBxODqFFIwK1vuvou4IY03GGpSk4BpAkEAh7qYhYbQ3bJrgUFjsI7GY8Of2zEB1obGihfbSS81olmnZI4M5elTWkNq1R3eFvrgrm6jS+SPRU8aSw7oFkeAmwJAMUyuN3OpSgzx3FxpDjhPfF0mhFt+5kSBYPtMpsGn5ogUlxWYbkFiCvKBsvWwwyYaD3MA/aM+CunVRTX8kxtsdw==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNAzD0JEnLTbnIGyrTNxRkGxDy5vijew9Xpq1r326GA96orSdKh3YnD+WTank9gQ1ahhWW7KuWCvDBHZwZOVd9FOldjwezrioMVhzlcM8qa8SZYCycvs6LH5FwelxBhCBf+bWcdW/i2fgFa99ExGkqhGYx+oxNYrsVdF9Qce0AhwIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;


	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(OkOrderActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(OkOrderActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(OkOrderActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(OkOrderActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};


	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
			.setTitle("警告")
			.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface dialoginterface, int i) {
					//
					finish();
				}
			}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo("测试", "该测试商品详细描述", "0.01");

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(OkOrderActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + sn+System.currentTimeMillis() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}


}
