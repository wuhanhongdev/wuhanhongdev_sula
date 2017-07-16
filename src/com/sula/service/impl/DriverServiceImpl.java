package com.sula.service.impl;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.spatial4j.core.io.GeohashUtils;
import com.sula.model.Truck;
import com.sula.model.TruckInfo;
import com.sula.service.DriverService;
import com.sula.util.Status;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

public class DriverServiceImpl implements DriverService {
    @Override
    public int insertCarInfo(Truck truck) {
        Record record = new Record();
        record.set("user_id",truck.getUserId());
        record.set("address",truck.getAddress());
        record.set("plateno",truck.getPlateno());
        record.set("carlength",truck.getCarLength());
        record.set("carmodels",truck.getCarModels());
        record.set("carload",truck.getCarLoad());
        record.set("img",truck.getImg());
        record.set("img2",truck.getImg2());
        record.set("img3",truck.getImg3());
        record.set("img4",truck.getImg4());
        record.set("create_time",truck.getCreateTime());
        boolean flag = Db.save("sl_user_truck",record);
        return flag ? Status.success : Status.fail;
    }

    @Override
    public List<Record> queryCarByUserId(Integer userId) {
        StringBuffer querySQL = new StringBuffer("select *," +
                "(select dict.value from sl_app_dict dict where dict.type='car-length' and dict.key=truck.carlength) as length,"+
                "(select dict.value from sl_app_dict dict where dict.type='car-model' and dict.key=truck.carmodels) as model"+
                " from sl_user_truck truck where truck.user_id="+userId);

        return Db.find(querySQL.toString());
    }

    @Override
    public int insertTruckInfo(TruckInfo info) {
        String geoCode = GeohashUtils.encodeLatLon(info.getStartLon(),info.getStartLat());
        String endGeoCode = GeohashUtils.encodeLatLon(info.getEndLon(),info.getEndLat());
        info.setCreateTime(new Date());
        Record record = new Record();
        record.set("trucks_id",info.getTruckId());
        record.set("loadtime",info.getLoadTime());
        record.set("startplace",info.getStartPlace());
        record.set("endplace",info.getEndPlace());
        record.set("create_time",info.getCreateTime());
        record.set("start_lon",info.getStartLon());
        record.set("start_lat",info.getStartLat());
        record.set("start_geo_code",geoCode);
        record.set("end_lon",info.getEndLon());
        record.set("end_lat",info.getEndLat());
        record.set("end_geo_code",endGeoCode);
        boolean flag = Db.save("sl_trucks_info",record);
        return flag ? Status.success : Status.fail;
    }

    @Override
    public int driverVerify(Record record) {
        boolean flag = Db.save("sl_acc_info",record);
        return flag ? Status.success : Status.fail;
    }

    @Override
    public int addReplayInfo(Integer userId, Integer wayBillId, Integer score, String contents) {
        Record record = new Record();
        record.set("userid",userId);
        record.set("waybillid",wayBillId);
        record.set("score",score);
        record.set("contents",contents);
        record.set("createtime",new Date());
        boolean flag = Db.save("sl_waybill_replay",record);
        return flag ? Status.success : Status.fail;
    }

    @Override
    public Page<Record> loadReplayInfo(int page,int sourceId) {
        String sql = "SELECT replay.*,userinfo.nick as username,userinfo.img as avatar ";
        String exSql = " LEFT JOIN sl_waybill_replay replay on replay.waybillid = waybill.id";
        exSql += " LEFT JOIN sl_user_info userinfo on userinfo.id = replay.userid";
        exSql += " where waybill.trucks_id="+sourceId;
        return Db.paginate(page,Status.appPageSize,sql,exSql);
    }

    @Override
    public Page<Record> findDriver(double lonValue, double latValue,int page) {
        //计算出前三位geo_code  默认查询一百五十公里内
        String geoCode = GeohashUtils.encodeLatLon(lonValue, latValue, 3);
        StringBuffer selectSQL = buildSelectSQL();
        StringBuffer querySQL = buildQuerySQL(geoCode);
        return Db.paginate(page,Status.appPageSize,selectSQL.toString(),querySQL.toString());
    }

    @Override
    public Page<Record> searchDrivers(String startPatten, String endPatten, Integer carLength, String loadTime, int page) {
        StringBuffer selectSQL = buildSearchDriversSelect();
        StringBuffer querySQL = buildSearchDriversQuery(startPatten,endPatten,carLength,loadTime);
        return Db.paginate(page,Status.appPageSize,selectSQL.toString(),querySQL.toString());
    }

