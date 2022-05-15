package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :22.1.22
 */
public class QualityTaskAddBean implements Serializable {
    private String processId;
    private String processCode;
    private String processName;
    private String processSeq;
    private String processCardId;
    private String barcode;
    private String checkoutType;
    private String processRouteTempDtlId;
    private String reportQty;
    private String source;
    private String materialId;
    private String materialName;
    private String processRouteTempId;

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

    public String getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(String checkoutType) {
        this.checkoutType = checkoutType;
    }

    public String getProcessRouteTempDtlId() {
        return processRouteTempDtlId;
    }

    public void setProcessRouteTempDtlId(String processRouteTempDtlId) {
        this.processRouteTempDtlId = processRouteTempDtlId;
    }

    public String getReportQty() {
        return reportQty;
    }

    public void setReportQty(String reportQty) {
        this.reportQty = reportQty;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
}
