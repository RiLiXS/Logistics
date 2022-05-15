package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :19.10.21
 */
public class UserRoleBean implements Serializable {

    private Integer code;
    private String msg;
    private List<RequestUserRoleBean> data;

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

    public List<RequestUserRoleBean> getData() {
        return data;
    }

    public void setData(List<RequestUserRoleBean> data) {
        this.data = data;
    }
}
