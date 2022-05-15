package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :27.4.22
 */
public class TransCodeMergeBean implements Serializable {
    private String packagingBoxId;
    private String packagingBoxEncode;
    private String createTime;
    private String createBy;
    private String createByName;
    private String updateTime;
    private String updateBy;
    private String updateByName;
    private String delFlag;
    private String tenantId;
    private String deptId;
    private String state;
    private String uuid;
    private String path;
    private String parentPackagingBoxId;
    private String parentPackagingBoxEncode;
    private String packagingId;
    private String packagingName;
    private String packagingHierarchy;
    private String materialId;
    private String materialName;
    private String capacity;
    private String packagingRequirement;
    private String packageQuantity;
    private String packagingBoxProcessCardList;
    private Boolean hasChildren;
    private List<?> children;
    private String id;
    private String parentId;
    private String num;
    private String boxStatus;
    private String currentNum;

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }

    public String getPackagingBoxId() {
        return packagingBoxId;
    }

    public void setPackagingBoxId(String packagingBoxId) {
        this.packagingBoxId = packagingBoxId;
    }

    public String getPackagingBoxEncode() {
        return packagingBoxEncode;
    }

    public void setPackagingBoxEncode(String packagingBoxEncode) {
        this.packagingBoxEncode = packagingBoxEncode;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentPackagingBoxId() {
        return parentPackagingBoxId;
    }

    public void setParentPackagingBoxId(String parentPackagingBoxId) {
        this.parentPackagingBoxId = parentPackagingBoxId;
    }

    public String getParentPackagingBoxEncode() {
        return parentPackagingBoxEncode;
    }

    public void setParentPackagingBoxEncode(String parentPackagingBoxEncode) {
        this.parentPackagingBoxEncode = parentPackagingBoxEncode;
    }

    public String getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(String packagingId) {
        this.packagingId = packagingId;
    }

    public String getPackagingName() {
        return packagingName;
    }

    public void setPackagingName(String packagingName) {
        this.packagingName = packagingName;
    }

    public String getPackagingHierarchy() {
        return packagingHierarchy;
    }

    public void setPackagingHierarchy(String packagingHierarchy) {
        this.packagingHierarchy = packagingHierarchy;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPackagingRequirement() {
        return packagingRequirement;
    }

    public void setPackagingRequirement(String packagingRequirement) {
        this.packagingRequirement = packagingRequirement;
    }

    public String getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(String packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getPackagingBoxProcessCardList() {
        return packagingBoxProcessCardList;
    }

    public void setPackagingBoxProcessCardList(String packagingBoxProcessCardList) {
        this.packagingBoxProcessCardList = packagingBoxProcessCardList;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
