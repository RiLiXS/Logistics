package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :21.1.22
 */
public class RequestProcessTypeListBean implements Serializable {

    private Integer code;
    private Object msg;
    private List<ProcessTypeListBean> data;

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

    public List<ProcessTypeListBean> getData() {
        return data;
    }

    public void setData(List<ProcessTypeListBean> data) {
        this.data = data;
    }
}
