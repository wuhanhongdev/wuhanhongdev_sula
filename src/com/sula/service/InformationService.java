package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface InformationService {

	//车源
	public Page<Record> getCarInfoList(int page);
	public boolean modifyIsTopCar(Record obj);
	public Record getCarById(int id);
	//货源
	public Page<Record> getGoodsInfoList(int page);
	public boolean modifyIsTopGoods(Record obj);
	public Record getGoodsById(int id);
}
