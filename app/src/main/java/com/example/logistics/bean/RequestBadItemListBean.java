package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :24.12.21
 */
public class RequestBadItemListBean  implements Serializable {

    private Integer code;
    private String msg;
    private List<BadItemListBean> data;

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

    public List<BadItemListBean> getData() {
        return data;
    }

    public void setData(List<BadItemListBean> data) {
        this.data = data;
    }
}
