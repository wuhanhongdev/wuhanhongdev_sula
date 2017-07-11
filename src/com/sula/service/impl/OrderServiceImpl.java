package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.OrderService;
import com.sula.util.Status;

public class OrderServiceImpl implements OrderService {

	@Override
	public Page<Record> getOrderList(int page) {
		String sql = " select *";
		String sqlEx = " from sl_pro_order_view order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public Record getOrderInfoById(int id) {
		return  Db.findById("sl_pro_order_view","id",id);
	}

	@Override
	public boolean sendOrder(Record obj) {
		return Db.update("sl_product_order", "id", obj);
		
	}

	@Override
	public Page<Record> getRefundList(int page) {
		String sql = " select *";
		String sqlEx = " from sl_order_refund order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public Record getOrderInfoBySn(String sn) {
		return Db.findFirst("select * from sl_pro_order_view where sn=?",sn);
	}

	@Override
	public boolean agreeRefund(String sn, String hf) {
		int i = Db
				.update("update sl_order_refund set type = 2 , hf=? where sn=?",
						hf, sn);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean refuseRefund(String sn,String hf) {

		int i = Db
				.update("update sl_order_refund set type = 3 , hf=? where sn=?",
						hf, sn);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	
	}

}
