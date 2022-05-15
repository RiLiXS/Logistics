package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :9.11.21
 */
public class AppUpdateBean implements Serializable {

    private Integer code;
    private String msg;
    private RequestAppUpdateBean data;

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

    public RequestAppUpdateBean getData() {
        return data;
    }

    public void setData(RequestAppUpdateBean data) {
        this.data = data;
    }
}
