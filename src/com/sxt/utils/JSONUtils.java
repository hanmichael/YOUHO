package com.sxt.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sxt.activity.MyApplication;
import com.sxt.activity.R;
import com.sxt.bean.AdvertInfo;
import com.sxt.bean.BrandInfo;
import com.sxt.bean.BrandValueInfo;
import com.sxt.bean.CartListInfo;
import com.sxt.bean.ClassesInfo;
import com.sxt.bean.FollowInfo;
import com.sxt.bean.GoodsInfo;
import com.sxt.bean.HomeInfo;
import com.sxt.bean.ImgInfo;
import com.sxt.bean.ImgvaleInfo;
import com.sxt.bean.NewsInfo;
import com.sxt.bean.UserInfo;
import com.sxt.model.HttpModel;

/**
 * JSON解析工具类
 * */
public class JSONUtils {

	//共有解析入口->接口名称;返回值
	public List BuidList(String name,String value){
		List list = null;
		if(name.equals(HttpModel.ADVERTURL)){
			list = getAdvertList(value);
			return list;
		}else if(name.equals(HttpModel.FIRSTHOMEURL)){
			list = getFirstHomeList(value);
			return list;
		}else if(name.equals(HttpModel.RECOMMENDURL)){
			list = getRecommendList(value);
			return list;
		}else if(name.equals(HttpModel.BOYURL)){
			list = getBoyList(value,"boy");
			return list;
		}else if(name.equals(HttpModel.GIRLURL)){
			list = getBoyList(value,"girl");
			return list;
		}else if(name.equals(HttpModel.LIFESTYLEURL)){
			list = getBoyList(value,"life");
			return list;
		}else if(name.equals(HttpModel.HOTBRANDURL)){
			list = getHotList(value);
			return list;
		}else if(name.equals(HttpModel.ALLBRANDURL)){
			list = getAllList(value);
			return list;
		}else if(name.equals(HttpModel.NEWSLISTURL)){
			list = getNewsList(value);
			return list;
		}else if(name.equals(HttpModel.FOLLOWURL)){
			list = getFollow(value);
			return list;
		}else if(name.equals(HttpModel.BRANDVALUEURL)){
			list = getGoodsList(value);
			return list;
		}else if(name.equals(HttpModel.GOODSVALUEURL)){
			list = getValueList(value);
			return list;
		}else if(name.equals(HttpModel.ADDCARTURL)){
			list = AddCartList(value);
			return list;
		}else if(name.equals(HttpModel.CARTLISTURL)){
			list = getCartList(value);
			return list;
		}else if(name.equals(HttpModel.ADDORDER)){
			list = CreatOrder(value);
			return list;
		}else if(name.equals(HttpModel.OKORDER)){
			list = getOkOrder(value);
			return list;
		}else if(name.equals(HttpModel.CONFIRM)){
			list = CreateOrderSn(value);
			return list;
		}else if(name.equals(HttpModel.SIGNURL)){
			list = SignInfo(value);
			return list;
		}
		return list;
	}

