package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :30.12.21
 */
public class ProcessDetailBean implements Serializable {
    private String processCardId;
    private String planScheduleId;
    private String barcode;
    private String rootProcessCardId;
    private String parentProcessCardId;
    private String planSpId;
    private String processRouteTempId;
    private String materialId;
    private String materialName;
    private String planNum;
    private String num;
    private String reportNum;
    private String rejectNum;
    private String workTime;
    private String costLabor;
    private String batchSn;
    private String creator;
    private String processId;
    private String processSortNo;
    private String processName;
    private String currentReportNum;
    private String splitProcessId;
    private String splitProcessSortNo;
    private String remark;
    private String wholeProcessId;
    private String isFinish;
    private String isProduced;
    private String productionState;
    private String bomId;
    private String locationId;
    private String isPrint;
    private String lastNum;
    private String qrCode;
    private String publicNum;
    private String materialNum;
    private String cardLevel;
    private String createTime;
    private String createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String delFlag;
    private String tenantId;
    private String deptId;
    private String splitNum;
    private String mergeNum;
    private String processPriceVos;
    private List<WorkCenterListBean> workCenterList;

    public String getProcessCardId() {
        return processCardId;
    }

    public void setProcessCardId(String processCardId) {
        this.processCardId = processCardId;
    }

    public String getPlanScheduleId() {
        return planScheduleId;
    }

    public void setPlanScheduleId(String planScheduleId) {
        this.planScheduleId = planScheduleId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRootProcessCardId() {
        return rootProcessCardId;
    }

    public void setRootProcessCardId(String rootProcessCardId) {
        this.rootProcessCardId = rootProcessCardId;
    }

    public String getParentProcessCardId() {
        return parentProcessCardId;
    }

    public void setParentProcessCardId(String parentProcessCardId) {
        this.parentProcessCardId = parentProcessCardId;
    }

    public String getPlanSpId() {
        return planSpId;
    }

    public void setPlanSpId(String planSpId) {
        this.planSpId = planSpId;
    }

    public String getProcessRouteTempId() {
        return processRouteTempId;
    }

    public void setProcessRouteTempId(String processRouteTempId) {
        this.processRouteTempId = processRouteTempId;
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

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    public String getRejectNum() {
        return rejectNum;
    }

    public void setRejectNum(String rejectNum) {
        this.rejectNum = rejectNum;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getCostLabor() {
        return costLabor;
    }

    public void setCostLabor(String costLabor) {
        this.costLabor = costLabor;
    }

    public String getBatchSn() {
        return batchSn;
    }

    public void setBatchSn(String batchSn) {
        this.batchSn = batchSn;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getCurrentReportNum() {
        return currentReportNum;
    }

    public void setCurrentReportNum(String currentReportNum) {
        this.currentReportNum = currentReportNum;
    }

    public String getSplitProcessId() {
        return splitProcessId;
    }

    public void setSplitProcessId(String splitProcessId) {
        this.splitProcessId = splitProcessId;
    }

    public String getSplitProcessSortNo() {
        return splitProcessSortNo;
    }

    public void setSplitProcessSortNo(String splitProcessSortNo) {
        this.splitProcessSortNo = splitProcessSortNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWholeProcessId() {
        return wholeProcessId;
    }

    public void setWholeProcessId(String wholeProcessId) {
        this.wholeProcessId = wholeProcessId;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getIsProduced() {
        return isProduced;
    }

    public void setIsProduced(String isProduced) {
        this.isProduced = isProduced;
    }

    public String getProductionState() {
        return productionState;
    }

    public void setProductionState(String productionState) {
        this.productionState = productionState;
    }

    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }

    public String getLastNum() {
        return lastNum;
    }

    public void setLastNum(String lastNum) {
        this.lastNum = lastNum;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getPublicNum() {
        return publicNum;
    }

    public void setPublicNum(String publicNum) {
        this.publicNum = publicNum;
    }

    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }

    public String getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(String cardLevel) {
        this.cardLevel = cardLevel;
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

    public String getSplitNum() {
        return splitNum;
    }

    public void setSplitNum(String splitNum) {
        this.splitNum = splitNum;
    }

    public String getMergeNum() {
        return mergeNum;
    }

    public void setMergeNum(String mergeNum) {
        this.mergeNum = mergeNum;
    }

    public String getProcessPriceVos() {
        return processPriceVos;
    }

    public void setProcessPriceVos(String processPriceVos) {
        this.processPriceVos = processPriceVos;
    }

    public List<WorkCenterListBean> getWorkCenterList() {
        return workCenterList;
    }

    public void setWorkCenterList(List<WorkCenterListBean> workCenterList) {
        this.workCenterList = workCenterList;
    }
}
