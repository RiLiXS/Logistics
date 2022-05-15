package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :27.12.21
 */
public class RequestMatterInfoBean implements Serializable {

    private Integer code;
    private String msg;
    private MatterInfoBean data;

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

    public MatterInfoBean getData() {
        return data;
    }

    public void setData(MatterInfoBean data) {
        this.data = data;
    }
}
