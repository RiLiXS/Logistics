package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :12.2.22
 */
public class RequestWorkShopListBean implements Serializable {

    private Integer code;
    private String msg;
    private WorkShopListBean data;

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

    public WorkShopListBean getData() {
        return data;
    }

    public void setData(WorkShopListBean data) {
        this.data = data;
    }
}
