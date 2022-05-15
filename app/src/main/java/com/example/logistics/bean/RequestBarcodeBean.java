package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :14.12.21
 */
public class RequestBarcodeBean implements Serializable {

    private Integer code;
    private String msg;
    private CodeBean data;

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

    public CodeBean getData() {
        return data;
    }

    public void setData(CodeBean data) {
        this.data = data;
    }
}