	//解析首页广告界面数据
	public List getAdvertList(String result){
		List list = new ArrayList();
		try {
			JSONArray jay  = new JSONArray(result);
			for(int i = 0;i < jay.length();i ++){
				JSONObject job = (JSONObject) jay.get(i);
				AdvertInfo info = new AdvertInfo();
				info._id = job.getString("_id");
				info.advertId = job.getString("advertId");
				info.imgpath =  job.getString("imgpath");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析首页内容
	private List getFirstHomeList(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String brand = job.getString("brand");
			String men = job.getString("men");
			String menpants = job.getString("menpants");
			String accessories = job.getString("accessories");
			String other = job.getString("other");
			JSONArray brandjay = new JSONArray(brand);
			JSONArray menjay = new JSONArray(men);
			JSONArray menpantsjay = new JSONArray(menpants);
			JSONArray accessoriesjay = new JSONArray(accessories);
			JSONArray otherjay = new JSONArray(other);
			String []titleArr = MyApplication.ctx.getResources().getStringArray(R.array.homepager_title);
			HomeInfo brandinfo = new HomeInfo();
			brandinfo.Title = titleArr[0];
			brandinfo.brandList = getHomeArr(brandjay);
			list.add(brandinfo);
			HomeInfo meninfo = new HomeInfo();
			meninfo.Title = titleArr[1];
			meninfo.brandList = getHomeArr(menjay);
			list.add(meninfo);
			HomeInfo menpantsinfo = new HomeInfo();
			menpantsinfo.Title = titleArr[2];
			menpantsinfo.brandList = getHomeArr(menpantsjay);
			list.add(menpantsinfo);
			HomeInfo accessoriesinfo = new HomeInfo();
			accessoriesinfo.Title = titleArr[3];
			accessoriesinfo.brandList = getHomeArr(accessoriesjay);
			list.add(accessoriesinfo);
			HomeInfo otherinfo = new HomeInfo();
			otherinfo.Title = titleArr[4];
			otherinfo.brandList = getHomeArr(otherjay);
			list.add(otherinfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析首页中二级jsonarray方法
	private List getHomeArr(JSONArray jay){
		List<BrandInfo>list = new ArrayList<BrandInfo>();
		for(int i = 0;i < jay.length();i ++){
			BrandInfo info = new BrandInfo();
			JSONObject job;
			try {
				job = (JSONObject) jay.get(i);
				info._id = job.getString("_id");
				info.hotflag = job.getString("hotflag");
				info.imgpath = job.getString("imgpath");
				info.letter = job.getString("letter");
				info.name = job.getString("name");
				info.shopId = job.getString("shopId");
				info.value = job.getString("value");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(info);
		}
		return list;
	}

	//解析首页首页推荐商品
	private List getRecommendList(String result){
		List list = new ArrayList();
		return list;
	}

	//解析品类男生数据
	private List getBoyList(String result,String name){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String boyStr = job.getString(name);
			JSONArray jay = new JSONArray(boyStr);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				ClassesInfo info = new ClassesInfo();
				info._id = ob.getString("_id");
				info.name = ob.getString("name");
				info.SexId = ob.getString("SexId");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析热门品牌方法
	private List getHotList(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String brand = job.getString("brand");
			JSONArray jay = new JSONArray(brand);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				BrandInfo info = new BrandInfo();
				info._id = ob.getString("_id");
				info.hotflag = ob.getString("hotflag");
				info.imgpath = ob.getString("imgpath");
				info.letter = ob.getString("letter");
				info.name = ob.getString("name");
				info.value = ob.getString("value");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	//解析所有品牌方法
	private List getAllList(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String brand = job.getString("brand");
			JSONArray jay = new JSONArray(brand);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				BrandInfo info = new BrandInfo();
				info._id = ob.getString("_id");
				info.hotflag = ob.getString("hotflag");
				info.letter = ob.getString("letter");
				info.name = ob.getString("name");
				info.value = ob.getString("value");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析逛数据
	private List getNewsList(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String news = job.getString("news");
			JSONArray jay = new JSONArray(news);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				NewsInfo info = new NewsInfo();
				info.imgpath = ob.getString("imgpath");
				info.NewsId = ob.getString("NewsId");
				info.time = ob.getString("time");
				info.title = ob.getString("title");
				info.url = ob.getString("url");
				info.value = ob.getString("value");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析关注数据
	private List getFollow(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String follow = job.getString("follow");
			JSONArray jay = new JSONArray(follow);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				FollowInfo info = new FollowInfo();
				info.brandimg = ob.getString("brandimg");
				info.brandname = ob.getString("brandname");
				String goods = ob.getString("goods");
				List<GoodsInfo>goodsList = new ArrayList<GoodsInfo>();
				JSONArray ay = new JSONArray(goods);
				for(int j = 0;j < ay.length();j ++){
					JSONObject goodsJob = (JSONObject) ay.get(j);
					GoodsInfo gInfo = new GoodsInfo();
					gInfo._id = goodsJob.getString("_id");
					gInfo.distance = goodsJob.getString("distance");
					gInfo.goodsimg = goodsJob.getString("goodsimg");
					gInfo.price = goodsJob.getString("price");
					goodsList.add(gInfo);
				}
				info.goodsList = goodsList;
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析品牌下商品列表
	private List getGoodsList(String result){
		List<BrandValueInfo> list = new ArrayList<BrandValueInfo>();
		try {
			JSONObject job = new JSONObject(result);
			BrandValueInfo info = new BrandValueInfo();
			info.name = job.getString("brandname");
			info.value = job.getString("brandvalue");
			String goods = job.getString("goods");
			JSONArray jay = new JSONArray(goods);
			List<GoodsInfo>gList = new ArrayList<GoodsInfo>();
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				GoodsInfo goodsInfo = new GoodsInfo();
				goodsInfo._id = ob.getString("_id");
				goodsInfo.distance = ob.getString("discount");
				goodsInfo.goodsimg = ob.getString("imgpath");
				goodsInfo.GoodsUrl = ob.getString("GoodsUrl");
				goodsInfo.price = ob.getString("price");
				goodsInfo.title = ob.getString("title");
				gList.add(goodsInfo);
			}
			info.list = gList;
			list.add(info);
		} catch (JSONException e) {
			Log.e("","解析品牌商品列表出错");
		}
		return list;
	}

	//解析商品详情方法
	private List getValueList(String result){
		List<GoodsInfo>list = new ArrayList<GoodsInfo>();
		try {
			JSONObject job = new JSONObject(result);
			String Goods = job.getString("goods");
			String Img = job.getString("img");
			String ImgValue = job.getString("imgvale"); 
			GoodsInfo info = new GoodsInfo();
			JSONArray jay = new JSONArray(Goods);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				info._id = ob.getString("_id");
				info.title = ob.getString("title");
				info.price = ob.getString("price");
				info.ShopId = ob.getString("ShopId");
				info.GoodsUrl = ob.getString("GoodsUrl");
				info.time = ob.getString("time");
				info.distance = ob.getString("discount");
			}
			JSONArray imgJay = new JSONArray(Img);
			List<ImgInfo>imgList = new ArrayList<ImgInfo>();
			for(int i = 0;i < imgJay.length();i ++){
				JSONObject ob = (JSONObject) imgJay.get(i);
				ImgInfo imgInfo = new ImgInfo();
				imgInfo._id = ob.getString("_id");
				imgInfo.goodsId = ob.getString("goodsId");
				imgInfo.imgpath = ob.getString("imgpath");
				imgList.add(imgInfo);
			}
			JSONArray valueJay = new JSONArray(ImgValue);
			List<ImgvaleInfo>valueList = new ArrayList<ImgvaleInfo>();
			for(int i = 0;i < valueJay.length();i++){
				JSONObject ob = (JSONObject) valueJay.get(i);
				ImgvaleInfo vinfo = new ImgvaleInfo();
				vinfo._id = ob.getString("_id");
				vinfo.goodsId = ob.getString("goodsId");
				vinfo.imgpath = ob.getString("imgpath");
				valueList.add(vinfo);
			}
			info.imgList = imgList;
			info.valueList = valueList;
			list.add(info);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//解析添加购物车
	private List AddCartList(String result){
		/**
		 * {"scuess":"ok"}
		 * */
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String scuess = job.getString("scuess");
			list.add(scuess);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	//解析购物车列表
	public List getCartList(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String cart = job.getString("cart");
			JSONArray jay = new JSONArray(cart);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				CartListInfo info = new CartListInfo();
				info.color = ob.getString("color");
				info.flag = true;
				info.imgpath = ob.getString("imgpath");
				info.num = ob.getString("num");
				info.price = ob.getString("price");
				info.size = ob.getString("size");
				info.title = ob.getString("title");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	//创建订单解析
	private List CreatOrder(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String scuess = job.getString("scuess");
			list.add(scuess);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	} 

	//解析确认订单界面
	private List getOkOrder(String result){
		List<CartListInfo>list= new ArrayList<CartListInfo>();
		try {
			JSONObject job = new JSONObject(result);
			String order = job.getString("order");
			JSONArray jay = new JSONArray(order);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob = (JSONObject) jay.get(i);
				CartListInfo info = new CartListInfo();
				info.color = ob.getString("color");
				info.imgpath = ob.getString("imgpath");
				info.num = ob.getString("num");
				info.price = ob.getString("price");
				info.size = ob.getString("size");
				info.title = ob.getString("title");
				list.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	//生成订单编号解析->{"scuess":"ok","ordernum":"3212313213213"}
	private List CreateOrderSn(String result){
		List list = new ArrayList();
		try {
			JSONObject job = new JSONObject(result);
			String scuess = job.getString("scuess");
			list.add(scuess);
			if(scuess.equals("ok"))
				list.add(job.getString("ordernum"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	//解析用户登录返回值
	private List SignInfo(String result){
		List<UserInfo>list = new ArrayList<UserInfo>();
		try {
			JSONObject job = new JSONObject(result);
			String users = job.getString("users");
			JSONArray jay = new JSONArray(users);
			for(int i = 0;i < jay.length();i ++){
				JSONObject ob  = (JSONObject) jay.get(i);
				UserInfo info = new UserInfo();
				info.id = ob.getString("UserId");
				info.NickName = ob.getString("NickName");
				info.PassWod = ob.getString("PassWod");
				info.UserBirthday = ob.getString("UserBirthday");
				info.UserHead = ob.getString("UserHead");
				info.UserName = ob.getString("UserName");
				info.UserSex = ob.getString("UserSex");
				list.add(info);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
