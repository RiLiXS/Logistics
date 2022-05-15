package com.example.logistics.bean;

public class TimeBean {
    private String time;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TimeBean(String time, int type) {
        this.time = time;
        this.type = type;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
