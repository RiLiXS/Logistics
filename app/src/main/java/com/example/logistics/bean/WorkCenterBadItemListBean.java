package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :15.3.22
 */
public class WorkCenterBadItemListBean implements Serializable {
    private String workCenterBadItemId;
    private String workCenterId;
    private String processCardId;
    private String processName;
    private String processId;
    private String processSortNo;
    private String badItemId;
    private String itemName;
    private String num;
    private String processState;

    public String getWorkCenterBadItemId() {
        return workCenterBadItemId;
    }

    public void setWorkCenterBadItemId(String workCenterBadItemId) {
        this.workCenterBadItemId = workCenterBadItemId;
    }

    public String getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(String workCenterId) {
        this.workCenterId = workCenterId;
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

    public String getBadItemId() {
        return badItemId;
    }

    public void setBadItemId(String badItemId) {
        this.badItemId = badItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }
}
