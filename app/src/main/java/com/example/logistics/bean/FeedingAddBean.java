package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :2.12.21
 */
public class FeedingAddBean implements Serializable {
    private String barCard;//条码
    private String batchNumber;//批号
    private String matterName;//物料名称
    private String matterCode;//物料编码
    private String unit;//单位
    private String totalNum;//总数量
    private String matterNum;//投料数

    public FeedingAddBean(String barCard, String batchNumber, String matterName, String matterCode, String unit, String totalNum, String matterNum) {
        this.barCard = barCard;
        this.batchNumber = batchNumber;
        this.matterName = matterName;
        this.matterCode = matterCode;
        this.unit = unit;
        this.totalNum = totalNum;
        this.matterNum = matterNum;
    }

    public String getBarCard() {
        return barCard;
    }

    public void setBarCard(String barCard) {
        this.barCard = barCard;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

    public String getMatterCode() {
        return matterCode;
    }

    public void setMatterCode(String matterCode) {
        this.matterCode = matterCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getMatterNum() {
        return matterNum;
    }

    public void setMatterNum(String matterNum) {
        this.matterNum = matterNum;
    }
}
