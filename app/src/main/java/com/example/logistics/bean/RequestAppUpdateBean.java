package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :9.11.21
 */
public class RequestAppUpdateBean implements Serializable {
    private String appPackageId;
    private String appId;
    private String fileName;
    private String original;
    private String forced;
    private String type;
    private String fileUrl;
    private String issue;
    private String fileSize;
    private String createTime;
    private String createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String delFlag;
    private String tenantId;
    private String deptId;
    private String bucketName;
    private String appVersion;
    private String remark;
    private String updateType;

    public String getAppPackageId() {
        return appPackageId;
    }

    public void setAppPackageId(String appPackageId) {
        this.appPackageId = appPackageId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getForced() {
        return forced;
    }

    public void setForced(String forced) {
        this.forced = forced;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
}
