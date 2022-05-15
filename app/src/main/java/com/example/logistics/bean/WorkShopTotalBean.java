package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :22.2.22
 */
public class WorkShopTotalBean implements Serializable {
    private Integer count;
    private String isFinish;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }
}
