package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :19.10.21
 */
public class TaskSourceBean implements Serializable {

    private Integer code;
    private Object msg;
    private List<RequestTaskSourceBean> data;

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

    public List<RequestTaskSourceBean> getData() {
        return data;
    }

    public void setData(List<RequestTaskSourceBean> data) {
        this.data = data;
    }
}
