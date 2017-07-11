package com.sula.controller;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.AuditService;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class AuditController extends Controller {

	/**
	 * 
	 */
	AuditService as ;
	/**
	 * 车主审核列表
	 */
	public void auditOwnerList() {
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = as.auditOwnerList(page);
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
	 * 货源审核列表
	 */
	public void auditsourceList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = as.auditsourceList(page);
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
	 * 审核通过
	 */
	public void auditPass() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 0 : getParaToInt("id");
		int i = as.auditPass(id);
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
	 * 审核驳回
	 */
	public void auditRebut(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 0 : getParaToInt("id");
		int i = as.auditRebut(id);
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
	 * 查找到一个对象
	 */
	public void getObjectById() {
		int id = getParaToInt("id");
		ResultJson rj = new ResultJson();
		Record obj = as.getObjectById(id);
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
