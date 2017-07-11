package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.ComplainService;
import com.sula.util.Status;
import org.apache.commons.lang.StringUtils;
import java.util.*;

public class ComplainServiceImpl implements ComplainService {

	@Override
	public Page<Record> getGoodsComplainList(int page) {
		String sql = "select *";
		String exSql = " from sl_complain_info where type = 1 order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean modifyGoodsComplainById(Record obj) {
		return Db.update("sl_complain_info","id", obj);
	}

	@Override
	public Page<Record> getTrucksComplainList(int page) {
		String sql = "select *";
		String exSql = " from sl_complain_info where type = 2 order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean modifyTrucksComplainById(Record obj) {
		boolean flag = Db.update("sl_complain_info", obj);
		return flag;
	}

	@Override
	public Record getGoodsById(int id, int source_id) {
		//Record gc = Db.findById("sl_complain_info", id);
		Record obj = Db.findFirst("select a.nick , a.mobile tel,b.goods,b.freight,b.startplace,b.endplace,c.* from sl_user_info a, sl_goods_info b , sl_complain_info c where a.id = b.user_id and c.source_id = b.id and c.id=? and b.id=?",id,source_id);
		return obj;
	}

	@Override
	public Record getTrucksById(int id, int source_id) {
		String sql = "select a.nick , a.mobile tel,b.loadtime,b.startplace,b.endplace,c.*,d.carmodels,d.carlength from sl_user_info a, sl_trucks_info b , sl_complain_info c,sl_user_truck d where c.source_id = b.id and b.trucks_id = d.id and d.user_id = a.id and c.id =? and c.source_id = ?";
		Record obj = Db.findFirst(sql, id, source_id);
		return obj;
	}

	@Override
	public int addComplain(Integer type, Integer sourceId, Integer userId, String mobile, String content) {
		Record record = new Record();
		record.set("type",type);
		record.set("source_id",sourceId);
		record.set("tsr",userId);
		record.set("mobile", StringUtils.isNotEmpty(mobile) ? mobile : "");
		record.set("tscont",content);
		record.set("state",0);
		record.set("create_time",new Date());
		boolean flag = Db.save("sl_complain_info",record);
		return flag ? Status.success : Status.fail;
	}

}
