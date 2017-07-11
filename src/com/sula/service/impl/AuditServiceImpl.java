package com.sula.service.impl;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.AuditService;
import com.sula.util.Status;

public class AuditServiceImpl implements AuditService {

	@Override
	public Page<Record> auditOwnerList(int page) {
		String sql = "select *";
		String exSql = " from sl_useracc_view where atype = 2";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public Page<Record> auditsourceList(int page) {
		String sql = "select *";
		String exSql = " from sl_useracc_view where atype = 1 order by create_time desc ";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public int auditPass(int id) {
		Record obj = new Record();
		obj.set("id", id);
		obj.set("type", 2);
		obj.set("finish_time", new Date());
		boolean flag = Db.update("sl_acc_info", obj);
		if(flag){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public int auditRebut(int id) {
		Record obj = new Record();
		obj.set("id", id);
		obj.set("type", 3);
		obj.set("finish_time", new Date());
		boolean flag = Db.update("sl_acc_info", obj);
		if(flag){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public Record getObjectById(int id) {
		Record obj = Db.findFirst("select * from sl_useracc_view where id=?",id);
		return obj;
	}

}
