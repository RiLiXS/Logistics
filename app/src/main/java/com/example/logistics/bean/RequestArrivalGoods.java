package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :2.3.22
 */
public class RequestArrivalGoods implements Serializable {
    private Integer outsourceWorkOrderdId;
    private Integer bigArrivalNum;
    private Integer arrivalNum;
    private Integer badNum;
    private Integer bigBadNum;
    private String arrivalTime;
    private List<WorkCenterBadItem> workCenterBadItemList;

    public Integer getOutsourceWorkOrderdId() {
        return outsourceWorkOrderdId;
    }

    public void setOutsourceWorkOrderdId(Integer outsourceWorkOrderdId) {
        this.outsourceWorkOrderdId = outsourceWorkOrderdId;
    }

    public Integer getBigArrivalNum() {
        return bigArrivalNum;
    }

    public void setBigArrivalNum(Integer bigArrivalNum) {
        this.bigArrivalNum = bigArrivalNum;
    }

    public Integer getArrivalNum() {
        return arrivalNum;
    }

    public void setArrivalNum(Integer arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    public Integer getBadNum() {
        return badNum;
    }

    public void setBadNum(Integer badNum) {
        this.badNum = badNum;
    }

    public Integer getBigBadNum() {
        return bigBadNum;
    }

    public void setBigBadNum(Integer bigBadNum) {
        this.bigBadNum = bigBadNum;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<WorkCenterBadItem> getWorkCenterBadItemList() {
        return workCenterBadItemList;
    }

    public void setWorkCenterBadItemList(List<WorkCenterBadItem> workCenterBadItemList) {
        this.workCenterBadItemList = workCenterBadItemList;
    }
}
