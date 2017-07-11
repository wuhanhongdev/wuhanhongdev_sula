package com.sula.service.impl;

import ch.hsr.geohash.GeoHash;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.spatial4j.core.io.GeohashUtils;
import com.sula.model.Goods;
import com.sula.service.GoodsService;
import com.sula.util.Status;

import java.util.Date;
import java.util.HashMap;
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
