package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :21.1.22
 */
public class ControlListBean implements Serializable {
    private String qualityControlTaskId;
    private String createTime;
    private String delFlag;
    private String tenantId;
    private String deptId;
    private String checkoutType;
    private String workCenterId;
    private String processId;
    private String processCode;
    private String processName;
    private String processSeq;
    private String processCardId;
    private String barcode;
    private String processRouteTempDtlId;
    private String materialId;
    private String materialName;
    private String reportQty;
    private String checkoutState;
    private String result;
    private String createBy;
    private String createByName;
    private String processRouteTempId;

    public String getProcessRouteTempId() {
        return processRouteTempId;
    }

    public void setProcessRouteTempId(String processRouteTempId) {
        this.processRouteTempId = processRouteTempId;
    }

    public String getQualityControlTaskId() {
        return qualityControlTaskId;
    }

    public void setQualityControlTaskId(String qualityControlTaskId) {
        this.qualityControlTaskId = qualityControlTaskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(String checkoutType) {
        this.checkoutType = checkoutType;
    }

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessSeq() {
        return processSeq;
    }

    public void setProcessSeq(String processSeq) {
        this.processSeq = processSeq;
    }

    public String getProcessCardId() {
        return processCardId;
    }

    public void setProcessCardId(String processCardId) {
        this.processCardId = processCardId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProcessRouteTempDtlId() {
        return processRouteTempDtlId;
    }

    public void setProcessRouteTempDtlId(String processRouteTempDtlId) {
        this.processRouteTempDtlId = processRouteTempDtlId;
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

    public String getReportQty() {
        return reportQty;
    }

    public void setReportQty(String reportQty) {
        this.reportQty = reportQty;
    }

    public String getCheckoutState() {
        return checkoutState;
    }

    public void setCheckoutState(String checkoutState) {
        this.checkoutState = checkoutState;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
