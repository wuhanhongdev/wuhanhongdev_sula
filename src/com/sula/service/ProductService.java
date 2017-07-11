package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface ProductService {

	/**
	 * 商品列表
	 * @param page
	 * @return
	 */
	public Page<Record> getProductList(int page);
	/**
	 * 添加商品
	 * @param obj
	 * @return
	 */
	public int addProduct(Record obj);
	public Record getOneProductById(int id);
	/**
	 * 修改
	 * @param obj
	 */
	public boolean modifyProductById(Record obj);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delProductById(int id);
	/**
	 * 
	 */
	public int updateProductById(int id);
}
