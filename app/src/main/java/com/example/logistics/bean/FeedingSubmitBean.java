package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :15.12.21
 */
public class FeedingSubmitBean implements Serializable {
    private String batchChargingId;
    private String barcode;
    private String materialId;
    private String materialName;
    private String processId;
    private String processName;
    private String eqptId;
    private String batchChargingUserId;
    private String batchChargingUserName;
    private List<BatchChargingDtl> batchChargingDtlList;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getBatchChargingId() {
        return batchChargingId;
    }

    public void setBatchChargingId(String batchChargingId) {
        this.batchChargingId = batchChargingId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getEqptId() {
        return eqptId;
    }

    public void setEqptId(String eqptId) {
        this.eqptId = eqptId;
    }

    public String getBatchChargingUserId() {
        return batchChargingUserId;
    }

    public void setBatchChargingUserId(String batchChargingUserId) {
        this.batchChargingUserId = batchChargingUserId;
    }

    public String getBatchChargingUserName() {
        return batchChargingUserName;
    }

    public void setBatchChargingUserName(String batchChargingUserName) {
        this.batchChargingUserName = batchChargingUserName;
    }

    public List<BatchChargingDtl> getBatchChargingDtlList() {
        return batchChargingDtlList;
    }

    public void setBatchChargingDtlList(List<BatchChargingDtl> batchChargingDtlList) {
        this.batchChargingDtlList = batchChargingDtlList;
    }
}
