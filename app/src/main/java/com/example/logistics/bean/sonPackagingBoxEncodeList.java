package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :15.12.21
 */
public class sonPackagingBoxEncodeList implements Serializable {
    private String barcode;//条码
    private String batchNo;//批号
    private String materialName;//物料名称
    private String materialCode;//编码
    private String packagingBoxEncode;//子箱编码
    private String boxStatus;//封箱状态
    private String  matterNum;//投料数

    public sonPackagingBoxEncodeList(String barcode, String batchNo, String materialName, String materialCode, String packagingBoxEncode, String boxStatus, String matterNum) {
        this.barcode = barcode;
        this.batchNo = batchNo;
        this.materialName = materialName;
        this.materialCode = materialCode;
        this.packagingBoxEncode = packagingBoxEncode;
        this.boxStatus = boxStatus;
        this.matterNum = matterNum;
    }

    public String getMatterNum() {
        return matterNum;
    }

    public void setMatterNum(String matterNum) {
        this.matterNum = matterNum;
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

    public String getPackagingBoxEncode() {
        return packagingBoxEncode;
    }

    public void setPackagingBoxEncode(String packagingBoxEncode) {
        this.packagingBoxEncode = packagingBoxEncode;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }
}
