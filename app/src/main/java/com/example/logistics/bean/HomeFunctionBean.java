package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :28.9.21
 */
public class HomeFunctionBean implements Serializable {
    private int img;
    private  String name;
    private int id;

    public HomeFunctionBean(int img, String name, int id) {
        this.img = img;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HomeFunctionBean(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
