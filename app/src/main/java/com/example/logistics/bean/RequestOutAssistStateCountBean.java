package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :1.3.22
 */
public class RequestOutAssistStateCountBean implements Serializable {

    private Integer code;
    private String msg;
    private List<OutAssistCountBean> data;

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

    public List<OutAssistCountBean> getData() {
        return data;
    }

    public void setData(List<OutAssistCountBean> data) {
        this.data = data;
    }
}
