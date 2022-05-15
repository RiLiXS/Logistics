package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :24.12.21
 */
public class WorkCenterBadItem implements Serializable {
    private String badItemId;
    private String itemName;
    private String num;

    public WorkCenterBadItem(String badItemId, String itemName, String num) {
        this.badItemId = badItemId;
        this.itemName = itemName;
        this.num = num;
    }

    public String getBadItemId() {
        return badItemId;
    }

    public void setBadItemId(String badItemId) {
        this.badItemId = badItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
