package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :22.2.22
 */
public class RequestSelfPlanListBean implements Serializable {

    private Integer code;
    private String msg;
    private RequestSelfPlanBean data;

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

    public RequestSelfPlanBean getData() {
        return data;
    }

    public void setData(RequestSelfPlanBean data) {
        this.data = data;
    }
}
