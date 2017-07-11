package com.sula.service;


import java.util.List;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface ShipperManagerService {

	/**
	 * 货主信息
	 */
	public Page<Record> getShippers(int page , String mobile);
	public Record getShipperById(int id);
	public boolean addShipper(Record obj);
	public boolean modifyShipper(Record obj);
	public boolean setShipperInBlack(int id);
	/**
	 * 发单信息
	 */
	public Page<Record> getWayBills(int page , String mobile);
	/**
	 * 收入
	 */
	public Page<Record> getPurseInfo(int page , String mobile);
	/**
	 * 货源管理
	 */
	public boolean addGoodInfo(Record obj);
	public Record getGoodsInfoById(int id);
	public boolean modifyGoodInfo(Record obj);
	/**
	 *  -------------货源匹配
	 */
	
	/**
	 * 根据发布的货源选择匹配公司车辆
	 */
	public List<Record> getCompanyCars(int id,String mobile);
	/**
	 * 手动匹配
	 * @param hyid  货源ID
	 * @param tel   货主电话
	 * @param carid 车辆ID
	 * @param mobile 车主电话
	 * @return
	 */
	public boolean addInfoToWayBill(int hyid,String tel,int carid,String mobile);
	/**
	 * 社会车辆匹配
	 * @param hyid 
	 * @param tel 车主电话
	 * @param mobile 货主电话
	 * @return
	 */
	public boolean addSocietyCarInfoToWayBill(int hyid,String tel,String mobile);
}
