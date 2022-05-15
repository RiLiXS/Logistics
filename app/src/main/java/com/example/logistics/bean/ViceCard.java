package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :21.12.21
 */
public class ViceCard implements Serializable {
    private String barcode;
    private String num;

    public ViceCard(String barcode, String num) {
        this.barcode = barcode;
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
