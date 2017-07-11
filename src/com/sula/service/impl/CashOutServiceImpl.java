package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CashOutService;
import com.sula.util.Status;

public class CashOutServiceImpl implements CashOutService{

	public Page<Record> getCashOutList(int page) {
		String sql = "select t1.*,t2.mobile";
		String exSql = " from sl_cashout_info t1 left join sl_user_info t2 on t1.userid = t2.id";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public Record getCashById(int id) {
		Record cashOut = Db.findById("sl_cashout_info", id);
		return cashOut;
	}

	@Override
	public boolean updateCashInfo(Record cashData) {
		cashData.set("end_time", new java.util.Date());
		return Db.update("sl_cashout_info", cashData);
	}

}
