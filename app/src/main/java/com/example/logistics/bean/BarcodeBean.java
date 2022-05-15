package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :14.12.21
 */
public class BarcodeBean implements Serializable {
    private String cardProcessRouteId;
    private String processRouteTempId;
    private String processId;
    private String processCode;
    private String processName;
    private String processSortNo;
    private String isReport;
    private String delFlag;
    private String processCardId;
    private String barcode;
    private String processSeq;
    private String multiple;
    private String reportQty;
    private String rejectQty;
    private String checkout;
    private String processRouteTempDtlId;
    private String locationId;

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

    public String getProcessRouteTempDtlId() {
        return processRouteTempDtlId;
    }

    public void setProcessRouteTempDtlId(String processRouteTempDtlId) {
        this.processRouteTempDtlId = processRouteTempDtlId;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getCardProcessRouteId() {
        return cardProcessRouteId;
    }

    public void setCardProcessRouteId(String cardProcessRouteId) {
        this.cardProcessRouteId = cardProcessRouteId;
    }

    public String getProcessRouteTempId() {
        return processRouteTempId;
    }

    public void setProcessRouteTempId(String processRouteTempId) {
        this.processRouteTempId = processRouteTempId;
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

    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public String getProcessSeq() {
        return processSeq;
    }

    public void setProcessSeq(String processSeq) {
        this.processSeq = processSeq;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
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
}
