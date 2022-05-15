package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :21.12.21
 */
public class RequestBoxTransMergeBean implements Serializable {
    private String boxStatus;
    private String packagingBoxEncode;
    private String parentPackagingBoxEncode;
    private List<sonPackagingBoxEncodeList> sonPackagingBoxEncodeLists;

    public String getPackagingBoxEncode() {
        return packagingBoxEncode;
    }

    public void setPackagingBoxEncode(String packagingBoxEncode) {
        this.packagingBoxEncode = packagingBoxEncode;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }

    public String getParentPackagingBoxEncode() {
        return parentPackagingBoxEncode;
    }

    public void setParentPackagingBoxEncode(String parentPackagingBoxEncode) {
        this.parentPackagingBoxEncode = parentPackagingBoxEncode;
    }

    public List<sonPackagingBoxEncodeList> getSonPackagingBoxEncodeLists() {
        return sonPackagingBoxEncodeLists;
    }

    public void setSonPackagingBoxEncodeLists(List<sonPackagingBoxEncodeList> sonPackagingBoxEncodeLists) {
        this.sonPackagingBoxEncodeLists = sonPackagingBoxEncodeLists;
    }
}
