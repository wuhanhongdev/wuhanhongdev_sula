package com.sula.service.impl;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.LoginService;
import com.sula.util.Status;

/**
 * 
 * Description : 
 * @author 冯沛
 * @date 2017年5月19日
 */
public class LoginServiceImpl implements LoginService {

	@Override
	public Record login(String name, String password) {
		List<Record> list = Db.find("select * from sl_user_info where mobile = ? and password = ? ",name,password);
		if( list.size()>0 ) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<Record> getModules(String roleId) {
		String find_top = " select * from shop_module where id in (select module from shop_role_module where role= ? ) and parent =0 order by orderno asc";
		List<Record> module_list = Db.find(find_top,roleId);
		if(module_list!=null&&module_list.size()>0) {
			for (int i = 0; i < module_list.size(); i++) {
				Record module_top = module_list.get(i);
				String moduleid = String.valueOf(module_top.get("id"));	// 当前父级ID
				String find_child = " select * from shop_module where parent = ? and id in ( select module from shop_role_module where role= ? )  order by orderno asc ";
				List<Record> child_module = Db.find(find_child,moduleid,roleId);
				module_top.set("child", child_module);
			}
			return module_list;
		}
		return null;
	}

	/**
	 * @param record 用户信息
	 * @return
	 */
	@Override
	public int saveLoginUser(Record record) {
		Record obj = Db.findFirst("select * from sl_user_info where mobile=?",record.getStr("mobile"));
		if(obj == null){//新增
			boolean flag = Db.save("sl_user_info", record);

			return flag ? Status.success : Status.fail;
		}else{//更新
			record.set("id",obj.getInt("id"));
			record.set("isvip",obj.getStr("isvip"));
			record.set("img",obj.getStr("img"));
			record.set("nick",obj.getStr("nick"));
			record.set("pay_pwd",obj.getStr("pay_pwd"));
			record.set("type",obj.getInt("type"));
			record.set("create_time",obj.getDate("create_time"));
			record.set("balance",obj.getDouble("balance"));

			boolean flag = Db.update("sl_user_info",record);

			return flag ? Status.success : Status.fail;
		}
	}

	@Override
	public List<Record> loadBanners() {
		StringBuffer querySQL = new StringBuffer("select * from sl_app_banner where status = 1");
		return Db.find(querySQL.toString());
	}

	@Override
	public List<Record> queryDicts(String dictType) {
		StringBuffer querySQL = new StringBuffer("select * from sl_app_dict where state = 1 and type='"+dictType+"'");
		return Db.find(querySQL.toString());
	}

	@Override
	public Record loginAdmin(String account, String password) {
		List<Record> list = Db.find("select * from sl_admin where account = ? and password = ? ",account,password);
		if( list.size()>0 ) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
