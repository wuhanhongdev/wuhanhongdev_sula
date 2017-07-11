package com.sula.controller;

import java.util.Date;
import java.util.List;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sula.service.ShipperManagerService;
import com.sula.util.ConfigUtils;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class ShipperManagerController extends Controller {

	private String path = ConfigUtils.getProperty("file.proPath");
	ShipperManagerService sms;
	public void getShippers(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String key = getPara("keyword") == null ? "" : getPara("keyword");
		Page<Record> data = sms.getShippers(page,key);
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
	public void addShipper(){
		UploadFile file = getFile("img", path+"UserImgs\\");
		String filename = FormUtil.renameFile(file.getFile(), path+"UserImgs\\");
		//--
		ResultJson rj = new ResultJson();
	    Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
	    obj.set("create_time", new Date());
	    obj.set("type", 1);
	    obj.set("credit", 100);
	    obj.set("state", 1);
	    obj.set("attach", 0);
	    obj.set("img", filename);
	    obj.set("balance", 0);
	    obj.remove("id");
	    boolean flag = sms.addShipper(obj);
	    if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void getShipperById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		Record obj = sms.getShipperById(id);
		if (obj != null) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
			rj.setResult(obj);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	public void modifyShipper(){
		UploadFile file = getFile("img",path+"UserImgs\\");
		String img = "";
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		if (file != null ) {
			img = FormUtil.renameFile(file.getFile(), path+"UserImgs\\");
			obj.set("img", img);
		}else{
			obj.remove("img");
		}
		boolean i = sms.modifyShipper(obj);
		ResultJson rj = new ResultJson();
		if (i) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void setShipperInBlack(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") ;
		boolean flag = sms.setShipperInBlack(id);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	
	public void getWayBills(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String key = getPara("keyword") == null ? "" : getPara("keyword");
		Page<Record> data = sms.getWayBills(page,key);
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
	
	public void getPurseInfo(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String key = getPara("keyword") == null ? "" : getPara("keyword");
		Page<Record> data = sms.getPurseInfo(page,key);
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
	
	public void addGoodInfo(){
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		boolean flag = sms.addGoodInfo(obj);
		ResultJson rj = new ResultJson();
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	
	public void getGoodsInfoById(){
		int id = getParaToInt("id");
		ResultJson rj = new ResultJson();
		Record obj = sms.getGoodsInfoById(id);
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
	public void modifyGoodInfo(){
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		boolean flag = sms.modifyGoodInfo(obj);
		ResultJson rj = new ResultJson();
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	public void getCompanyCars(){
		int id = getParaToInt("id");
		String mobile = getPara("mobile");
		ResultJson rj = new ResultJson();
		List<Record> obj = sms.getCompanyCars(id,mobile);
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
	public void addInfoToWayBill(){
		int hyid = getParaToInt("hyid");
		String tel = getPara("tel");
		int carid = getParaToInt("carid");
		String mobile = getPara("mobile");
		ResultJson rj = new ResultJson();
		boolean flag = sms.addInfoToWayBill(hyid, tel, carid, mobile);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	public void addSocietyCarInfoToWayBill(){
		int hyid = getParaToInt("id");
		String tel = getPara("tel");
		String mobile = getPara("mobile");
		ResultJson rj = new ResultJson();
		boolean flag = sms.addSocietyCarInfoToWayBill(hyid, tel,  mobile);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage("~~短信已发送成功，等待车主确定~~");
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
}
