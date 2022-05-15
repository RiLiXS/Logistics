package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :22.12.21
 */
public class SplitBean implements Serializable {
    private String processCardId;
    private String num;
    private String seq;
    private String cardNum;

    public String getProcessCardId() {
        return processCardId;
    }

    public void setProcessCardId(String processCardId) {
        this.processCardId = processCardId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
