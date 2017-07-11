package com.sula.controller;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CashOutService;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class CashOutController extends Controller {
	
	private CashOutService cos;
	
	public void cashoutList() {
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = cos.getCashOutList(page);
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
	public void getObjectById() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		Record data = cos.getCashById(id);
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
	public void setObjectById() {
		
		ResultJson rj = new ResultJson();
		Record cashout = FormUtil.formToRecord(getRequest(), getParaNames());
		boolean isUp = cos.updateCashInfo(cashout);
		if (isUp) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
//			rj.setResult(data);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
}
