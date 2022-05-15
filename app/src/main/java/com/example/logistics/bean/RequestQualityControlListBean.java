package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :21.1.22
 */
public class RequestQualityControlListBean implements Serializable {

    private Integer code;
    private String msg;
    private QualityControlListBean data;

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

    public QualityControlListBean getData() {
        return data;
    }

    public void setData(QualityControlListBean data) {
        this.data = data;
    }
}
