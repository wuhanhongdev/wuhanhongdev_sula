package com.sula.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * 车源信息
 */
public class TruckInfo {
    private Integer id;
    private Integer truckId;
    private String loadTime;
    private String startPlace;
    private String endPlace;
    private Date createTime;
    private Double startLon;
    private Double startLat;
    private Double endLon;
    private Double endLat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getStartLon() {
        return startLon;
    }

    public void setStartLon(Double startLon) {
        this.startLon = startLon;
    }

    public Double getStartLat() {
        return startLat;
    }

    public void setStartLat(Double startLat) {
        this.startLat = startLat;
    }

    public Double getEndLon() {
        return endLon;
    }

    public void setEndLon(Double endLon) {
        this.endLon = endLon;
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public static void main(String[] args){
        TruckInfo info = new TruckInfo();
        info.setTruckId(1);
        info.setStartPlace("xx");
        info.setStartLon(123.0);
        info.setStartLat(123.0);
        info.setEndPlace("xxx");
        info.setEndLon(123.0);
        info.setEndLat(123.0);
        info.setLoadTime("2017-10-10");
        System.out.println(JSON.toJSONString(info));
    }
}
