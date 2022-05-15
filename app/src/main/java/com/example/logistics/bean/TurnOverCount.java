package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class TurnOverCount implements Serializable {
    private String turnoverState;
    private String count;

    public String getTurnoverState() {
        return turnoverState;
    }

    public void setTurnoverState(String turnoverState) {
        this.turnoverState = turnoverState;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
