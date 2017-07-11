package com.sula.controller;

import java.util.Date;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.ComplainService;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class ComplainController extends Controller {

	ComplainService cls;
	/**
	 * 货源
	 */
	public void getGoodsComplainList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = cls.getGoodsComplainList(page);
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
	public void getGoodsById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		int sid = getParaToInt("sid");
		Record data = cls.getGoodsById(id, sid);
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
	public void modifyGoodsComplainById(){
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(),getParaNames());
		obj.set("finish_time", new Date());
		obj.set("state", 1);
		boolean flag = cls.modifyGoodsComplainById(obj);
		if(flag){
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		}else{
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	/**
	 * 车源
	 */
	public void getTrucksById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		int sid = getParaToInt("sid");
		Record data = cls.getTrucksById(id, sid);
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
	public void getTrucksComplainList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = cls.getTrucksComplainList(page);
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
	public void modifyTrucksComplainById(){
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(),getParaNames());
		obj.set("finish_time", new Date());
		obj.set("state", 1);
		boolean flag = cls.modifyTrucksComplainById(obj);
		if(flag){
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		}else{
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
}
