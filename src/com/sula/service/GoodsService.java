package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Record;
import com.sula.model.Goods;

import java.util.Map;

/**
 * 货物服务
 */
@AutoInstance
public interface GoodsService {

    /**
     * 插入货源信息
     * @param goods 货源信息
     * @return
     */
    Map<String,Object> insertGoods(Goods goods);

    /**
     * 货主实名认证
     * @param record
     * @return
     */
    int shipperVerify(Record record);

    /**
     * 查询货源信息
     * @param goodId 货源ID
     * @return
     */
    Record searchGoodById(Integer goodId);

    /**
     * 货主加价
     * @param goodId 货源ID
     * @param price 加价价格
     * @return
     */
    int increasePrice(Integer goodId, String price);
}
