package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :22.1.22
 */
public class RequestQualitySuccessBean implements Serializable {

    private Integer code;
    private String msg;
    private QualitySuccessBean data;

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

    public QualitySuccessBean getData() {
        return data;
    }

    public void setData(QualitySuccessBean data) {
        this.data = data;
    }
}
