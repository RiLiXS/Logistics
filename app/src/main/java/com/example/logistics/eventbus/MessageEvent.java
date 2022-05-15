package com.example.logistics.eventbus;

/**
 *
 * 发送事件
 */

public class MessageEvent {

    private String action="";
    private String msg="";
    private int id = 0;

    public MessageEvent(String action) {
        this.action = action;
    }
    public MessageEvent(String action,String msg) {
        this.action = action;
        this.msg = msg;
    }

    public MessageEvent(int id) {
        this.id = id;
    }

    public MessageEvent() {
    }

    public int getId() {
        return id;
    }

    public MessageEvent setId(int id) {
        this.id = id;
        return this;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
