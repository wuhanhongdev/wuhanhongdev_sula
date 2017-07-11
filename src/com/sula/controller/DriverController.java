package com.sula.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengpei.ioc.Controller;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.spatial4j.core.io.GeohashUtils;
import com.sula.config.Config;
import com.sula.model.Truck;
import com.sula.model.TruckInfo;
import com.sula.omparator.DistanceComparator;
import com.sula.service.ComplainService;
import com.sula.service.DriverService;
import com.sula.service.GoodsService;
import com.sula.service.impl.DriverServiceImpl;
import com.sula.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 司机接口
 */
public class DriverController extends Controller {

    DriverService driverService;
    ComplainService complainService;
    GoodsService goodsService;

    /**
     * 车源推荐/查找车源
     */
    public void findDriver(){
        ResultJson json = new ResultJson();
        //货源ID
        Integer goodId = getParaToInt("goodId");
        String sign = getPara("sign");
        int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(goodId == null){
            json.setCode(Status.fail);
            json.setMessage("货源ID为空");
        }else{
            if(Verify.isMac(sign)){
                Record good = goodsService.searchGoodById(goodId);
                if(good == null){
                    json.setCode(Status.fail);
                    json.setMessage("货源未查询到");
                }else{
                    double lonValue = good.get("start_lon");
                    double latValue = good.get("start_lat");
                    Page<Record> records = driverService.findDriver(lonValue,latValue,page);
                    List<Record> recordList = records.getList();
                    List<Record> resultList = convertRecords(lonValue, latValue, recordList);
                    Collections.sort(resultList, new DistanceComparator());
                    Page<Record> resultPage = new Page<Record>(
                            resultList,
                            records.getPageNumber(),
                            records.getPageSize(),
                            records.getTotalPage(),
                            records.getTotalRow()
                    );
                    JSONObject resultObj = new JSONObject();
                    resultObj.put("goodInfo",good);
                    resultObj.put("driverInfos",resultPage);

                    json.setCode(Status.success);
                    json.setMessage("查询成功");
                    json.setResult(resultObj);
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    private List<Record> convertRecords(double lonValue, double latValue, List<Record> recordList) {
        List<Record> resultList = new ArrayList<Record>();
        for(Record record : recordList){//计算距离
            double longitude = record.get("startLon");
            double dimension = record.get("startLat");

            double distance = DistUtils.countDistance(lonValue,latValue,longitude,dimension);

            record.set("distance",distance);

            resultList.add(record);
        }
        return resultList;
    }

    /**
     * 获取车源信息
     */
    public void loadDriverInfo(){
        ResultJson json = new ResultJson();
        //车源ID
        Integer driverId = getParaToInt("driverId");
        //车辆ID
        Integer trucksId = getParaToInt("trucksId");

        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(driverId == null){
            json.setCode(Status.fail);
            json.setMessage("车辆ID为空");
        }else if(trucksId == null){
            json.setCode(Status.fail);
            json.setMessage("车源ID为空");
        }else{
            if(Verify.isMac(sign)){
                try{
                    //获取车辆信息
                    Record driverInfo = driverService.selectTruckInfo(driverId);
                    //获取车源信息
                    Record trucksInfo = driverService.selectTrucksInfo(trucksId);
                    //获取车辆评分信息
                    Record rateInfo = driverService.selectRateInfo(trucksId);

                    JSONObject object = new JSONObject();
                    object.put("driverInfo",driverInfo);
                    object.put("trucksInfo",trucksInfo);
                    object.put("rateInfo",rateInfo);

                    json.setCode(Status.success);
                    json.setMessage("查询成功");
                    json.setResult(object);
                }catch (Exception e){
                    e.printStackTrace();
                    json.setCode(Status.fail);
                    json.setMessage("查询失败");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }

        renderJson(json);
    }

    /**
     * 查找就近车源:默认匹配150公里内的所有车源
     */
    public void searchDrivers(){
        ResultJson json = new ResultJson();
        Double startLon = StringUtils.isEmpty(getPara("startLon")) ? null : Double.valueOf(getPara("startLon"));
        Double startLat = StringUtils.isEmpty(getPara("startLat")) ? null : Double.valueOf(getPara("startLat"));

        Double endLon = StringUtils.isEmpty(getPara("endLon")) ? null : Double.valueOf(getPara("endLon"));
        Double endLat = StringUtils.isEmpty(getPara("endLat")) ? null : Double.valueOf(getPara("endLat"));

        Integer carLength = StringUtils.isEmpty(getPara("carLength")) ? null : Integer.valueOf(getPara("carLength"));

        String loadTime = getPara("loadTime");

        int page = getParaToInt("page") == null ? 1 : getParaToInt("page");

        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else{
            if(Verify.isMac(sign)){
                String startPatten = "",endPatten = "";
                if(startLon != null && startLat != null){//计算触发第匹配值

                    startPatten = GeohashUtils.encodeLatLon(startLon, startLat, 3);
                }

                if(endLon != null && endLat != null){//计算目的地匹配值
                    endPatten = GeohashUtils.encodeLatLon(endLon, endLat, 3);
                }

                try{
                    Page<Record> driverInfos = driverService.searchDrivers(startPatten,endPatten,carLength,loadTime,page);
                    List<Record> resultList = convertRecords(startLon, startLat, driverInfos.getList());
                    Collections.sort(resultList, new DistanceComparator());
                    Page<Record> resultPage = new Page<Record>(
                            resultList,
                            driverInfos.getPageNumber(),
                            driverInfos.getPageSize(),
                            driverInfos.getTotalPage(),
                            driverInfos.getTotalRow()
                    );
                    json.setCode(Status.success);
                    json.setMessage("车源信息查询成功");
                    json.setResult(resultPage);
                }catch (Exception e){
                    e.printStackTrace();
                    json.setCode(Status.fail);
                    json.setMessage("车源信息查询出错,请重试");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }

        }

        renderJson(json);

    }

    /**
     * 分页获取车源评价
     */
    public void loadReplayInfo(){
        ResultJson json = new ResultJson();
        Integer sourceId = getParaToInt("sourceId");
        String sign = getPara("sign");
        int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(sourceId == null){
            json.setCode(Status.fail);
            json.setMessage("车源ID为空");
        }else{
            if(Verify.isMac(sign)){
                Page<Record> replays = driverService.loadReplayInfo(page,sourceId);
                if (replays != null) {
                    json.setCode(Status.success);
                    json.setMessage(Status.message_success);
                    json.setResult(replays);
                } else {
                    json.setCode(Status.fail);
                    json.setMessage(Status.message_null);
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    /**
     * 添加货主对车源评价
     */
    public void addReplayInfo(){
        ResultJson json = new ResultJson();
        Integer wayBillId = getParaToInt("wayBillId");
        Integer score = getParaToInt("score");
        String contents = getPara("contents");
        Integer userId = getParaToInt("userId");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(wayBillId == null){
            json.setCode(Status.fail);
            json.setMessage("运单ID不能为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("评价用户ID不能为空");
        }else if(score == null){
            json.setCode(Status.fail);
            json.setMessage("评价分数不能为空");
        }else if(StringUtils.isEmpty(contents)){
            json.setCode(Status.fail);
            json.setMessage("评价内容不能为空");
        }else{
            if(Verify.isMac(sign)){
                int state = driverService.addReplayInfo(userId,wayBillId,score,contents);
                if(state == Status.fail){
                    json.setCode(Status.fail);
                    json.setMessage("评论失败!");
                }else{
                    json.setCode(Status.success);
                    json.setMessage("评论成功");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }


    /**
     * 添加车辆
     */
    public void addCarInfo(){
        ResultJson json = new ResultJson();
        UploadFile uploadFile = getFile("img");
        UploadFile uploadFile2 = getFile("img2");
        UploadFile uploadFile3 = getFile("img3");
        UploadFile uploadFile4 = getFile("img4");

        String carInfo = getPara("carInfo");
        String sign = getPara("sign");

        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(StringUtils.isEmpty(carInfo)){
            json.setCode(Status.fail);
            json.setMessage("车辆信息不能为空");
        }else if(uploadFile == null || uploadFile2 == null || uploadFile3 == null || uploadFile4 == null){
            json.setCode(Status.fail);
            json.setMessage("车辆图片不能为空");
        }else{
            if(Verify.isMac(sign)){
                Truck truck = JSON.parseObject(carInfo,Truck.class);
                Map<String,Object> resultMap = canAdd(truck);
                boolean success = (boolean) resultMap.get("success");
                if(success){
                    //先处理图片
                    String fileName = uploadFile.getFileName();
                    String extentionName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名
                    String newName = GenerateSequenceUtil.generateSequenceNo() + extentionName;// 新名
                    String filePath = Config.relativePath + "truckImgs" + File.separator + newName;

                    String fileName2 = uploadFile2.getFileName();
                    String extentionName2 = fileName2.substring(fileName2.lastIndexOf(".")); // 后缀名
                    String newName2 = GenerateSequenceUtil.generateSequenceNo() + extentionName2;// 新名
                    String filePath2 = Config.relativePath + "truckImgs" + File.separator + newName2;

                    String fileName3 = uploadFile3.getFileName();
                    String extentionName3 = fileName3.substring(fileName3.lastIndexOf(".")); // 后缀名
                    String newName3 = GenerateSequenceUtil.generateSequenceNo() + extentionName3;// 新名
                    String filePath3 = Config.relativePath + "truckImgs" + File.separator + newName3;

                    String fileName4 = uploadFile4.getFileName();
                    String extentionName4 = fileName4.substring(fileName4.lastIndexOf(".")); // 后缀名
                    String newName4 = GenerateSequenceUtil.generateSequenceNo()  + extentionName4;// 新名
                    String filePath4 = Config.relativePath  + "truckImgs" + File.separator + newName4;
                    try {
                        truck.setCreateTime(new Date());

                        FileUtils.copyFile(uploadFile.getFile(),new File(filePath));
                        truck.setImg(newName);//此处仅保存文件名,将文件路径作为工程发布

                        FileUtils.copyFile(uploadFile2.getFile(),new File(filePath2));
                        truck.setImg2(newName2);//此处仅保存文件名,将文件路径作为工程发布

                        FileUtils.copyFile(uploadFile3.getFile(),new File(filePath3));
                        truck.setImg3(newName3);//此处仅保存文件名,将文件路径作为工程发布

                        FileUtils.copyFile(uploadFile4.getFile(),new File(filePath4));
                        truck.setImg4(newName4);//此处仅保存文件名,将文件路径作为工程发布

                        int state = driverService.insertCarInfo(truck);
                        if(state == Status.fail){
                            json.setCode(Status.fail);
                            json.setMessage("车辆添加失败,请重试!");
                        }else{
                            json.setCode(Status.success);
                            json.setMessage("车辆添加成功");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        json.setCode(Status.fail);
                        json.setMessage("车辆图片上传失败");
                    }
                }else{
                    String msg = (String)resultMap.get("msg");
                    json.setCode(Status.fail);
                    json.setMessage(msg);
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }

        renderJson(json);
    }

    /**
     * 获取我的车辆
     */
    public void loadMimeCars(){
        ResultJson json = new ResultJson();
        Integer userId = getParaToInt("userId");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("用户ID不能为空");
        }else{
            if(Verify.isMac(sign)){
                List<Record> records = driverService.queryCarByUserId(userId);
                json.setCode(Status.success);
                json.setMessage("查询成功");
                json.setResult(records);
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }

        renderJson(json);
    }

    /**
     * 发布车源
     */
    public void publishCarInfo(){

        String truckInfo = getPara("truckInfo");
        String sign = getPara("sign");
        ResultJson json = new ResultJson();
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(StringUtils.isEmpty(truckInfo)){
            json.setCode(Status.fail);
            json.setMessage("车源信息不能为空");
        }else{
            if(Verify.isMac(sign)){
                TruckInfo info = JSON.parseObject(truckInfo,TruckInfo.class);
                Map<String,Object> resultMap = canAddTruck(info);
                boolean success = (boolean) resultMap.get("success");
                if(success){
                    int state = driverService.insertTruckInfo(info);
                    if(state == Status.fail){
                        json.setCode(Status.fail);
                        json.setMessage("车源信息发布失败,请重试!");
                    }else{
                        json.setCode(Status.success);
                        json.setMessage("车源信息发布成功");
                    }
                }else{
                    String msg = (String)resultMap.get("msg");
                    json.setCode(Status.fail);
                    json.setMessage(msg);
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    /**
     * 加载货主评价
     */
    public void loadRepay(){
        Integer trucksId = getParaToInt("trucksId");
        Integer page = getParaToInt("trucksId") == null ? 1 : getParaToInt("trucksId");
        String sign = getPara("sign");
        ResultJson json = new ResultJson();
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(trucksId == null){
            json.setCode(Status.fail);
            json.setMessage("车源ID为空");
        }else{
            if(Verify.isMac(sign)){
                Page<Record> records = driverService.loadRepay(trucksId,page);
                json.setCode(Status.success);
                json.setMessage("货主评价加载成功");
                json.setResult(records);
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }
    private Map<String,Object> canAddTruck(TruckInfo info) {
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuffer buffer = new StringBuffer();
        if(StringUtils.isBlank(info.getEndPlace())){
            buffer.append("目的地不能为空   ");
        }

        if(StringUtils.isBlank(info.getStartPlace())){
            buffer.append("出发地不能为空   ");
        }

        if(info.getTruckId() == null){
            buffer.append("车辆ID不能为空   ");
        }

        if(StringUtils.isBlank(info.getLoadTime())){
            buffer.append("装货时间不能为空   ");
        }

        if(StringUtils.isBlank(buffer.toString())){
            map.put("success",true);

        }else{
            map.put("success",false);
            map.put("msg",buffer.toString());
        }

        return map;
    }

    private Map<String,Object> canAdd(Truck truck) {
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuffer buffer = new StringBuffer();

        if(truck.getUserId() == null){
            buffer.append("驾驶员ID不能为空  ");
        }

        if(StringUtils.isBlank(truck.getAddress())){
            buffer.append("地址不能为空  ");
        }

        if(StringUtils.isBlank(truck.getCarLength())){
            buffer.append("车辆长度不能为空  ");
        }

        if(StringUtils.isBlank(truck.getCarLoad())){
            buffer.append("车辆载重不能为空  ");
        }

        if(StringUtils.isBlank(truck.getCarModels())){
            buffer.append("车辆型号不能为空  ");
        }

        if(StringUtils.isBlank(truck.getPlateno())){
            buffer.append("车牌号不能为空  ");
        }

        if(StringUtils.isBlank(buffer.toString())){
            map.put("success",true);

        }else{
            map.put("success",false);
            map.put("msg",buffer.toString());
        }

        return map;
    }


}
