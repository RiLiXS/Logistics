package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class RecordsTurnOverListBean implements Serializable {
    private Integer turnoverInvoicesId;
    private String turnoverInvoicesNo;
    private String workCenterCode;
    private String barcode;
    private String rollOutLocationId;
    private String rollOutLocationName;
    private String shiftToLocationId;
    private String shiftToLocationName;
    private String turnoverNum;
    private String delFlag;
    private String createTime;
    private String createBy;
    private String updateTime;
    private String updateBy;
    private String tenantId;
    private String createByName;
    private String updateByName;
    private String deptId;
    private String turnoverBy;
    private String turnoverByName;
    private String turnoverState;
    private String materialName;
    private String materialId;
    private String planNo;
    private boolean checked = false;//是否选中

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getTurnoverInvoicesId() {
        return turnoverInvoicesId;
    }

    public void setTurnoverInvoicesId(Integer turnoverInvoicesId) {
        this.turnoverInvoicesId = turnoverInvoicesId;
    }

    public String getTurnoverInvoicesNo() {
        return turnoverInvoicesNo;
    }

    public void setTurnoverInvoicesNo(String turnoverInvoicesNo) {
        this.turnoverInvoicesNo = turnoverInvoicesNo;
    }

    public String getWorkCenterCode() {
        return workCenterCode;
    }

    public void setWorkCenterCode(String workCenterCode) {
        this.workCenterCode = workCenterCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRollOutLocationId() {
        return rollOutLocationId;
    }

    public void setRollOutLocationId(String rollOutLocationId) {
        this.rollOutLocationId = rollOutLocationId;
    }

    public String getRollOutLocationName() {
        return rollOutLocationName;
    }

    public void setRollOutLocationName(String rollOutLocationName) {
        this.rollOutLocationName = rollOutLocationName;
    }

    public String getShiftToLocationId() {
        return shiftToLocationId;
    }

    public void setShiftToLocationId(String shiftToLocationId) {
        this.shiftToLocationId = shiftToLocationId;
    }

    public String getShiftToLocationName() {
        return shiftToLocationName;
    }

    public void setShiftToLocationName(String shiftToLocationName) {
        this.shiftToLocationName = shiftToLocationName;
    }

    public String getTurnoverNum() {
        return turnoverNum;
    }

    public void setTurnoverNum(String turnoverNum) {
        this.turnoverNum = turnoverNum;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTurnoverBy() {
        return turnoverBy;
    }

    public void setTurnoverBy(String turnoverBy) {
        this.turnoverBy = turnoverBy;
    }

    public String getTurnoverByName() {
        return turnoverByName;
    }

    public void setTurnoverByName(String turnoverByName) {
        this.turnoverByName = turnoverByName;
    }

    public String getTurnoverState() {
        return turnoverState;
    }

    public void setTurnoverState(String turnoverState) {
        this.turnoverState = turnoverState;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }
}
