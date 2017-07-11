package com.sula.controller;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CompanyCarService;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class CompanyCarController extends Controller {

	CompanyCarService ccs;
	public void getCompanyCars(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String key = getPara("keyword") == null ? "" : getPara("keyword");
		Page<Record> data = ccs.getCompanyCars(page,key);
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
}
