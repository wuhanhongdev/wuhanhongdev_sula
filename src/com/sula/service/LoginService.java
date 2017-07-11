package com.sula.service;

import java.util.List;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * Description : 
 * @author 冯沛
 * @date 2017年5月19日
 */
@AutoInstance
public interface LoginService {

	public Record login(String account,String pwd);
	
	/**
	 * 后台登录
	 * @param account
	 * @param password
	 * @return
	 */
	public Record loginAdmin(String account,String password);
	/**
	 * 根据用户名,查询拥有权限的模块
	 * @return
	 */
	public List<Record> getModules(String roleId);

	/**
	 * 注册用户
	 * @param record 用户信息
	 * @return
	 */
	public int saveLoginUser(Record record);

	List<Record> loadBanners();

	/**
	 * 获取字典信息
	 * @param dictType 字典类型
	 * @return
	 */
    List<Record> queryDicts(String dictType);
}
