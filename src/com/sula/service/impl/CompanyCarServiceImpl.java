package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CompanyCarService;
import com.sula.util.Status;

public class CompanyCarServiceImpl implements CompanyCarService {

	@Override
	public Page<Record> getCompanyCars(int page, String num) {
		String sql = "select a.carmodels,a.plateno,b.startplace,b.endplace,c.waystate";
		String sqlEx = "";
		if(num == null || num.equals("")){
			sqlEx = " from sl_user_truck a , sl_trucks_info b , sl_waybill c  where a.id = b.trucks_id and b.id = c.trucks_id and a.user_id in ( select id from sl_user_info where attach =2)";
		}else{
			sqlEx = " from sl_user_truck a , sl_trucks_info b , sl_waybill c  where a.id = b.trucks_id and b.id = c.trucks_id and a.plateno like '%"+num+"%' and a.user_id in ( select id from sl_user_info where attach =2)";
		}
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

}
