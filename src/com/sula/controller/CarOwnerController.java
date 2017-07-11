package com.sula.controller;

import java.util.Date;
import java.util.List;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sula.service.CarOwnerService;
import com.sula.util.ConfigUtils;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

/**
 * 车主管理
 * @author Administrator
 *车主信息包括帐号信息、车辆信息、接单信息、收入信息、类别信息、信用值；功能包括【添加/修改 - 人物标签】【拉入黑名单】【查询】 【短信/消息推送】
 */
public class CarOwnerController extends Controller {

	private String path = ConfigUtils.getProperty("file.proPath");
	private CarOwnerService cos ;
	public void getCarOwnerList(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String key = getPara("keyword") == null ? "" : getPara("keyword");
		Page<Record> data = cos.getCarOwnerList(page,key);
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
	public void addCarOwner(){
		UploadFile file = getFile("img", path+"UserImgs\\");
		String filename = FormUtil.renameFile(file.getFile(), path+"UserImgs\\");
		//--
		ResultJson rj = new ResultJson();
	    Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
	    obj.set("create_time", new Date());
	    obj.set("type", 2);
	    obj.set("credit", 100);
	    obj.set("state", 1);
	    obj.set("attach", 2);
	    obj.set("img", filename);
	    obj.set("balance", 0);
	    obj.remove("id");
	    boolean flag = cos.addCarOwner(obj);
	    if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void getCarOwnerById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		Record obj = cos.getCarOwnerById(id);
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
	public void updateCarOwnerById(){
		UploadFile file = getFile("img",path+"UserImgs\\");
		String img = "";
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		if (file != null ) {
			img = FormUtil.renameFile(file.getFile(), path+"UserImgs\\");
			obj.set("img", img);
		}else{
			obj.remove("img");
		}
		boolean i = cos.updateCarOwnerById(obj);
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
	public void getUserCars(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		List<Record> data = cos.getUserCars(id);
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
	public void getWaysById(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		Page<Record> data = cos.getWaysById(page, id);
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
	public void getPurseById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		Record data = cos.getPurseById(id);
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
	public void updateUserState(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") ;
		boolean flag = cos.updateUserState(id);
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);

		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	
	public void getALLCars(){
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String para = getPara("para") == null ? "" : getPara("para");
		Page<Record> data = cos.getALLCars(page, para);
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
	public void addTruck(){
		UploadFile file = getFile("img", path+"truckImgs\\");
		String img = FormUtil.renameFile(file.getFile(), path+"truckImgs\\");
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		obj.set("img", img);
		obj.set("create_time",new Date());
		obj.remove("id");
		boolean flag = cos.addTruck(obj);
		ResultJson rj = new ResultJson();
		if (flag) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}
	public void modifyTruck(){
		UploadFile file = getFile("img",path+"truckImgs\\");
		String img = "";
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		if (file != null ) {
			img = FormUtil.renameFile(file.getFile(), path+"truckImgs\\");
			obj.set("img", img);
		}else{
			obj.remove("img");
		}
		boolean i = cos.modifyTruck(obj);
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
	public void delTruck() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		boolean i =cos.delTruck(id);
		if (i) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	public void getTruck(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		Record obj = cos.getTruck(id);
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
}
