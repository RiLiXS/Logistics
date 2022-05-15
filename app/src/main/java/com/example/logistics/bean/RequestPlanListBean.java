package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :12.2.22
 */
public class RequestPlanListBean implements Serializable {

    private Integer code;
    private String msg;
    private PlanListBean data;

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

    public PlanListBean getData() {
        return data;
    }

    public void setData(PlanListBean data) {
        this.data = data;
    }
}
