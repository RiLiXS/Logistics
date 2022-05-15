package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :19.10.21
 */
public class TaskReasonBean implements Serializable {

    private Integer code;
    private String msg;
    private List<RequestTaskReasonBean> data;

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

    public List<RequestTaskReasonBean> getData() {
        return data;
    }

    public void setData(List<RequestTaskReasonBean> data) {
        this.data = data;
    }
}
