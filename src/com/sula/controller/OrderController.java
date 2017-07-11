package com.sula.controller;

import java.util.Date;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.OrderService;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class OrderController extends Controller {

	OrderService osi ;
	public void getOrderList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = osi.getOrderList(page);
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
	public void getOrderInfoById(){
		int id = getParaToInt("id");
		ResultJson rj = new ResultJson();
		Record obj = osi.getOrderInfoById(id);
		if (obj != null) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(obj);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void sendOrder(){
		int id = getParaToInt("id");
		String exp = getPara("exp");
		String expno = getPara("expno");
		Record obj = new Record();
		obj.set("id", id);
		obj.set("type", 3);
		obj.set("exp", exp);
		obj.set("expno", expno);
		obj.set("exp_time", new Date());
		ResultJson rj = new ResultJson();
		boolean flag = osi.sendOrder(obj);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void getRefundList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = osi.getRefundList(page);
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
	public void getOrderInfoBySn(){
		String sn = getPara("sn");
		ResultJson rj = new ResultJson();
		Record obj = osi.getOrderInfoBySn(sn);
		if (obj != null) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(obj);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void agreeRefund(){
		String sn = getPara("sn");
		String hf = getPara("hf");
		ResultJson rj = new ResultJson();
		boolean flag = osi.agreeRefund(sn, hf);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			//rj.setResult(obj);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void refuseRefund(){
		String sn = getPara("sn");
		String hf = getPara("hf");
		ResultJson rj = new ResultJson();
		boolean flag = osi.refuseRefund(sn, hf);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			//rj.setResult(obj);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
}
