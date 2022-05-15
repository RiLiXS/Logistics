package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :27.4.22
 */
public class processCardList implements Serializable {
    private String barcode;
    private String materialName;

    public processCardList(String barcode, String materialName) {
        this.barcode = barcode;
        this.materialName = materialName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
