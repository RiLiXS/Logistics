package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class RequestTurnOverListBean implements Serializable {

    private Integer code;
    private Object msg;
    private TurnOverListBean data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public TurnOverListBean getData() {
        return data;
    }

    public void setData(TurnOverListBean data) {
        this.data = data;
    }
}
