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
	 * 生成运单
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

}
