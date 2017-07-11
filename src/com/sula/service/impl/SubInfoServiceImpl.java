package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.SubInfoService;
import com.sula.util.Status;

public class SubInfoServiceImpl implements SubInfoService {

	/**
	 * (non-Javadoc)
	 * @see com.sula.service.SubInfoService#getAdvertList(int)
	 * 广告编辑开始
	 */
	@Override
	public Page<Record> getAdvertList(int page) {
		String sql = "select *";
		String exSql = " from sl_sub_info where type = 1 order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean addAdvert(Record advert) {
		boolean flag = Db.save("sl_sub_info", advert);
		return flag;
	}

	@Override
	public int updateAdvert(Record obj) {
		boolean flag = Db.update("sl_sub_info", obj);
		if(flag){
			return 1 ;
		}else{
			return 0;
		}
	}

	@Override
	public int delAdvert(int id) {
		int i = Db.update("delete from sl_sub_info where id =?",id);
		return i;
	}
    /**
     * 广告编辑结束
     */

	@Override
	public Record getAdvertByID(int id) {
		Record obj = Db.findById("sl_sub_info", id);
		return obj;
	}

	@Override
	public Page<Record> getConsultList(int page) {
		String sql = "select *";
		String exSql = " from sl_sub_info where type = 2 order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean addConsult(Record obj) {
		boolean flag = Db.save("sl_sub_info", obj);
		return flag;
	}

	@Override
	public int updateConsult(Record obj) {
		boolean flag = Db.update("sl_sub_info", obj);
		if(flag){
			return 1 ;
		}else{
			return 0;
		}
	}

	@Override
	public int delConsult(int id) {
		int i = Db.update("delete from sl_sub_info where id =?",id);
		return i;
	}

	@Override
	public Record getConsultById(int id) {
		Record obj = Db.findById("sl_sub_info", id);
		return obj;
	}
	/**
	 * 帮助信息
	 */
	@Override
	public Page<Record> getAssistList(int page) {
		String sql = "select *";
		String exSql = " from sl_sub_info where type = 3 order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean addAssist(Record obj) {
		boolean flag = Db.save("sl_sub_info", obj);
		return flag;
	}

	@Override
	public int updateAssist(Record obj) {
		boolean flag = Db.update("sl_sub_info", obj);
		if(flag){
			return 1 ;
		}else{
			return 0;
		}
	}

	@Override
	public int delAssist(int id) {
		int i = Db.update("delete from sl_sub_info where id =?",id);
		return i;
	}

	@Override
	public Record getAssistById(int id) {
		Record obj = Db.findById("sl_sub_info", id);
		return obj;
	}
}
