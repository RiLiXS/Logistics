package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :20.12.21
 */
public class WorkReportBean implements Serializable {
    private String processCardId;
    private String processName;
    private String processId;
    private String processSortNo;
    private String reportQty;
    private String rejectQty;
    private String minimumPieces;
    private String workTime;
    private String meansCost;
    private String worker;
    private String workerName;
    private String eqptId;
    private String eqptName;
    private String remark;
    private String locationId;
    private String workCenterId;
    private List<WorkCenterBadItem> workCenterBadItemList;

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
    }

    public List<WorkCenterBadItem> getWorkCenterBadItemList() {
        return workCenterBadItemList;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setWorkCenterBadItemList(List<WorkCenterBadItem> workCenterBadItemList) {
        this.workCenterBadItemList = workCenterBadItemList;
    }

    public String getProcessCardId() {
        return processCardId;
    }

    public void setProcessCardId(String processCardId) {
        this.processCardId = processCardId;
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

    public String getReportQty() {
        return reportQty;
    }

    public void setReportQty(String reportQty) {
        this.reportQty = reportQty;
    }

    public String getRejectQty() {
        return rejectQty;
    }

    public void setRejectQty(String rejectQty) {
        this.rejectQty = rejectQty;
    }

    public String getMinimumPieces() {
        return minimumPieces;
    }

    public void setMinimumPieces(String minimumPieces) {
        this.minimumPieces = minimumPieces;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getMeansCost() {
        return meansCost;
    }

    public void setMeansCost(String meansCost) {
        this.meansCost = meansCost;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
