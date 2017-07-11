package com.sula.service.impl;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.ShipperManagerService;
import com.sula.util.Status;

public class ShipperManagerServiceImpl implements ShipperManagerService {

	@Override
	public Page<Record> getShippers(int page, String mobile) {
		String sql = "select *";
		String sqlEx = "";
		if (mobile == null || mobile.equals("")) {
			sqlEx = " from sl_user_info  where type =1 order by create_time desc ";
		} else {
			sqlEx = " from sl_user_info where type =1 and mobile like '%"
					+ mobile + "%'  order by create_time desc";
		}
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public Record getShipperById(int id) {
		return Db.findById("sl_user_info", "id", id);
	}

	@Override
	public boolean addShipper(Record obj) {
		return Db.save("sl_user_info", obj);
	}

	@Override
	public boolean modifyShipper(Record obj) {
		return Db.update("sl_user_info", "id", obj);
	}

	@Override
	public boolean setShipperInBlack(int id) {
		int i = Db.update("update sl_user_info set state = 0 where id =?", id);
		if (i > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Page<Record> getWayBills(int page, String mobile) {
		String sql = "select a.id,a.goods,a.weights,a.dulk,a.startplace,a.endplace,a.loadtime,a.create_time,a.type,a.message,a.freight,b.nick,b.mobile";
		String sqlEx = "";
		if (sqlEx == null || sqlEx.equals("")) {
			sqlEx = "from sl_goods_info a , sl_user_info b where b.type = 1 and a.user_id = b.id order by a.create_time desc";
		} else {
			sqlEx = "from sl_goods_info a , sl_user_info b where b.type = 1 and a.user_id = b.id and b.mobile like '%"
					+ mobile + "%' order by a.create_time desc";
		}
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public Page<Record> getPurseInfo(int page, String mobile) {
		String sql = "select b.nick,b.mobile,a.type,a.money,a.create_time";
		String sqlEx = "";
		if (sqlEx == null || sqlEx.equals("")) {
			sqlEx = "from sl_purse_info a,sl_user_info b where a.user_id = b.id and b.type = 1 order by a.create_time desc";
		} else {
			sqlEx = "from sl_purse_info a,sl_user_info b where a.user_id = b.id and b.type = 1 and b.mobile like '%"
					+ mobile + "%' order by a.create_time desc";
		}
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public boolean addGoodInfo(Record obj) {
		String mobile = obj.getStr("mobile");
		Record user = Db.findFirst(
				"select * from sl_user_info where mobile=? and type=1", mobile);
		if (user != null) {
			obj.set("user_id", user.get("id"));
			obj.remove("mobile");
			obj.remove("id");
			obj.set("create_time", new Date());
			return Db.save("sl_goods_info", obj);
		} else {
			return false;
		}
	}

	@Override
	public Record getGoodsInfoById(int id) {
		return Db
				.findFirst(
						"SELECT a.nick , a.mobile , b.id,b.goods,b.weights,b.dulk,b.freight,b.loadtime,b.message,b.startplace,b.endplace,b.create_time,b.type,b.carmodels,b.carlength FROM sl_user_info a ,sl_goods_info b where a.id = b.user_id and b.id = ?",
						id);
	}

	@Override
	public boolean modifyGoodInfo(Record obj) {
		String mobile = obj.getStr("mobile");
		Record user = Db.findFirst(
				"select * from sl_user_info where mobile=? and type=1", mobile);
		if (user != null) {
			obj.set("user_id", user.get("id"));
			obj.remove("mobile");
			obj.remove("nick");
			//obj.remove("id");
			//obj.set("create_time", new Date());
			return Db.update("sl_goods_info", "id",obj);
		} else {
			return false;
		}
	}

	
	@Override
	public List<Record> getCompanyCars(int id,String mobile) {
		Record good = Db.findById("sl_goods_info", "id", id);
		String starplace = good.getStr("startplace");
		String endplace = good.getStr("endplace");
		String sql = "select a.nick,a.mobile,b.address,b.carmodels,b.plateno,b.carload,b.id from sl_user_info a , sl_user_truck b where a.type = 2 and a.attach = 2 and a.id = b.user_id and (b.address = ? or b.address = ? ) limit 0,10";
		List<Record> data = Db.find(sql, starplace,endplace);
		for (Record temp : data) {
			temp.set("hy_id", id);
			temp.set("hy_tel", mobile);
		}
		
		
		return data;
	}

	@Override
	public boolean addInfoToWayBill(int hyid, String tel, int carid,
			String mobile) {
		// 1 根据货源信息 生成一条车源信息
		Record good = Db.findById("sl_goods_info", "id", hyid);
		Record carY = new Record();
		carY.set("trucks_id", carid);
		carY.set("loadtime", good.get("loadtime"));
		carY.set("startplace", good.get("startplace"));
		carY.set("endplace", good.get("endplace"));
		carY.set("create_time", new Date());
		boolean flag1 = Db.save("sl_trucks_info", carY);
		if (flag1) {
			//2 生成运单
			Record waybill = new Record();
			waybill.set("trucks_id", carY.get("id"));
			waybill.set("goods_id", hyid);
			waybill.set("cost", good.getDouble("freight"));
			waybill.set("createtime", new Date());
			waybill.set("paystate", 0);
			waybill.set("isloading", 0);
			boolean flag = Db.save("sl_waybill", waybill);
			if(flag){
				String sql = "update sl_goods_info set type=2 where id=?";
				Db.update(sql,hyid);
				/**
				 * 发送短信通知双方
				 */
				return true ;
			}else{
				return  false;
			}
			
		} else {
			return false;
		}
	}

	@Override
	public boolean addSocietyCarInfoToWayBill(int hyid, String tel,
			String mobile) {
		//车主
		Record user = Db.findFirst("select * from sl_user_info where mobile = ?",tel);
		//货源
		Record goods = Db.findById("sl_goods_info", "id", hyid);
		
		//车源
		List<Record> cars = Db.find("select a.* from sl_user_truck a where a.user_id = ?  ",user.get("id")) ;
		if(cars!=null && cars.size() >0){
			//发送短信
			return true;
		}else{
			return false;
		}
		//return false;
	}


}
