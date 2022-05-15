package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :31.3.22
 */
public class RequestMatterDetails implements Serializable {

    private Integer code;
    private String msg;
    private MatterDetails data;

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

    public MatterDetails getData() {
        return data;
    }

    public void setData(MatterDetails data) {
        this.data = data;
    }
}
