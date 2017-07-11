package com.sula.controller;

import java.util.Date;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.SubInfoService;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class SubInfoController extends Controller {

	SubInfoService sis;

	/**
	 * 列表
	 */
	public void getAdvertList() {
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = sis.getAdvertList(page);
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

	/**
	 * 新增
	 */
	public void addAdvert() {
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		obj.set("type", 1);
		obj.set("create_time", new Date());
		boolean flag = sis.addAdvert(obj);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}

	/**
	 * 获取对象
	 */
	public void getAdvertByID() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		Record obj = sis.getAdvertByID(id);
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

	/**
	 * 修改
	 */
	public void updateAdvert() {
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		int i = sis.updateAdvert(obj);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 删除
	 */
	public void delAdvert(){
		int id = getParaToInt("id");
		ResultJson rj = new ResultJson();
		int i = sis.delAdvert(id);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	//广告功能完毕
	//资讯功能开始
	/**
	 * 资讯 列表
	 */
	public void getConsultList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = sis.getConsultList(page);
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
	/**
	 * 新增
	 */
	public void addConsult() {
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		obj.set("type", 2);
		obj.set("create_time", new Date());
		boolean flag = sis.addConsult(obj);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 修改
	 */
	public void updateConsult() {
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		int i = sis.updateConsult(obj);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 删除
	 */
	public void delConsult(){
		int id = getParaToInt("id");
		ResultJson rj = new ResultJson();
		int i = sis.delConsult(id);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 获取对象
	 */
	public void getConsultById() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		Record obj = sis.getConsultById(id);
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
	/**
	 * 帮助信息
	 */
	public void getAssistList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = sis.getAssistList(page);
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
	/**
	 * 新增
	 */
	public void addAssist() {
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		obj.set("type", 3);
		obj.set("create_time", new Date());
		boolean flag = sis.addAssist(obj);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 修改
	 */
	public void updateAssist() {
		ResultJson rj = new ResultJson();
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		int i = sis.updateAssist(obj);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 删除
	 */
	public void delAssist(){
		int id = getParaToInt("id");
		ResultJson rj = new ResultJson();
		int i = sis.delAssist(id);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	/**
	 * 获取对象
	 */
	public void getAssistById() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id");
		Record obj = sis.getAssistById(id);
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
}
