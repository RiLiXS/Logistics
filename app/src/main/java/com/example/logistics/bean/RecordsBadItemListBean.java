package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :10.2.22
 */
public class RecordsBadItemListBean implements Serializable {
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
    private String materialName;
    private String locationId;
    private String barcode;
    private String planNo;
    private String createTime;
    private String artificial;
    private boolean checked = false;//是否选中
    private boolean isPeople =false;//是否人为

    public boolean isPeople() {
        return isPeople;
    }

    public void setPeople(boolean people) {
        isPeople = people;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getArtificial() {
        return artificial;
    }

    public void setArtificial(String artificial) {
        this.artificial = artificial;
    }
}
