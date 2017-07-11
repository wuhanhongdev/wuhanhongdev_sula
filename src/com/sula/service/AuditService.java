package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface AuditService {

	public Page<Record> auditOwnerList(int page);
	public Page<Record> auditsourceList(int page);
	public int auditPass(int id);
	public int auditRebut(int id);
	public Record getObjectById(int id);
}
