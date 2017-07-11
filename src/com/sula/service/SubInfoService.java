package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface SubInfoService {

	/**
	 * 广告列表
	 */
	public Page<Record> getAdvertList(int page);
	/**
	 * 新增广告信息
	 * @param advert
	 * @return
	 */
	public boolean addAdvert(Record advert);
	/**
	 * 修改广告信息
	 * @param id
	 * @param obj
	 * @return
	 */
	public int updateAdvert(Record obj);
	/**
	 * 删除广告信息
	 * @param id
	 * @return
	 */
	public int delAdvert(int id);
	/**
	 * 获取一条广告信息
	 * @param id
	 * @return
	 */
	public Record getAdvertByID(int id);
	/**
	 * 资讯列表
	 */
	public Page<Record> getConsultList(int page);
	public boolean addConsult(Record obj);
	public int updateConsult(Record obj);
	public int delConsult(int id);
	public Record getConsultById(int id);
	/**
	 * 帮助信息
	 */
	public Page<Record> getAssistList(int page);
	public boolean addAssist(Record obj);
	public int updateAssist(Record obj);
	public int delAssist(int id);
	public Record getAssistById(int id);
	
}