    private StringBuffer buildSearchDriversQuery(String startPatten, String endPatten, Integer carLength, String loadTime) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" FROM");
        stringBuffer.append(" (SELECT * FROM sl_trucks_info WHERE 1=1 ");
        if(StringUtils.isNotEmpty(startPatten)){
            stringBuffer.append(" and start_geo_code like '"+startPatten+"%'");
        }
        if(StringUtils.isNotEmpty(endPatten)){
            stringBuffer.append(" and end_geo_code like '"+endPatten+"%'");
        }
        if(StringUtils.isNotEmpty(loadTime)){
            stringBuffer.append(" and loadtime='"+loadTime+"'");
        }
        stringBuffer.append(" ) trucks");
        stringBuffer.append(" LEFT JOIN sl_user_truck truck ON truck.id = trucks.trucks_id");
        stringBuffer.append(" LEFT JOIN sl_user_info userinfo ON userinfo.id = truck.user_id where 1=1");
        if(carLength != null){
            stringBuffer.append(" and truck.carlength ='"+carLength+"'");
        }

        return stringBuffer;
    }

    private StringBuffer buildSearchDriversSelect() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT ");
        stringBuffer.append("   trucks.id AS id,");
        stringBuffer.append("   trucks.trucks_id  AS trucksId,");
        stringBuffer.append("   trucks.startplace AS startPlace,");
        stringBuffer.append("   trucks.endplace AS endPlace,");
        stringBuffer.append("   trucks.loadtime as loadTime,");
        stringBuffer.append("   trucks.start_lon as startLon,");
        stringBuffer.append("   trucks.start_lat as startLat,");
        stringBuffer.append("   trucks.create_time as createTime,");
        stringBuffer.append("   (select dict.value from sl_app_dict dict where dict.type='car-length' and dict.key=truck.carlength) as carLength,");
        stringBuffer.append("   (select dict.value from sl_app_dict dict where dict.type='car-model' and dict.key=truck.carmodels) as carModels,");
        stringBuffer.append("   truck.carload as carLoad,");
        stringBuffer.append("   userInfo.img AS image,");
        stringBuffer.append("   userInfo.mobile as phone");
        return stringBuffer;
    }

    private StringBuffer buildQuerySQL(String geoCode) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" from (SELECT * FROM sl_trucks_info  WHERE start_geo_code LIKE '"+geoCode+"%') trucks");
        stringBuffer.append(" LEFT JOIN sl_user_truck truck ON truck.id = trucks.trucks_id");
        stringBuffer.append(" LEFT JOIN sl_user_info userinfo ON userinfo.id = truck.user_id");
        return stringBuffer;
    }

    private StringBuffer buildSelectSQL() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT ");
            stringBuffer.append("   trucks.id as id,");
        stringBuffer.append("   trucks.trucks_id as trucksId,");
        stringBuffer.append(" trucks.startplace as startPlace,");
        stringBuffer.append(" trucks.endplace as endPlace,");
        stringBuffer.append(" trucks.loadtime as loadTime,");
        stringBuffer.append(" trucks.start_lon as startLon,");
        stringBuffer.append(" trucks.start_lat as startLat,");
        stringBuffer.append(" trucks.end_lon as endLon,");
        stringBuffer.append(" trucks.end_lat as endLat,");
        stringBuffer.append(" (SELECT  dict.value from sl_app_dict dict WHERE dict.key = truck.carlength and dict.type='car-length') as carLength,");
        stringBuffer.append(" (SELECT  dict.value from sl_app_dict dict WHERE dict.key = truck.carmodels and dict.type='car-models') as carModels,");
        stringBuffer.append(" truck.carload as carLoad,");
        stringBuffer.append(" userinfo.img as image,");
        stringBuffer.append(" userinfo.mobile as phone");

        return stringBuffer;
    }

    @Override
    public Record selectTruckInfo(Integer driverId) {
        String selectSQL = "SELECT truck.*," +
                "(select dict.value from sl_app_dict dict where dict.type='car-length' and dict.key=truck.carlength) as carLength," +
                "(SELECT  dict.value from sl_app_dict dict WHERE dict.key = truck.carmodels and dict.type='car-models') as carModels," +
                "userInfo.img as profile,userInfo.mobile as phone" +
                " from (SELECT * FROM sl_user_truck WHERE id="+driverId+") truck" +
                " LEFT JOIN sl_user_info userInfo ON userInfo.id = truck.user_id";
        return Db.findFirst(selectSQL);
    }


    @Override
    public Record selectTrucksInfo(Integer trucksId) {
        String selectSQL = "SELECT * FROM sl_trucks_info WHERE id="+trucksId;
        return Db.findFirst(selectSQL);
    }

    @Override
    public Record selectRateInfo(Integer trucksId) {
        String selectSQL = "SELECT count(*) as waybillTimes,(SELECT avg(score) " +
                "FROM sl_waybill_replay WHERE waybillid = waybill.id) as rate " +
                "FROM sl_waybill waybill WHERE waybill.trucks_id="+trucksId;
        return Db.findFirst(selectSQL);
    }

    @Override
    public Page<Record> loadRepay(Integer trucksId, Integer page) {
        String selectSQL = "SELECT replay.contents,replay.createtime,userinfo.nick,userinfo.img ";
        String querySQL = "FROM (SELECT * from sl_waybill WHERE trucks_id="+trucksId+") waybill " +
                "LEFT JOIN sl_waybill_replay replay ON replay.waybillid = waybill.id " +
                "LEFT JOIN sl_user_info userinfo on userinfo.id = replay.userid";
        return Db.paginate(page,Status.appPageSize,selectSQL,querySQL);
    }

    @Override
    public Record selectMimeWaybill(Integer userId, String type) {
        return null;
    }
}
