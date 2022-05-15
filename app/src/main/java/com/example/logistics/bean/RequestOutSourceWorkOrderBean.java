package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :1.3.22
 */
public class RequestOutSourceWorkOrderBean implements Serializable {

    private Integer code;
    private String msg;
    private RequestOutWorkOrderBean data;

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

    public RequestOutWorkOrderBean getData() {
        return data;
    }

    public void setData(RequestOutWorkOrderBean data) {
        this.data = data;
    }
}
