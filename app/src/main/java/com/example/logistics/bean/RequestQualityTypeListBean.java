package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :21.1.22
 */
public class RequestQualityTypeListBean implements Serializable {

    private Integer code;
    private String msg;
    private List<QualityTypeListBean> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<QualityTypeListBean> getData() {
        return data;
    }

    public void setData(List<QualityTypeListBean> data) {
        this.data = data;
    }
}
