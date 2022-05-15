package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :2.3.22
 */
public class ArrivalDetailBean implements Serializable {
    private String outsourceArrivalId;
    private String outsourceWorkOrderdId;
    private String serialNumber;
    private String arrivalNum;
    private String arrivalTime;
    private String createTime;
    private String createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String delFlag;
    private String tenantId;
    private String deptId;
    private String workCenterId;
    private String workCenterCode;
    private String workCenterBadItemList;
    private String bigArrivalNum;
    private String badNum;
    private String bigBadNum;

    public String getOutsourceArrivalId() {
        return outsourceArrivalId;
    }

    public void setOutsourceArrivalId(String outsourceArrivalId) {
        this.outsourceArrivalId = outsourceArrivalId;
    }

    public String getOutsourceWorkOrderdId() {
        return outsourceWorkOrderdId;
    }

    public void setOutsourceWorkOrderdId(String outsourceWorkOrderdId) {
        this.outsourceWorkOrderdId = outsourceWorkOrderdId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getArrivalNum() {
        return arrivalNum;
    }

    public void setArrivalNum(String arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getWorkCenterCode() {
        return workCenterCode;
    }

    public void setWorkCenterCode(String workCenterCode) {
        this.workCenterCode = workCenterCode;
    }

    public String getWorkCenterBadItemList() {
        return workCenterBadItemList;
    }

    public void setWorkCenterBadItemList(String workCenterBadItemList) {
        this.workCenterBadItemList = workCenterBadItemList;
    }

    public String getBigArrivalNum() {
        return bigArrivalNum;
    }

    public void setBigArrivalNum(String bigArrivalNum) {
        this.bigArrivalNum = bigArrivalNum;
    }

    public String getBadNum() {
        return badNum;
    }

    public void setBadNum(String badNum) {
        this.badNum = badNum;
    }

    public String getBigBadNum() {
        return bigBadNum;
    }

    public void setBigBadNum(String bigBadNum) {
        this.bigBadNum = bigBadNum;
    }
}
