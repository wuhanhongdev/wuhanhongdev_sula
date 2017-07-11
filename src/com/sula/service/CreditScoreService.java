package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface CreditScoreService {

	/**
	 * 信用积分
	 */
	public Record getCreditObject();
	public boolean modifyCreditObject(boolean flag , Record obj);
	public Page<Record> getCreditUser(int page,int creditState);
	public boolean modifyAccountState(int id,int state);
}
