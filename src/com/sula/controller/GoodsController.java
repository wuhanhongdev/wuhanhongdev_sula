package com.sula.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengpei.ioc.Controller;
import com.sula.model.Goods;
import com.sula.service.GoodsService;
import com.sula.util.ResultJson;
import com.sula.util.Status;
import com.sula.util.Verify;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 查找货源
     */
    public void searchGoos(){

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
