package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :15.3.22
 */
public class ProductJobHistoryBean implements Serializable {
    private String workCenterId;
    private String workCenterCode;
    private String processCardId;
    private String barcode;
    private String bomId;
    private String materialId;
    private String materialName;
    private String processName;
    private String processId;
    private String processSortNo;
    private String locationId;
    private String planQty;
    private Double reportQty;
    private Double rejectQty;
    private Double publicQty;
    private Double materialQty;
    private Double minimumPieces;
    private Double minimumPiecesBad;
    private Double workTime;
    private String meansCost;
    private String costPrice;
    private String costLabor;
    private String remark;
    private String planSpId;
    private String firstInspecto;
    private String lastInspector;
    private String routingInspector;
    private String createTime;
    private String createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String delFlag;
    private String tenantId;
    private String deptId;
    private String processRouteTempId;
    private String priceBatchTempId;
    private String priceBatchTempDtlId;
    private String worker;
    private String workerName;
    private String eqptId;
    private String eqptName;
    private String multiple;
    private String planNo;
    private String workerType;
    private String workCenterBadItemList;
    private String workSource;

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

    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
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

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessSortNo() {
        return processSortNo;
    }

    public void setProcessSortNo(String processSortNo) {
        this.processSortNo = processSortNo;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPlanQty() {
        return planQty;
    }

    public void setPlanQty(String planQty) {
        this.planQty = planQty;
    }

    public Double getReportQty() {
        return reportQty;
    }

    public void setReportQty(Double reportQty) {
        this.reportQty = reportQty;
    }

    public Double getRejectQty() {
        return rejectQty;
    }

    public void setRejectQty(Double rejectQty) {
        this.rejectQty = rejectQty;
    }

    public Double getPublicQty() {
        return publicQty;
    }

    public void setPublicQty(Double publicQty) {
        this.publicQty = publicQty;
    }

    public Double getMaterialQty() {
        return materialQty;
    }

    public void setMaterialQty(Double materialQty) {
        this.materialQty = materialQty;
    }

    public Double getMinimumPieces() {
        return minimumPieces;
    }

    public void setMinimumPieces(Double minimumPieces) {
        this.minimumPieces = minimumPieces;
    }

    public Double getMinimumPiecesBad() {
        return minimumPiecesBad;
    }

    public void setMinimumPiecesBad(Double minimumPiecesBad) {
        this.minimumPiecesBad = minimumPiecesBad;
    }

    public Double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = workTime;
    }

    public String getMeansCost() {
        return meansCost;
    }

    public void setMeansCost(String meansCost) {
        this.meansCost = meansCost;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getCostLabor() {
        return costLabor;
    }

    public void setCostLabor(String costLabor) {
        this.costLabor = costLabor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanSpId() {
        return planSpId;
    }

    public void setPlanSpId(String planSpId) {
        this.planSpId = planSpId;
    }

    public String getFirstInspecto() {
        return firstInspecto;
    }

    public void setFirstInspecto(String firstInspecto) {
        this.firstInspecto = firstInspecto;
    }

    public String getLastInspector() {
        return lastInspector;
    }

    public void setLastInspector(String lastInspector) {
        this.lastInspector = lastInspector;
    }

    public String getRoutingInspector() {
        return routingInspector;
    }

    public void setRoutingInspector(String routingInspector) {
        this.routingInspector = routingInspector;
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

    public String getProcessRouteTempId() {
        return processRouteTempId;
    }

    public void setProcessRouteTempId(String processRouteTempId) {
        this.processRouteTempId = processRouteTempId;
    }

    public String getPriceBatchTempId() {
        return priceBatchTempId;
    }

    public void setPriceBatchTempId(String priceBatchTempId) {
        this.priceBatchTempId = priceBatchTempId;
    }

    public String getPriceBatchTempDtlId() {
        return priceBatchTempDtlId;
    }

    public void setPriceBatchTempDtlId(String priceBatchTempDtlId) {
        this.priceBatchTempDtlId = priceBatchTempDtlId;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getEqptId() {
        return eqptId;
    }

    public void setEqptId(String eqptId) {
        this.eqptId = eqptId;
    }

    public String getEqptName() {
        return eqptName;
    }

    public void setEqptName(String eqptName) {
        this.eqptName = eqptName;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getWorkCenterBadItemList() {
        return workCenterBadItemList;
    }

    public void setWorkCenterBadItemList(String workCenterBadItemList) {
        this.workCenterBadItemList = workCenterBadItemList;
    }

    public String getWorkSource() {
        return workSource;
    }

    public void setWorkSource(String workSource) {
        this.workSource = workSource;
    }
}
