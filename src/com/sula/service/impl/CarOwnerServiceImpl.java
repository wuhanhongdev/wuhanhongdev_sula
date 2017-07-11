package com.sula.service.impl;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CarOwnerService;
import com.sula.util.Status;

public class CarOwnerServiceImpl implements CarOwnerService {

	@Override
	public Page<Record> getCarOwnerList(int page,String key) {
		String sql = "select *";
		String sqlEx = "";
		if(key == null || key.equals("")){
			sqlEx =	" from sl_user_info  where type =2 order by create_time desc";
		}else{
			sqlEx =	" from sl_user_info where type =2 mobile like '%"+key+"%'  order by create_time desc";
		}	
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public boolean addCarOwner(Record obj) {
		return Db.save("sl_user_info", obj);
		//return false;
	}

	@Override
	public Record getCarOwnerById(int id) {
		return Db.findById("sl_user_info", "id", id);
	}

	@Override
	public boolean updateCarOwnerById(Record obj) {
		return  Db.update("sl_user_info", "id", obj);
		//return false;
	}

	@Override
	public List<Record> getUserCars(int id) {
		return Db.find("select * from sl_user_truck where user_id=? order by id desc",id);
	}

	@Override
	public Page<Record> getWaysById(int page,int id) {
		String sql = "select *";
		String sqlEx = " from sl_waybill a where a.trucks_id in ( select id from sl_user_truck where user_id = ? )  order by a.createtime";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx,id);
		return data;
	}

	@Override
	public Record getPurseById(int id) {
		Record obj = new Record();
		Record temp1 = Db.findFirst("select sum(money) sr from sl_purse_info where user_id = ? and type =1",id);
		Record temp2 = Db.findFirst("select sum(money) zc from sl_purse_info where user_id = ? and type =2",id);
		obj.set("create_time", new Date());
		obj.set("sr", temp1.getDouble("sr") == null ? 0 : temp1.getDouble("sr"));
		obj.set("zc", temp2.getDouble("zc") == null ? 0 : temp2.getDouble("zc"));
		return obj;
	}

	@Override
	public boolean updateUserState(int id) {
		int i = Db.update("update sl_user_info set state = 0 where id =?", id);
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public Page<Record> getALLCars(int page, String para) {
		String sql = "select a.nick , b.*";
		String sqlEx = "";
		if(para == null || para.equals("")){
			sqlEx = "from sl_user_info a ,sl_user_truck b where a.id = b.user_id order by user_id desc ,create_time desc ";
		}else{
			sqlEx = "from sl_user_info a ,sl_user_truck b where a.id = b.user_id and b.plateno like '%"+para+"%' order by user_id desc ,create_time desc ";
		}
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public boolean addTruck(Record obj) {
		String mobile = obj.getStr("mobile");
		Record user = Db.findFirst("select * from sl_user_info where mobile = ? and type =2",mobile);
		obj.set("user_id", user.get("id"));
		obj.remove("mobile");
		return Db.save("sl_user_truck", obj);
	}

	@Override
	public boolean modifyTruck(Record obj) {
		obj.remove("mobile");
		return Db.update("sl_user_truck", "id", obj);
	}

	@Override
	public boolean delTruck(int id) {
		return Db.deleteById("sl_user_truck", "id", id);
	}

	@Override
	public Record getTruck(int id) {
		return Db.findFirst("select a.nick , a.mobile,b.* from sl_user_info a ,sl_user_truck b where a.id = b.user_id and b.id = ?", id);
	}

}
