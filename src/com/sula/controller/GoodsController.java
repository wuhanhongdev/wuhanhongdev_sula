package com.sula.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.spatial4j.core.io.GeohashUtils;
import com.sula.model.Goods;
import com.sula.omparator.DistanceComparator;
import com.sula.service.GoodsService;
import com.sula.util.DistUtils;
import com.sula.util.ResultJson;
import com.sula.util.Status;
import com.sula.util.Verify;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class GoodsController extends Controller {

    GoodsService goodsService;

    /**
     * 发布货源 POST
     * param:提交参数json格式
     */
    public void publishGoods(){
        ResultJson json = new ResultJson();
        String goodsInfo = getPara("goodsInfo");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(StringUtils.isEmpty(goodsInfo)){
            json.setCode(Status.fail);
            json.setMessage("货源信息不能为空");
        }else{
            if(Verify.isMac(sign)){
                Goods goods = JSON.parseObject(goodsInfo, Goods.class);
                Map<String,Object> resultMap = canAdd(goods);
                boolean success = (boolean) resultMap.get("success");
                if(success){
                    Map<String,Object> res = goodsService.insertGoods(goods);
                    int state = (Integer) res.get("success");
                    if(state == Status.fail){
                        json.setCode(Status.fail);
                        json.setMessage("货源信息发布失败,请重试!");
                    }else{
                        json.setCode(Status.success);
                        json.setMessage("货源信息发布成功");
                        JSONObject obj = new JSONObject();
                        obj.put("goodId",res.get("goodId"));
                        json.setResult(obj);
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
     * 货主加价
     */
    public void increasePrice(){
        ResultJson json = new ResultJson();
        Integer goodId = getParaToInt("goodId");
        String price = getPara("price");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(goodId == null){
            json.setCode(Status.fail);
            json.setMessage("货源ID为空");
        }else if(StringUtils.isEmpty(price)){
            json.setCode(Status.fail);
            json.setMessage("加价金额为空");
        }else{
            if(Verify.isMac(sign)){
                int state = goodsService.increasePrice(goodId,price);
                if(state == Status.fail){
                    json.setCode(Status.fail);
                    json.setMessage("货源信息发布失败,请重试!");
                }else if(state == 2){
                    json.setCode(Status.fail);
                    json.setMessage("加价金额不能少于原始价格!");
                }else{
                    json.setCode(Status.success);
                    json.setMessage("加价成功");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    public void loadMimeGoods(){
        ResultJson json = new ResultJson();
        Integer userId = getParaToInt("userId");
        Integer page = getParaToInt("page") == null ? 1 : getParaToInt("page");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("用户ID为空");
        }else{
            if(Verify.isMac(sign)){
                Page<Record> records = goodsService.loadMineGoods(userId,page);
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

    public void searchGoods(){
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
        }else if(startLon == null || startLat == null){
            json.setCode(Status.fail);
            json.setMessage("起始位置为空");
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
                    Page<Record> driverInfos = goodsService.searchGoods(startPatten,endPatten,carLength,loadTime,page);
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
                    json.setMessage("货源信息查询成功");
                    json.setResult(resultPage);
                }catch (Exception e){
                    e.printStackTrace();
                    json.setCode(Status.fail);
                    json.setMessage("货源信息查询出错,请重试");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    public void loadGoodsDetail(){
        ResultJson json = new ResultJson();
        Integer goodsId = getParaToInt("goodsId");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(goodsId == null){
            json.setCode(Status.fail);
            json.setMessage("货源ID为空");
        }else{
            if(Verify.isMac(sign)){
                JSONObject object = new JSONObject();
                try{
                    Record goodsInfo = goodsService.selectGoodsById(goodsId);
                    if(goodsInfo != null){
                        Integer userId = goodsInfo.get("user_id");
                        Record rateInfo = goodsService.selectRateInfo(userId);
                        object.put("goodsInfo",goodsInfo);
                        object.put("rateInfo",rateInfo);

                        json.setCode(Status.success);
                        json.setMessage("货源信息查询失成功");
                        json.setResult(object);
                    }else{
                        json.setCode(Status.fail);
                        json.setMessage("无改ID对应的货源");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    json.setCode(Status.fail);
                    json.setMessage("货源信息查询失败");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    public void loadReplay(){
        ResultJson json = new ResultJson();
        Integer userId = getParaToInt("userId");
        String sign = getPara("sign");
        int page = getParaToInt("page") == null ? 1 : getParaToInt("page");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("用户ID为空");
        }else{
            if(Verify.isMac(sign)){
                Page<Record> replays = goodsService.loadReplay(userId, page);
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

    private Map<String,Object> canAdd(Goods goods){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuffer buffer = new StringBuffer();
        if(goods.getUserId() == null){
            buffer.append("用户ID为空  ");
        }
        if(goods.getLoadTime() == null){
            buffer.append("装货时间为空  ");
        }
        if(goods.getStartPlace() == null){
            buffer.append("出发地为空  ");
        }
        if(goods.getEndPlace() == null){
            buffer.append("目的地为空  ");
        }
        if(goods.getCarLength() == null){
            buffer.append("车长度为空");
        }
        if(goods.getCarModels() == null){
            buffer.append("车型号为空");
        }
        if(goods.getGoods() == null){
            buffer.append("货物名称为空");
        }
        if(goods.getMessage() == null){
            buffer.append("给司机留言为空");
        }
        if(goods.getFreight() == null){
            buffer.append("系统生成价格为空");
        }

        if(goods.getStartLon() == null){
            buffer.append("出发地经度为空  ");
        }
        if(goods.getStartLat() == null){
            buffer.append("出发地纬度为空  ");
        }
        if(goods.getEndLon() == null){
            buffer.append("目的地经度为空  ");
        }
        if(goods.getEndLat() == null){
            buffer.append("目的地纬度为空  ");
        }

        if(StringUtils.isEmpty(buffer.toString())){
            map.put("success",true);
        }else{
            map.put("success",false);
            map.put("msg",buffer.toString());
        }

        return map;
    }
}
