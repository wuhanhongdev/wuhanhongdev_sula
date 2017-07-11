package com.sula.service;

import java.util.List;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface CarOwnerService {

	/**
	 * 车主列表
	 */
	
	public Page<Record> getCarOwnerList(int page,String key);
	public boolean addCarOwner(Record obj);
	public Record getCarOwnerById(int id);
	public boolean updateCarOwnerById(Record obj);
	public List<Record> getUserCars(int id);
	public Page<Record> getWaysById(int page,int id);
	public Record getPurseById(int id);
	public boolean updateUserState(int id);
	
	
	
	/**
	 * 车辆信息
	 */
	public Page<Record> getALLCars(int page,String para);
	public boolean addTruck(Record obj);
	public boolean modifyTruck(Record obj);
	public boolean delTruck(int id);
	public Record getTruck(int id);
}
