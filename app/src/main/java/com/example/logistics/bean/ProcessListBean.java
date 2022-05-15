
package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :10.2.22
 */
public class ProcessListBean implements Serializable {
    private String processId;
    private String processCode;
    private String processName;
    private String processTypeId;
    private String processTypeName;
    private String processSegId;
    private String picPath;
    private String orderSeq;
    private String remark;
    private String isQc;
    private String isReport;
    private String processAlias;
    private String busiType;
    private String delFlag;
    private String createTime;
    private String createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String tenantId;
    private String deptId;

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

    public String getProcessTypeId() {
        return processTypeId;
    }

    public void setProcessTypeId(String processTypeId) {
        this.processTypeId = processTypeId;
    }

    public String getProcessTypeName() {
        return processTypeName;
    }

    public void setProcessTypeName(String processTypeName) {
        this.processTypeName = processTypeName;
    }

    public String getProcessSegId() {
        return processSegId;
    }

    public void setProcessSegId(String processSegId) {
        this.processSegId = processSegId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsQc() {
        return isQc;
    }

    public void setIsQc(String isQc) {
        this.isQc = isQc;
    }

    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    public String getProcessAlias() {
        return processAlias;
    }

    public void setProcessAlias(String processAlias) {
        this.processAlias = processAlias;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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
}
