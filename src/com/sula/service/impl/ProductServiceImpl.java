package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.ProductService;
import com.sula.util.Status;

public class ProductServiceImpl implements ProductService {

	@Override
	public Page<Record> getProductList(int page) {
		String sql = "select *";
		String sqlEx = " from sl_product_info order by create_time desc";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, sqlEx);
		return data;
	}

	@Override
	public int addProduct(Record obj) {
		boolean i = Db.save("sl_product_info", obj);
		if(i){
			return 1 ;
		}else{
			return 0;
		}
	}

	@Override
	public boolean modifyProductById(Record obj) {
		boolean i = Db.update("sl_product_info","id", obj);
		return i;
	}

	@Override
	public int delProductById(int id) {
		return Db.update("delete from sl_product_info where id=?",id);
	}

	@Override
	public Record getOneProductById(int id) {
		return Db.findById("sl_product_info", "id", id);
	}

	@Override
	public int updateProductById(int id) {
		Record obj = Db.findById("sl_product_info", "id", id);
		int is_show = obj.getInt("is_show");
		if(is_show == 0){
			is_show = 1;
		}else{
			is_show = 0;
		}
		return Db.update("update sl_product_info set is_show=? where id=?",is_show,id);
	}

}
