package com.sula.util;

public class Status {

	//返回对象code
	public static int success = 1 ;
	public static int fail = 0 ;
	//返回对象message
	public static String message_success = "~~操作成功~~";
	public static String message_fail = "~~操作失败~~";
	public static String message_null = "~~没有数据~~";
	//pageSize
	public static int pageSize = 10;
	//ApppageSize
	public static int appPageSize = 20;

	//waybill states
	public final static int WAIT_PAY = 0;//待支付
	public final static int WAIT_LOAD = 1;//待装货
	public final static int IN_TRANSIT = 2;//运输中
	public final static int WAIT_ARRIVALS = 3;//待收货
	public final static int DONE = 5;//完成
	public final static int WAIT_EVAL = 4;//待评价

	//我的运单查询购率
	public final static String TYPE_ALL = "ALL";
	public final static String TYPE_IN_TRANSIT = "IN";

	
	//短信URL
	public static String smsUrl = "http://114.215.196.145/sendSmsApi";
	public static String username = "sula";
	public static String pwd = "TglIStWq";
}
