package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :16.12.21
 */
public class RequestDeviceListBean implements Serializable {

    private Integer code;
    private String msg;
    private List<DeviceListBean> data;

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

    public List<DeviceListBean> getData() {
        return data;
    }

    public void setData(List<DeviceListBean> data) {
        this.data = data;
    }
}
