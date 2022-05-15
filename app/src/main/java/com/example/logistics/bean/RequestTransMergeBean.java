package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :21.12.21
 */
public class RequestTransMergeBean implements Serializable {
    private String packagingBoxEncode;
    private String materialName;
    private List<processCardList> processCardListList;

    public String getPackagingBoxEncode() {
        return packagingBoxEncode;
    }

    public void setPackagingBoxEncode(String packagingBoxEncode) {
        this.packagingBoxEncode = packagingBoxEncode;
    }

    public List<processCardList> getProcessCardListList() {
        return processCardListList;
    }

    public void setProcessCardListList(List<processCardList> processCardListList) {
        this.processCardListList = processCardListList;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

}
