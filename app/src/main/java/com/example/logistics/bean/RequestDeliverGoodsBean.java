package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :1.3.22
 */
public class RequestDeliverGoodsBean implements Serializable {
    private Integer consigner;
    private String consignerName;
    private String deliveryTime;
    private List<Integer> ids;

    public Integer getConsigner() {
        return consigner;
    }

    public void setConsigner(Integer consigner) {
        this.consigner = consigner;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
