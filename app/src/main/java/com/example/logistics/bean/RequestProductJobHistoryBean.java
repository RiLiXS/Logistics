package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :15.3.22
 */
public class RequestProductJobHistoryBean implements Serializable {

    private Integer code;
    private String msg;
    private List<ProductJobHistoryBean> data;

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

    public List<ProductJobHistoryBean> getData() {
        return data;
    }

    public void setData(List<ProductJobHistoryBean> data) {
        this.data = data;
    }
}
