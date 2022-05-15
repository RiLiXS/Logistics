package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :22.2.22
 */
public class RequestSelfPlanTotalListBean implements Serializable {

    private Integer code;
    private Object msg;
    private List<SelfPlanTotalListBean> data;

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

    public List<SelfPlanTotalListBean> getData() {
        return data;
    }

    public void setData(List<SelfPlanTotalListBean> data) {
        this.data = data;
    }
}
