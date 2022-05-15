package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :27.12.21
 */
public class RequestProcessCardBean implements Serializable {

    private Integer code;
    private String msg;
    private ProcessCardBean data;

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

    public ProcessCardBean getData() {
        return data;
    }

    public void setData(ProcessCardBean data) {
        this.data = data;
    }
}
