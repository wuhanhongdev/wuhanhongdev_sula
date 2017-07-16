package com.sula.controller;

import com.alibaba.fastjson.JSONObject;
import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.DriverService;
import com.sula.service.GoodsService;
import com.sula.service.WayBillService;
import com.sula.util.ResultJson;
import com.sula.util.Status;
import com.sula.util.Verify;
import org.apache.commons.lang.StringUtils;

public class WayBillController extends Controller {
	
	private WayBillService wbs;
	GoodsService goodsService;
	DriverService driverService;
	
	public void getWayBillList() {
		ResultJson rj = new ResultJson();
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String keyword = getPara("keyword");
		Page<Record> data = wbs.getWayBillList(page,keyword);
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
	 * 加载我的运单
	 */
	public void loadMimeWaybill(){
		ResultJson json = new ResultJson();

		String userRole = getPara("userRole");
		Integer userId = getParaToInt("userId");
		String type = getPara("type");
		int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
		String sign = getPara("sign");
		if (StringUtils.isEmpty(sign)){
			json.setCode(Status.fail);
			json.setMessage("手机MAC地址为空");
		}else if(StringUtils.isEmpty(userRole)){
			json.setCode(Status.fail);
			json.setMessage("用户类型为空");
		}else if(userId == null){
			json.setCode(Status.fail);
			json.setMessage("用户ID为空");
		}else if(StringUtils.isEmpty(type)){
			json.setCode(Status.fail);
			json.setMessage("查询类型为空");
		}else{
			if(Verify.isMac(sign)){
				Page<Record> record = null;
				if("2".equals(userRole)){//查询货主运单
					record = goodsService.selectMimeWaybill(userId,type,page);
				}else if("1".equals(userRole)){//查询司机运单
					record = driverService.selectMimeWaybill(userId,type,page);
				}
				json.setCode(Status.success);
				json.setMessage("查询成功");
				json.setResult(record);
			}else{
				json.setCode(Status.fail);
				json.setMessage("校验码错误");
			}
		}
		renderJson(json);
	}

	/**
	 * 生成运单,货主将某个货源指派给司机或者司机主动找货并得到货主认可将货源指派后,生成运单,初始状态为0即待支付状态
	 */
	public void initWaybill(){
		ResultJson json = new ResultJson();
	    Integer goodId = getParaToInt("goodId");
	    Integer truckId = getParaToInt("truckId");
	    String cost = getPara("price");
		String sign = getPara("sign");
		if (StringUtils.isEmpty(sign)){
			json.setCode(Status.fail);
			json.setMessage("手机MAC地址为空");
		}else if(goodId == null){
			json.setCode(Status.fail);
			json.setMessage("货源ID为空");
		}else if(truckId == null){
			json.setCode(Status.fail);
			json.setMessage("车源ID为空");
		}else if(StringUtils.isEmpty(cost)){
			json.setCode(Status.fail);
			json.setMessage("运单金额为空");
		}else{
			if(Verify.isMac(sign)){
				int state = wbs.initWaybill(goodId,truckId,cost);
				if(state == Status.fail){
					json.setCode(Status.fail);
					json.setMessage("运单创建失败,请重试!");
				}else{
					json.setCode(Status.success);
					json.setMessage("运单创建成功");
				}
			}else{
				json.setCode(Status.fail);
				json.setMessage("校验码错误");
			}
		}
		renderJson(json);
	}

	/**
	 * 加载运单详情
	 */
	public void loadWaybillDetail(){
		ResultJson json = new ResultJson();
		Integer waybillId = getParaToInt("waybillId");
		String sign = getPara("sign");
		if (StringUtils.isEmpty(sign)){
			json.setCode(Status.fail);
			json.setMessage("手机MAC地址为空");
		}else if(waybillId == null){
			json.setCode(Status.fail);
			json.setMessage("运单ID为空");
		}else{
			if(Verify.isMac(sign)){
				Record waybill = wbs.selectWaybill(waybillId);
				if(waybill != null){
					JSONObject object = new JSONObject();
					//得到货源ID
					Integer goodId = waybill.getInt("goods_id");
					//得到车源ID
					Integer truckId = waybill.getInt("trucks_id");
					//得到货源
					Record good = goodsService.searchGoodById(goodId);
					//得到车源
					Record trucks = driverService.selectTrucksInfo(truckId);

					Record rateInfo = driverService.selectRateInfo(truckId);
					object.put("waybill",waybill);
					object.put("goods",good);
					object.put("trucks",trucks);
					object.put("rateInfo",rateInfo);

					if(trucks != null){
						Integer driverId = trucks.getInt("trucks_id");
						object.put("driverInfo",driverId == null ? null : driverService.selectTruckInfo(driverId));
					}
					json.setCode(Status.success);
					json.setMessage("运单信息查询成功");
					json.setResult(object);
				}else{
					json.setCode(Status.fail);
					json.setMessage("运单信息查询失败");
				}
			}else{
				json.setCode(Status.fail);
				json.setMessage("校验码错误");
			}
		}

		renderJson(json);
	}

	/**
	 * 支付成功后,上传支付状态,将运单状态变为待装货
	 */
	public void uploadPayState(){
		modifyWaybillState(Status.WAIT_LOAD);
	}

	/**
	 * 司机上传装货结果
	 * 货主支付完成后,由司机前去装货,装货完成后,由司机触发运单状态为运输中
	 */
	public void uploadDriverLoad(){
		modifyWaybillState(Status.IN_TRANSIT);
	}

	/**
	 * 司机上传已到达结果
	 * 运输到达目的地后,司机将运单状态触发为待收货状态
	 */
	public void uploadArrivals(){
		modifyWaybillState(Status.WAIT_ARRIVALS);
	}

	/**
	 * 由货主将运单确认收货，并将运单状态触发为4即待评价状态,随后app应跳转到对运单的评价页面，使货主对运单做评价
	 */
	public void uploadReceiveState(){
		modifyWaybillState(Status.DONE);
	}

	/**
	 * 由货主对司机作评价，评价完成后将运单状态变为完成即5
	 */
	public void uploadEvaluation(){
		ResultJson json = new ResultJson();
		Integer waybillId = getParaToInt("waybillId");//运单ID
		String evaluationContent = getPara("evaluationContent");
		Integer userId = getParaToInt("userId");//评价人ID
		Integer score = getParaToInt("score");//评价人ID
		String sign = getPara("sign");
		if (StringUtils.isEmpty(sign)){
			json.setCode(Status.fail);
			json.setMessage("手机MAC地址为空");
		}else if(waybillId == null){
			json.setCode(Status.fail);
			json.setMessage("运单ID为空");
		}else if(userId == null){
			json.setCode(Status.fail);
			json.setMessage("评价人ID为空");
		}else if(StringUtils.isEmpty(evaluationContent)){
			json.setCode(Status.fail);
			json.setMessage("评价内容为空");
		}else if(score == null){
			json.setCode(Status.fail);
			json.setMessage("评价分数为空");
		}else{
			if(Verify.isMac(sign)){
				int state = wbs.addWaybillEvaluation(waybillId,userId,evaluationContent,score);
				if(state == Status.fail){
					json.setCode(Status.fail);
					json.setMessage("运单评价失败,请重试!");
				}else{
					json.setCode(Status.success);
					json.setMessage("运单评价成功!");
				}
			}else{
				json.setCode(Status.fail);
				json.setMessage("校验码错误");
			}
		}
		renderJson(json);
	}

	private void modifyWaybillState(int s){
		ResultJson json = new ResultJson();
		Integer waybillId = getParaToInt("waybillId");//运单ID

		String sign = getPara("sign");
		if (StringUtils.isEmpty(sign)){
			json.setCode(Status.fail);
			json.setMessage("手机MAC地址为空");
		}else if(waybillId == null){
			json.setCode(Status.fail);
			json.setMessage("运单ID为空");
		}else{
			if(Verify.isMac(sign)){
				int state = wbs.modifyWaybillState(waybillId,s);
				if(state == Status.fail){
					json.setCode(Status.fail);
					json.setMessage("运单状态更新失败,请重试!");
				}else{
					json.setCode(Status.success);
					json.setMessage("运单状态更新成功");
				}
			}else{
				json.setCode(Status.fail);
				json.setMessage("校验码错误");
			}
		}

		renderJson(json);
	}

}
