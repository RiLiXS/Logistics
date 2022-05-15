package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :27.4.22
 */
public class RequestTransCodeMergeBean implements Serializable {

    private Integer code;
    private String msg;
    private TransCodeMergeBean data;

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

    public TransCodeMergeBean getData() {
        return data;
    }

    public void setData(TransCodeMergeBean data) {
        this.data = data;
    }
}
