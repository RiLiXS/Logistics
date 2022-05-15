package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class RequestDefectStateCount implements Serializable {

    private Integer code;
    private String msg;
    private List<DefectStateCount> data;

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

    public List<DefectStateCount> getData() {
        return data;
    }

    public void setData(List<DefectStateCount> data) {
        this.data = data;
    }
}
