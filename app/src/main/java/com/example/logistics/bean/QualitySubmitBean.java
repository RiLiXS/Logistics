package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :22.1.22
 */
public class QualitySubmitBean implements Serializable {
    private String processId;
    private String processCode;
    private String processName;
    private String processSeq;
    private String processCardId;
    private String barcode;
    private String checkoutType;
    private String sampleNum;
    private String result;
    private String processRouteTempDtlId;
    private String processRouteTempId;
    private String qualityControlTaskId;
    private List<QualityControlDtl> qualityControlDtlList;

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

    public String getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(String sampleNum) {
        this.sampleNum = sampleNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getProcessRouteTempDtlId() {
        return processRouteTempDtlId;
    }

    public void setProcessRouteTempDtlId(String processRouteTempDtlId) {
        this.processRouteTempDtlId = processRouteTempDtlId;
    }

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

    public List<QualityControlDtl> getQualityControlDtlList() {
        return qualityControlDtlList;
    }

    public void setQualityControlDtlList(List<QualityControlDtl> qualityControlDtlList) {
        this.qualityControlDtlList = qualityControlDtlList;
    }
}
