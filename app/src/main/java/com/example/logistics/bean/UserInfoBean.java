package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :12.10.21
 */
public class UserInfoBean implements Serializable {

    private Integer code;
    private String msg;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }
}
