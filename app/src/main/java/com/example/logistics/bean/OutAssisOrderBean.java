package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :28.2.22
 */
public class OutAssisOrderBean implements Serializable {
    private String barcode;
    private String customerId;
    private String customerName;
    private String outsourceDeliveryTime;
    private Integer processSortNo;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
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

    public Integer getProcessSortNo() {
        return processSortNo;
    }

    public void setProcessSortNo(Integer processSortNo) {
        this.processSortNo = processSortNo;
    }
}
