package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@AutoInstance
public interface WayBillService {

    Page<Record> getWayBillList(int page,String keyword);

    /**
     * 生成运单
     * @param goodId 货源ID
     * @param truckId 车源ID
     * @param cost 运单价格
     * @return
     */
    int initWaybill(Integer goodId, Integer truckId, String cost);

    /**
     * 根据运单ID查询运单
     * @param waybillId 运单ID
     * @return
     */
    Record selectWaybill(Integer waybillId);

    /**
     * 变更运单状态
     * @param waybillId 运单ID
     * @param i 状态
     * @return
     */
    int modifyWaybillState(Integer waybillId, int i);

    /**
     * 添加运单评价
     * @param waybillId 运单ID
     * @param userId 评价人ID
     * @param evaluationContent 评价内容
     * @return
     */
    int addWaybillEvaluation(Integer waybillId, Integer userId, String evaluationContent,Integer score);
}
