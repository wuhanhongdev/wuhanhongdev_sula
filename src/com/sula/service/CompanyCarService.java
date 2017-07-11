package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface CompanyCarService {

	/**
	 * 公司车辆信息
	 * @param page
	 * @param num
	 * @return
	 */
	public Page<Record> getCompanyCars(int page,String num);
}
