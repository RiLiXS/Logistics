package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :24.12.21
 */
public class BadItemListBean implements Serializable {
    private String badItemId;
    private String itemName;
    private String createTime;
    private String createBy;
    private String createByName;
    private Object updateTime;
    private Object updateBy;
    private Object updateByName;
    private String delFlag;
    private String tenantId;
    private Object deptId;
    private Object remark;
    private String num;

    public BadItemListBean(String badItemId, String itemName, String num) {
        this.badItemId = badItemId;
        this.itemName = itemName;
        this.num = num;
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

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(Object updateByName) {
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

    public Object getDeptId() {
        return deptId;
    }

    public void setDeptId(Object deptId) {
        this.deptId = deptId;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
