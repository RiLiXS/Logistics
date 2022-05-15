package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :22.1.22
 */
public class QualityControlDtl implements Serializable {
    private String badItemId;
    private String itemName;
    private String num;
    private String averageValue;
    private String type;
    private String upValue;
    private String lowValue;
    private String unit;
    private List<Double> recordList;
    private String judgingResults;

    public String getJudgingResults() {
        return judgingResults;
    }

    public void setJudgingResults(String judgingResults) {
        this.judgingResults = judgingResults;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public QualityControlDtl(String badItemId, String itemName, String type, String judgingResults) {
        this.badItemId = badItemId;
        this.itemName = itemName;
        this.type = type;
        this.judgingResults = judgingResults;
    }

    public QualityControlDtl(String badItemId, String itemName, String num, String averageValue, String type, String upValue, String lowValue, String unit, List<Double> recordList) {
        this.badItemId = badItemId;
        this.itemName = itemName;
        this.num = num;
        this.averageValue = averageValue;
        this.type = type;
        this.upValue = upValue;
        this.lowValue = lowValue;
        this.unit = unit;
        this.recordList = recordList;
    }

    public QualityControlDtl(String badItemId, String itemName, String num, String averageValue, String type, List<Double> recordList) {
        this.badItemId = badItemId;
        this.itemName = itemName;
        this.num = num;
        this.averageValue = averageValue;
        this.type = type;
        this.recordList = recordList;
    }

    public String getUpValue() {
        return upValue;
    }

    public void setUpValue(String upValue) {
        this.upValue = upValue;
    }

    public String getLowValue() {
        return lowValue;
    }

    public void setLowValue(String lowValue) {
        this.lowValue = lowValue;
    }

    public String getBadItemId() {
        return badItemId;
    }

    public void setBadItemId(String badItemId) {
        this.badItemId = badItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(String averageValue) {
        this.averageValue = averageValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Double> recordList) {
        this.recordList = recordList;
    }
}
