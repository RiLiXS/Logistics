package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :15.12.21
 */
public class BatchChargingDtl implements Serializable {
    private String barcode;
    private String batchNo;
    private String materialName;
    private String materialCode;
    private String unit;
    private String totalQuantity;
    private String feedingNum;
    private String supplier;
    private String specs;

    public BatchChargingDtl(String barcode, String batchNo, String materialName, String materialCode, String unit, String totalQuantity, String feedingNum,String specs,String supplier) {
        this.barcode = barcode;
        this.batchNo = batchNo;
        this.materialName = materialName;
        this.materialCode = materialCode;
        this.unit = unit;
        this.totalQuantity = totalQuantity;
        this.feedingNum = feedingNum;
        this.supplier = supplier;
        this.specs = specs;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getFeedingNum() {
        return feedingNum;
    }

    public void setFeedingNum(String feedingNum) {
        this.feedingNum = feedingNum;
    }
}
