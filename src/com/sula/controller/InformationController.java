package com.sula.controller;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.InformationService;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class InformationController extends Controller {

	InformationService ifs ;
	//车源
	public void getCarInfoList(){
		int page = getParaToInt("page") == null ? 1 :getParaToInt("page");
		Page<Record> data = ifs.getCarInfoList(page);
		ResultJson rj = new ResultJson();
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
	public void modifyIsTopCar(){
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		ResultJson rj = new ResultJson();
		boolean flag = ifs.modifyIsTopCar(obj);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void getCarById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		Record obj = ifs.getCarById(id);
		if (obj!=null) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(obj);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	//货源
	public void getGoodsInfoList(){
		int page = getParaToInt("page") == null ? 1 :getParaToInt("page");
		Page<Record> data = ifs.getGoodsInfoList(page);
		ResultJson rj = new ResultJson();
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
	public void modifyIsTopGoods(){
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		ResultJson rj = new ResultJson();
		boolean flag = ifs.modifyIsTopGoods(obj);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void getGoodsById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		Record obj = ifs.getGoodsById(id);
		if (obj!=null) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(obj);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
}
