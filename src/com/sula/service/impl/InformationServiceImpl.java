package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.InformationService;
import com.sula.util.Status;

public class InformationServiceImpl implements InformationService {

	@Override
	public Page<Record> getCarInfoList(int page) {
		String sql = "select a.*,b.carmodels,b.address,b.plateno,c.nick,c.mobile ";
		String exSql = " from sl_trucks_info a , sl_user_truck b, sl_user_info c where a.trucks_id = b.id and b.user_id = c.id order by a.is_top desc,a.create_time desc  "	;
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean modifyIsTopCar(Record obj) {
		boolean flag = Db.update("sl_trucks_info", "id", obj);
		return flag;
	}

	@Override
	public Page<Record> getGoodsInfoList(int page) {
		String sql = "select a.*,b.nick,b.mobile";
		String sqlEx = "from sl_goods_info a,sl_user_info b where a.user_id = b.id order by a.is_top desc,a.create_time desc ";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public boolean modifyIsTopGoods(Record obj) {
		boolean flag = Db.update("sl_goods_info", "id", obj);
		return flag;
	}

	@Override
	public Record getCarById(int id) {
		String sql = "select a.*,b.carmodels,b.address,b.plateno,b.carlength,c.nick,c.mobile from sl_trucks_info a , sl_user_truck b, sl_user_info c where a.trucks_id = b.id and b.user_id = c.id and a.id=?"	;
		Record obj = Db.findFirst(sql,id);
		return obj;
	}

	@Override
	public Record getGoodsById(int id) {
		String sql = "select a.*,b.nick,b.mobile from sl_goods_info a,sl_user_info b where a.user_id = b.id and a.id=?";
		Record obj = Db.findFirst(sql,id);
		return obj;
	}

}
