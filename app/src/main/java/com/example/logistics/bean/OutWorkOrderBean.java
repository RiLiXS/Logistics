package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :1.3.22
 */
public class OutWorkOrderBean implements Serializable {
    private Integer outsourceWorkOrderdId;
    private Integer processCardId;
    private String barcode;
    private String serialNumber;
    private Integer customerId;
    private String customerName;
    private String outsourceDeliveryTime;
    private String deliveryTime;
    private String consigner;
    private String consignerName;
    private Integer processRouteTempId;
    private Integer materialId;
    private String materialName;
    private Integer processId;
    private String processName;
    private Integer processSortNo;
    private Integer arrivalNum;
    private String allArrivalTime;
    private Integer consignmentNum;
    private Integer multiple;
    private String createTime;
    private Integer createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String delFlag;
    private String tenantId;
    private Integer deptId;
    private String state;
    private String ids;
    private Boolean isCheck=false;

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public Integer getOutsourceWorkOrderdId() {
        return outsourceWorkOrderdId;
    }

    public void setOutsourceWorkOrderdId(Integer outsourceWorkOrderdId) {
        this.outsourceWorkOrderdId = outsourceWorkOrderdId;
    }

    public Integer getProcessCardId() {
        return processCardId;
    }

    public void setProcessCardId(Integer processCardId) {
        this.processCardId = processCardId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOutsourceDeliveryTime() {
        return outsourceDeliveryTime;
    }

    public void setOutsourceDeliveryTime(String outsourceDeliveryTime) {
        this.outsourceDeliveryTime = outsourceDeliveryTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getConsigner() {
        return consigner;
    }

    public void setConsigner(String consigner) {
        this.consigner = consigner;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    public Integer getProcessRouteTempId() {
        return processRouteTempId;
    }

    public void setProcessRouteTempId(Integer processRouteTempId) {
        this.processRouteTempId = processRouteTempId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getProcessSortNo() {
        return processSortNo;
    }

    public void setProcessSortNo(Integer processSortNo) {
        this.processSortNo = processSortNo;
    }

    public Integer getArrivalNum() {
        return arrivalNum;
    }

    public void setArrivalNum(Integer arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    public String getAllArrivalTime() {
        return allArrivalTime;
    }

    public void setAllArrivalTime(String allArrivalTime) {
        this.allArrivalTime = allArrivalTime;
    }

    public Integer getConsignmentNum() {
        return consignmentNum;
    }

    public void setConsignmentNum(Integer consignmentNum) {
        this.consignmentNum = consignmentNum;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
