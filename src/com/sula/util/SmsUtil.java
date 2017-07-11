package com.sula.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 短信
 * @author Administrator
 *
 */
public class SmsUtil {

	//执行发送
		public static void exece(String mobile, String content)
		{
			// 短信网关发送
			Map<String, String> rep = new HashMap<String, String>();
			rep.put("username", Status.username);
			rep.put("password", Status.pwd);
			rep.put("mobile", mobile);
			rep.put("content", content);
			//rep.put("needstatus", "true");
			// 开始发送
			String code = HttpClientUtil.sendPostRequest(Status.smsUrl, rep);
			//String code ="CLOSE~";
			//发送记录写入到日志表中去
			System.out.println("发送后收到的回应代码为：" + code);
		}
		
		public static void main(String[] args){
			
			SmsUtil.exece("18538083321", "你好，我是短信小助手【速拉】");
			
		}
}
