package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :23.1.22
 */
public class TotalCountBean implements Serializable {
    private String checkoutState;
    private String count;

    public String getCheckoutState() {
        return checkoutState;
    }

    public void setCheckoutState(String checkoutState) {
        this.checkoutState = checkoutState;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
