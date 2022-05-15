package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :30.12.21
 */
public class RequestProcessDetailBean implements Serializable {

    private Integer code;
    private String msg;
    private ProcessDetailBean data;

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

    public ProcessDetailBean getData() {
        return data;
    }

    public void setData(ProcessDetailBean data) {
        this.data = data;
    }
}
