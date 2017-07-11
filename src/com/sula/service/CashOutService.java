package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface CashOutService {

	public Page<Record> getCashOutList(int page);
	
	public Record getCashById(int id);
	
	public boolean updateCashInfo(Record cashData);
}
