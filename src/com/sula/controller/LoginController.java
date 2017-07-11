package com.sula.controller;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.LoginService;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

/**
 * 用户登录 Description : 用户登录操作及其它
 * 
 * @author 冯沛
 * @date 2016年12月22日
 */
public class LoginController extends Controller {

	LoginService ls;
	/**
	 * 默认跳转路径
	 */
	public void index() {
		render("index.jsp");
	}
	/**
	 * 登录操作
	 */
	public void adminlogin() {
		ResultJson json = new ResultJson();
		Record login_record = new Record();
		login_record = FormUtil.formToRecord(getRequest(), getParaNames());
		String account = login_record.getStr("account");
		String password = login_record.getStr("password");
		Record userInfo = ls.loginAdmin(account, password);
		if(userInfo!=null) {
			userInfo.remove("password");
			getSession().setAttribute("USERINFO", userInfo);
			// 获取模块信息
//			List<Record> modules_list = ls.getModules(String.valueOf(userInfo.get("role")));
//			getSession().setAttribute("MODULES", modules_list);
			json.setCode(Status.success);
            json.setMessage("");
		} else {
			json.setCode(Status.fail);
            json.setMessage("登录失败！");
		}
		renderJson(json);
	}
}
