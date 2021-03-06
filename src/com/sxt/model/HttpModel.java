package com.sxt.model;

public class HttpModel {

	public static String HTTPURL = "http://www.wwtliu.com/School_Sky/";
	public static String IMGURL = "http://www.wwtliu.com/School_Sky/Img/";
	//获取首页广告接口
	//参数:""
	/**返回值:
	 * [{"_id":"1","imgpath":"top1.jpg",
	 * "goodsId":null,"UserId":null,
	 * "NewsId":null,"advertId":"1",
	 * "shopId":null},{"_id":"2","imgpath":"top2.jpg","goodsId":null,"UserId":null,"NewsId":null,"advertId":"2","shopId":null},{"_id":"3","imgpath":"top3.jpg","goodsId":null,"UserId":null,"NewsId":null,"advertId":"3","shopId":null},{"_id":"4","imgpath":"top4.jpg","goodsId":null,"UserId":null,"NewsId":null,"advertId":"4","shopId":null},{"_id":"5","imgpath":"top5.jpg","goodsId":null,"UserId":null,"NewsId":null,"advertId":"5","shopId":null}]
	 * */
	public static String ADVERTURL = "yohoadvert.php";
	//获取首页内容接口
	/**
	 * 参数：parames={\"shop\":\"1\"}
	 * shop->男女潮童类型
	 * */
	public static String FIRSTHOMEURL = "homepager.php";
	//获取首页推荐商品接口
	/**
	 * 参数：parames={\"page\":\""+page+"\"}
	 * */
	public static String RECOMMENDURL = "recommend.php";
	//分类页面boy接口
	/**
	 * 参数:""
	 * 
	 * */
	public static String BOYURL = "categoryboy.php";
	//分类页面girl接口
	public static String GIRLURL = "categorygirl.php";
	//分类页面lifestyle接口
	public static String LIFESTYLEURL = "categorylife.php";
	//分类页面toplistview接口
	public static String CATEGORYVALUEURL="categoryvalue.php";
	//所有品牌接口
	public static String ALLBRANDURL = "allbrand.php";
	//热门品牌接口
	public static String HOTBRANDURL = "hotbrand.php";
	//获取关注列表接口
	public static String FOLLOWURL = "follow.php";
	//获取商品详情
	public static String GOODSVALUEURL = "goodsvalue.php";
	//收藏商品接口
	public static String COLLECTIONSURL = "CollectionShop.php";
	//添加购物车接口
	public static String ADDCARTURL = "addcart.php";
	//购物车列表接口
	public static String CARTLISTURL = "cartlist.php";
	//购物车提交商品接口
	public static String ADDORDER = "UpOrder.php";
	//订单列表接口
	public static String OKORDER = "OkOrder.php";
	//确认订单接口
	public static String CONFIRM = "Confirm.php";
	//刷新购物车列表接口
	public static String RefrashCartURL="RefrashCart.php";
	//获取新闻接口
	public static String NEWSLISTURL = "news.php";
	//登陆接口
	public static String SIGNURL = "yohologin.php";
	//注册接口
	public static String REGIST = "yohoregiste.php";
	//上传头像接口
	public static String UPHEAD = "yohouphead.php";
	//获取品牌商品数据源
	public static String BRANDVALUEURL = "brandvalue.php";
	//获取服务器apk版本接口
	public static String UPDATEAPK = "upVersion.php";


}
