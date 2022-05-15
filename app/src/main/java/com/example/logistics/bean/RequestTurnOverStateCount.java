package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class RequestTurnOverStateCount implements Serializable {

    private Integer code;
    private Object msg;
    private List<TurnOverCount> data;

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

    public List<TurnOverCount> getData() {
        return data;
    }

    public void setData(List<TurnOverCount> data) {
        this.data = data;
    }
}
