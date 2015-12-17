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
	//toplinear��ʹ��view
	private View topView;
	private TextView mAddress;
	//cenlinearʹ�õ�view
	private ExpandableListView mexpand;
	private ListView mLv;
	//bottemlinear��ʹ��view
	private View bottemView;
	private TextView mPrice;
	private Button mBtn;
	//mLv����Դ
	private List<CartListInfo>list;
	private OkOrderAdapter adapter;
	//mexpand����Դ
	private List<OrderInfo>orderList = new ArrayList<OrderInfo>();
	private OrderExpandAdapter expandAdapter;
	private String sn;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	private void initLocation(){
		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
		mLocationClient.registerLocationListener(myListener);    //ע���������
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy
				);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span=0;
		option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);//��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
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
			LOGE("��λ���:"+arg0.getAddrStr());
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
		//��������֧����ʽ������
		OrderInfo in1 = new OrderInfo();
		in1.title="֧����ʽ";
		in1.value = "����֧��";
		List<OrderChildInfo>in1list = new ArrayList<OrderChildInfo>();
		in1list.add(new OrderChildInfo("����֧��", true));
		in1list.add(new OrderChildInfo("��������", false));
		in1.list = in1list;
		orderList.add(in1);
		//-------------------------------
		OrderInfo in2 = new OrderInfo();
		in2.title="���ͷ�ʽ";
		in2.value = "��ͨ���";
		List<OrderChildInfo>in2list = new ArrayList<OrderChildInfo>();
		in2list.add(new OrderChildInfo("��ͨ���", true));
		in2list.add(new OrderChildInfo("˳��", false));
		in2.list = in2list;
		orderList.add(in2);
		//--------------------------------------
		OrderInfo in3 = new OrderInfo();
		in3.title="�ͻ�ʱ��";
		in3.value = "������";
		List<OrderChildInfo>in3list = new ArrayList<OrderChildInfo>();
		in3list.add(new OrderChildInfo("������", true));
		in3list.add(new OrderChildInfo("����", false));
		in3list.add(new OrderChildInfo("ȫ��", false));
		in3.list = in3list;
		orderList.add(in3);
		expandAdapter = new OrderExpandAdapter(orderList, this);
		mexpand.setAdapter(expandAdapter);
		//��Ӹ�OrderScrollView
		msc.setExpand(mexpand);
	}

	BaseHandler hand = new BaseHandler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what!=200){
				return;
			}
			//ȷ�϶�����ڷ���ֵ
			if(msg.arg1 == 0){
				list = (List<CartListInfo>) msg.obj;
				adapter = new OkOrderAdapter(list,
						OkOrderActivity.this);
				mLv.setAdapter(adapter);
				msc.setLv(mLv);
			}else if(msg.arg1 == 1){
				List<String> l = (List<String>) msg.obj;
				if(!l.get(0).equals("ok")){
					Toast("��������ʧ��");
					return;
				}else{
					Toast("��������"+l.get(1));
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
			//�û�ȷ�϶���->�����������->֧����֧������
			NetStrInfo info = new NetStrInfo();
			info.arg1 = 1;
			info.ctx = this;
			info.hand = hand;
			info.interfaceStr = HttpModel.CONFIRM;
			info.netFlag = 1;
			info.pramase = "parames=111";//ȷ�Ϻ�Ҫ������Ʒ->��Ʒid,��ɫ,�ߴ�,����,�û�id
			((MyApplication)getApplicationContext()).Thread_Pool.execute(new HttpThread(info));
			break;
		}
	}


	// �̻�PID
	public static final String PARTNER = "2088121049994081";
	// �̻��տ��˺�
	public static final String SELLER = "handonghai@hmxy.cc";
	// �̻�˽Կ��pkcs8��ʽ
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPCkBd5PV/blpUiVK8kffvXAxYp+GHBt+e4v1hcHnXczBMt7ZTxgrwSC3UE6eKq4JJyd69JyxVpqQT646DX1+Z+7n+29wNo6DzS2aGgrn5hMVDD1i2/808mMNkf4DFu3iZkR/+ReNX1T0xXpaXKHuLzIsFvHf9PuTL8lasLRl533AgMBAAECgYAQfm2Yiz+wQJVPoEncAzikh44CrBnouuECnmzCL1dvsCOH/vE1bjsQyBd6PRPAP9bilUBwsEUuqVHHhs0DxdRSKHezJJXQYP6qkM6s7C+JPmK39uL18FGveWpE4si0zBsr9PS8nXrfSlnxRIn8JmIC38piluin+Nbx3MfGfij7QQJBAPkBffmd/up7TkZm67nJtQ3X4gg7Bpb+z/jW1ftSYAWRU3KJBK6AT8uS9wg7eQ6VEdDZ9B47+8ZZMiSsqYY5xokCQQD3ZmFjbR3xe/1De5MT47Y57bLpC0rkXyGFI9D8osFKejS/HIMCB+I8RO30XvJirQ57+6ek+gyAdj8OU6659SB/AkAxG685lIRawDi+v7uLp5EyiroEP4fcLaLcg5ot96ACWRfpBcbLl3ilQHXBxODqFFIwK1vuvou4IY03GGpSk4BpAkEAh7qYhYbQ3bJrgUFjsI7GY8Of2zEB1obGihfbSS81olmnZI4M5elTWkNq1R3eFvrgrm6jS+SPRU8aSw7oFkeAmwJAMUyuN3OpSgzx3FxpDjhPfF0mhFt+5kSBYPtMpsGn5ogUlxWYbkFiCvKBsvWwwyYaD3MA/aM+CunVRTX8kxtsdw==";
	// ֧������Կ
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNAzD0JEnLTbnIGyrTNxRkGxDy5vijew9Xpq1r326GA96orSdKh3YnD+WTank9gQ1ahhWW7KuWCvDBHZwZOVd9FOldjwezrioMVhzlcM8qa8SZYCycvs6LH5FwelxBhCBf+bWcdW/i2fgFa99ExGkqhGYx+oxNYrsVdF9Qce0AhwIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;


	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(OkOrderActivity.this, "֧���ɹ�",
							Toast.LENGTH_SHORT).show();
				} else {
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(OkOrderActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(OkOrderActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(OkOrderActivity.this, "�����Ϊ��" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};


	/**
	 * call alipay sdk pay. ����SDK֧��
	 * 
	 */
	public void pay() {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
			.setTitle("����")
			.setMessage("��Ҫ����PARTNER | RSA_PRIVATE| SELLER")
			.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface dialoginterface, int i) {
					//
					finish();
				}
			}).show();
			return;
		}
		// ����
		String orderInfo = getOrderInfo("����", "�ò�����Ʒ��ϸ����", "0.01");

		// �Զ�����RSA ǩ��
		String sign = sign(orderInfo);
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// �����ķ���֧���������淶�Ķ�����Ϣ
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(OkOrderActivity.this);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// �����첽����
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. ��ȡSDK�汾��
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + sn+System.currentTimeMillis() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
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
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}


}
