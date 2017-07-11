package com.sula.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.service.CreditScoreService;
import com.sula.util.Status;

public class CreditScoreServiceImpl implements CreditScoreService {

	@Override
	public Record getCreditObject() {
		return Db.findFirst("select * from sl_integral_rule");
		
	}

	@Override
	public boolean modifyCreditObject(boolean flag , Record obj) {
		boolean returnFlag = false ;
		if(flag){
			//新增动作
			returnFlag = Db.save("sl_integral_rule", obj);
		}else{
			//修改动作
			returnFlag = Db.update("sl_integral_rule", "id", obj);
		}
		return returnFlag;
	}

	@Override
	public Page<Record> getCreditUser(int page,int creditState) {
		
		String sql = "select *";
		String exSql = " from sl_user_credit_info_view ";
		if(creditState!=0&&creditState!=1) {
			exSql = exSql + " where ";
			if(creditState==1) {
				
			} else if(creditState==2) {
				exSql = exSql + " iswarning = 1 ";
			} else if(creditState==3) {
				exSql = exSql + " ispublish = 0 ";
			} else if(creditState==4) {
				exSql = exSql + " isorder = 0 ";
			}
		}
		exSql = exSql + " order by credit asc ";
		Page<Record> data = Db.paginate(page, Status.pageSize, sql, exSql);
		return data;
	}

	@Override
	public boolean modifyAccountState(int id, int state) {
		String sql = " update sl_user_info set state = "+state+" where id = " + id;
		int i = Db.update(sql);
		if(i>0) {
			return true;
		} else {
			return false;
		}
	}

}
