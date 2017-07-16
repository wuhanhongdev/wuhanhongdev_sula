package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
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

    /**
     * 主动找货
     * @param startPatten 起点匹配
     * @param endPatten 目的地匹配
     * @param carLength 车长
     * @param loadTime 装货时间
     * @param page 页码
     * @return
     */
    Page<Record> searchGoods(String startPatten, String endPatten, Integer carLength, String loadTime, int page);

    /**
     * 后去我的货源/type=1
     * @param userId
     * @param page
     * @return
     */
    Page<Record> loadMineGoods(Integer userId, Integer page);

    /**
     * 获取货主评星
     * @param userId 货主
     * @return
     */
    Record selectRateInfo(Integer userId);

    /**
     * 根据ID查询货源
     * @param goodsId
     * @return
     */
    Record selectGoodsById(Integer goodsId);

    /**
     * 查询货主评价
     * @param userId
     * @param page
     * @return
     */
    Page<Record> loadReplay(Integer userId, Integer page);

    /**
     * 查询货主运单
     * @param userId 货主ID
     * @param type 查询类型
     * @return
     */
    Record selectMimeWaybill(Integer userId, String type);
}
