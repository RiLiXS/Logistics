package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :10.2.22
 */
public class RequestWorkBadItemListBean implements Serializable {

    private Integer code;
    private Object msg;
    private WorkBadItemListBean data;

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

    public WorkBadItemListBean getData() {
        return data;
    }

    public void setData(WorkBadItemListBean data) {
        this.data = data;
    }
}
