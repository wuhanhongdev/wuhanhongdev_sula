package com.sula.controller;

import java.util.Date;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sula.service.ProductService;
import com.sula.util.ConfigUtils;
import com.sula.util.FormUtil;
import com.sula.util.ResultJson;
import com.sula.util.Status;

public class ProductController extends Controller {

	ProductService psi;
	String path = ConfigUtils.getProperty("file.proPath");

	public void getProductList() {
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		Page<Record> data = psi.getProductList(page);
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

	public void addProduct() {
		UploadFile file = getFile("img", path+"proImgs\\");
		String img = FormUtil.renameFile(file.getFile(), path+"proImgs\\");
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		obj.set("img", img);
		obj.set("is_show", 1);
		obj.set("create_time",new Date());
		obj.remove("id");
		int i = psi.addProduct(obj);
		ResultJson rj = new ResultJson();
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_fail);
		}
		renderJson(rj);
	}

	public void getOneProductById() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		Record obj = psi.getOneProductById(id);
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

	public void modifyProductById() {
		UploadFile file = getFile("img",path+"proImgs\\");
		String img = "";
		Record obj = FormUtil.formToRecord(getRequest(), getParaNames());
		if (file != null ) {
			img = FormUtil.renameFile(file.getFile(), path+"proImgs\\");
			obj.set("img", img);
		}else{
			obj.remove("img");
		}
		boolean i = psi.modifyProductById(obj);
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

	public void delProductById() {
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		int i = psi.delProductById(id);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
	
	public void updateProductById(){
		ResultJson rj = new ResultJson();
		int id = getParaToInt("id") == null ? 1 : getParaToInt("id");
		int i = psi.updateProductById(id);
		if (i > 0) {
			rj.setCode(Status.success);
			rj.setMessage(Status.message_success);
		} else {
			rj.setCode(Status.fail);
			rj.setMessage(Status.message_null);
		}
		renderJson(rj);
	}
}
