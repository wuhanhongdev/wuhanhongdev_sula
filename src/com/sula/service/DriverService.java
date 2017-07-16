package com.sula.service;

import com.fengpei.ioc.AutoInstance;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sula.model.Truck;
import com.sula.model.TruckInfo;

import java.util.List;

@AutoInstance
public interface DriverService {

    /**
     * 将车辆信息入库
     * @param truck
     * @return
     */
    int insertCarInfo(Truck truck);

    /**
     * 根据用户ID获取个人车辆
     * @param userId
     * @return
     */
    List<Record> queryCarByUserId(Integer userId);

    /**
     * 发布车源
     * @param info
     * @return
     */
    int insertTruckInfo(TruckInfo info);

    /**
     * 车主实名认证
     * @param record
     * @return
     */
    int driverVerify(Record record);

    /**
     * 添加对车源评价
     * @return
     */
    int addReplayInfo(Integer userId, Integer wayBillId, Integer score, String contents);

    /**
     * 获取车源评价
     * @param page
     * @return
     */
    Page<Record> loadReplayInfo(int page,int sourceId);

    /**
     * 根据经纬度获取推荐车源
     * @param lonValue
     * @param latValue
     * @return
     */
    Page<Record> findDriver(double lonValue, double latValue,int page);

    /**
     * 查找车源
     * @param startPatten 出发地匹配值
     * @param endPatten 目的地匹配值
     * @param carLength 车长
     * @param carModel 车型
     * @param page
     * @return
     */
    Page<Record> searchDrivers(String startPatten, String endPatten, Integer carLength, String loadTime, int page);

    /**
     * 查询车辆信息
     * @param driverId 车辆ID
     * @return
     */
    Record selectTruckInfo(Integer driverId);

    /**
     * 获取车源信息
     * @param trucksId 车源ID
     * @return
     */
    Record selectTrucksInfo(Integer trucksId);

    /**
     * 获取运单次数和评星
     * @param trucksId 运单ID
     * @return
     */
    Record selectRateInfo(Integer trucksId);

    /**
     * 加载货主评价
     * @param trucksId
     * @return
     */
    Page<Record> loadRepay(Integer trucksId, Integer page);

    /**
     * 查询司机运单
     * @param userId 司机ID
     * @param type 查询类型
     * @return
     */
    Page<Record> selectMimeWaybill(Integer userId, String type,Integer page);
}
