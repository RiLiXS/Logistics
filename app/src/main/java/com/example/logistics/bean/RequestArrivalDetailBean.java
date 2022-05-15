package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :2.3.22
 */
public class RequestArrivalDetailBean implements Serializable {

    private Integer code;
    private String msg;
    private RequestArrivalBean data;

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

    public RequestArrivalBean getData() {
        return data;
    }

    public void setData(RequestArrivalBean data) {
        this.data = data;
    }
}
