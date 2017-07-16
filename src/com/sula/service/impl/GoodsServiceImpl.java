package com.sula.service.impl;

import ch.hsr.geohash.GeoHash;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.spatial4j.core.io.GeohashUtils;
import com.sula.model.Goods;
import com.sula.service.GoodsService;
import com.sula.util.Status;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsServiceImpl implements GoodsService {
    @Override
    public Map<String,Object> insertGoods(Goods goods) {
        Map<String,Object> resultMap = new HashMap<>();
        //取到经纬度计算geohash编码
        String geoCode = GeohashUtils.encodeLatLon(goods.getStartLon(),goods.getStartLat());
        String endGeoCode = GeohashUtils.encodeLatLon(goods.getEndLon(),goods.getEndLat());
        Record record = new Record();
        record.set("user_id",goods.getUserId())
        .set("loadtime",goods.getLoadTime())
        .set("startplace",goods.getStartPlace())
        .set("endplace",goods.getEndPlace())
        .set("carlength",goods.getCarLength())
        .set("carmodels",goods.getCarModels())
        .set("goods",goods.getGoods())
        .set("weights",goods.getWeights())
        .set("dulk",goods.getDulk())
        .set("message",goods.getMessage())
        .set("freight",goods.getFreight())
        .set("create_time",new Date())
        .set("start_lon",goods.getStartLon())
        .set("start_lat",goods.getStartLat())
        .set("end_lon",goods.getStartLon())
        .set("end_lat",goods.getStartLat())
        .set("start_geo_code",geoCode)
        .set("end_geo_code",endGeoCode)
        .set("type",1);

        boolean flag = Db.save("sl_goods_info", record);
        resultMap.put("success",flag ? Status.success : Status.fail);
        resultMap.put("goodId",record.get("id"));
        return resultMap;
    }

    @Override
    public int shipperVerify(Record record) {
        boolean flag = Db.save("sl_acc_info", record);

        return flag ? Status.success : Status.fail;
    }

    @Override
    public Record searchGoodById(Integer goodId) {

        return Db.findById("sl_goods_info",goodId);
    }

    @Override
    public int increasePrice(Integer goodId, String price) {
        Record record = Db.findById("sl_goods_info",goodId);
        if(record == null){
            return Status.fail;
        }

        Double freight = record.getDouble("freight");
        Double increasePrice = Double.valueOf(price);
        if(freight >= increasePrice){
            return 2;
        }
        record.set("freight",increasePrice);
        boolean flag = Db.update("sl_goods_info",record);
        return flag ? Status.success : Status.fail;
    }

    @Override
    public Page<Record> searchGoods(String startPatten, String endPatten, Integer carLength, String loadTime, int page) {
        String selectSQL = "SELECT goods.id,goods.startplace,goods.endplace,goods.loadtime,goods.start_lon as startLon,goods.start_lat as startLat,goods.goods,goods.freight,goods.create_time,userinfo.img," +
                " (SELECT dict.value FROM sl_app_dict dict WHERE dict.type='car-length' and dict.key = goods.carlength) as  carLength," +
                " (SELECT dict.value FROM sl_app_dict dict WHERE dict.type='car-model' and dict.key = goods.carmodels) as  carModel";
        String querySQL = " FROM sl_goods_info goods" +
                " LEFT JOIN sl_user_info userinfo ON userinfo.id = goods.user_id where 1=1 ";
        if(StringUtils.isNotEmpty(startPatten)){
            querySQL += " AND goods.start_geo_code like '"+startPatten+"%'";
        }
        if(StringUtils.isNotEmpty(endPatten)){
            querySQL += " AND goods.end_geo_code like '"+endPatten+"%'";
        }
        if(carLength != null){
            querySQL += " and goods.carlength='"+carLength+"%'";
        }
        if(loadTime != null){
            querySQL += " goods.loadtime='"+loadTime+"%'";
        }

        return Db.paginate(page,Status.appPageSize,selectSQL,querySQL);
    }

    @Override
    public Page<Record> loadMineGoods(Integer userId, Integer page) {
        String selectSQL = "SELECT * ";
        String querySQL = "FROM sl_goods_info WHERE type=1 and user_id="+userId;
        return Db.paginate(page,Status.appPageSize,selectSQL,querySQL);
    }

    @Override
    public Record selectRateInfo(Integer userId) {
        String selectSQL = "SELECT count(*) as waybillTimes, " +
                "(SELECT avg(score) FROM sl_waybill_replay WHERE waybillid = waybill.id) as rate " +
                "FROM sl_waybill waybill WHERE waybill.goods_id in(SELECT id FROM sl_goods_info WHERE user_id="+userId+")";
        return Db.findFirst(selectSQL);
    }

    @Override
    public Record selectGoodsById(Integer goodsId) {
        String selectSQL = "SELECT goods.*,userinfo.img," +
                "(SELECT dict.value FROM sl_app_dict dict WHERE dict.type='car-length' and dict.key = goods.carlength) as  carLength," +
                "(SELECT dict.value FROM sl_app_dict dict WHERE dict.type='car-model' and dict.key = goods.carmodels) as  carModel" +
                " FROM sl_goods_info goods "+
                " LEFT JOIN sl_user_info userinfo ON userinfo.id = goods.user_id" +
                " where goods.id="+goodsId;
        List<Record> records = Db.find(selectSQL);
        return records.size() > 0 ? records.get(0) : null;
    }

    @Override
    public Page<Record> loadReplay(Integer userId, Integer page) {
        String selectSQL = "SELECT replay.contents,replay.createtime,userinfo.nick,userinfo.img ";
        String querySQL = " FROM sl_waybill waybill" +
                " LEFT JOIN sl_waybill_replay replay ON replay.waybillid = waybill.id" +
                " LEFT JOIN sl_user_info userinfo on userinfo.id = replay.userid" +
                " WHERE waybill.goods_id in(SELECT id FROM sl_goods_info WHERE user_id="+userId+") and replay.contents is not null";
        return Db.paginate(page,Status.appPageSize,selectSQL,querySQL);
    }

    @Override
    public Record selectMimeWaybill(Integer userId, String type) {
        return null;
    }

    public static void main(String[] args){
        //中国上海市上海市奉贤区
        double lon = 121.485796;
        double lat = 30.947010;
        String geoCode = GeohashUtils.encodeLatLon(lat, lon, 5);
        System.out.println("geoCode====>"+geoCode);
        GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lon, 10);
        System.out.println("geoHash====>"+geoHash.toBase32());
        GeoHash[] adjacent = geoHash.getAdjacent();
        for (GeoHash hash : adjacent) {
            System.out.println("hash.toBase32()====>"+hash.toBase32());
        }
    }
}
