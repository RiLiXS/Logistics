package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :15.3.22
 */
public class RequestWorkCenterDetailsBean implements Serializable {

    private Integer code;
    private String msg;
    private RequestWorkCenterBean data;

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

    public RequestWorkCenterBean getData() {
        return data;
    }

    public void setData(RequestWorkCenterBean data) {
        this.data = data;
    }
}
