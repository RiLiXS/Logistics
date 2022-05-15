package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :10.1.22
 */
public class DefectBean implements Serializable {
    private String name;
    private String num;

    public DefectBean(String name, String num) {
        this.name = name;
        this.num = num;
    }
    public DefectBean(String num) {
        this.num = num;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
