package com.sula.controller;

import java.util.Date;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CreditScoreService;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class CreditScoreController extends Controller {

	CreditScoreService crs ;
	/**
	 * 积分规则
	 */
	public void getCreditObject(){
		ResultJson rj = new ResultJson();
		Record obj = crs.getCreditObject();
		if(obj!=null){
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(obj);
		}else{
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	public void modifyCreditObject(){
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		String id =obj.getStr("id");
		boolean flag = false ;
		if(id == null || id.equals("")){
			obj.remove("id");
			obj.set("create_time", new Date());
			flag = crs.modifyCreditObject(true, obj);
		}else{
			obj.set("finish_time", new Date());
			flag = crs.modifyCreditObject(false, obj);
		}
		if(flag){
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		}else{
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void creditUserList() {
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		int creditState = getParaToInt("creditState")==null?0:getParaToInt("creditState");
		Page<Record> data = crs.getCreditUser(page, creditState);
		if (data != null) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(data);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	public void setAccountState() {
		ResultJson rj = new ResultJson();
		int accountId = getParaToInt("id");
		int state = getParaToInt("state");
		boolean isUpdate = crs.modifyAccountState(accountId, state);
		if(isUpdate) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
}
