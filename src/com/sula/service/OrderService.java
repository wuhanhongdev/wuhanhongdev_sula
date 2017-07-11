package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface OrderService {

	public Page<Record> getOrderList(int page);
	public Record getOrderInfoById(int id);
	public boolean sendOrder(Record obj);
	/**
	 * 退款
	 */
	public Page<Record> getRefundList(int page);
	public Record getOrderInfoBySn(String sn);
	public boolean agreeRefund(String sn,String hf);
	public boolean refuseRefund(String sn,String hf);
}
