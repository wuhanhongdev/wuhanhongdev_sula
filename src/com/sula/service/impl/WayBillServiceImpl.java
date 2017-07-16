package com.sula.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
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
		record.set("waystate",0);//将货源指定给司机时,属于未支付状态
		boolean flag = Db.save("sl_waybill",record);

		if(flag){//运单生成成功,修改货源状态为运输中
			Record goodsRec = Db.findById("sl_goods_info",goodId);
			if(goodsRec == null){
				return Status.fail;
			}else{
				goodsRec.set("type",2);
				flag = Db.update("sl_goods_info",goodsRec);
			}
		}

		return flag ? Status.success : Status.fail;
	}

	@Override
	public Record selectWaybill(Integer waybillId) {
		return Db.findById("sl_waybill",waybillId);
	}

	@Override @Before(Tx.class)
	public int modifyWaybillState(Integer waybillId, int i) {

		Record record = Db.findById("sl_waybill",waybillId);
		if(record == null){
			return Status.fail;
		}
		record.set("waystate",i);
		Date opDate = new Date();
		switch (i){
			case 1 :
				record.set("paytime",opDate);
				break;
			case 2:
				record.set("loadingtime",opDate);
				break;
			case 3:
				record.set("deliverytime",opDate);
				break;
			case 5:
				record.set("receipttime",opDate);
				//将其货源置为完成
				Integer goodsId = record.get("goods_id");
				Record rec = Db.findById("sl_goods_info",goodsId);
				if(rec != null){
					rec.set("type",3);
					Db.update("sl_goods_info",rec);
				}
				break;
		}
		boolean flag = Db.update("sl_waybill",record);
		return flag ? Status.success : Status.fail;
	}

	@Override
	public int addWaybillEvaluation(Integer waybillId, Integer userId, String evaluationContent,Integer score) {
		Record record = new Record();
		record.set("waybillid",waybillId);
		record.set("score",score);
		record.set("contents",evaluationContent);
		record.set("createtime",new Date());
		record.set("userid",userId);
		boolean flag = Db.save("sl_waybill_replay",record);
		return flag ? Status.success : Status.fail;
	}
}
