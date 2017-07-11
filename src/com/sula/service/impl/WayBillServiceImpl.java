package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.WayBillService;
import com.sula.util.Status;

import java.util.Date;

public class WayBillServiceImpl implements WayBillService{

	public Page<Record> getWayBillList(int page,String keyword) {
		String sql = "select *";
		String exSql = " from sl_waybill";
		if(keyword!=null&&!keyword.equals("")) {
			exSql = exSql + " where code like '%"+keyword+"%'";
		}
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public int initWaybill(Integer goodId, Integer truckId, String cost) {
		Record record = new Record();
		record.set("goods_id",goodId);
		record.set("trucks_id",truckId);
		record.set("cost",cost);
		record.set("createtime",new Date());
		record.set("paystate",0);//未支付
		record.set("isloading",0);//未装货
		record.set("waystate",1);//运输中
		boolean flag = Db.save("sl_waybill",record);
		return flag ? Status.success : Status.fail;
	}

	@Override
	public Record selectWaybill(Integer waybillId) {
		return Db.findById("sl_waybill",waybillId);
	}
}
