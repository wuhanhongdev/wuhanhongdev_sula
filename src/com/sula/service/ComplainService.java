package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 投诉处理
 * @author 2017年6月27日 
 */
@AutoInstance
public interface ComplainService {

	/**
	 * 货源投诉
	 */
	public Page<Record> getGoodsComplainList(int page);
	public boolean modifyGoodsComplainById(Record obj);
	public Record getGoodsById(int id,int source_id);
	/**
	 * 车源投诉
	 */
	public Page<Record> getTrucksComplainList(int page);
	public boolean modifyTrucksComplainById(Record obj);
	public Record getTrucksById(int id,int source_id);

	/**
	 * 添加投诉
	 */
	int addComplain(Integer type, Integer sourceId, Integer userId, String mobile, String content);
}
