package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :2.12.21
 */
public class ReasonReportBean implements Serializable {
    private String unqualifiedNum;//不合格数
    private String ng_reason;//ng原因
    private String identification_card;//标识卡号

    public ReasonReportBean(String unqualifiedNum, String ng_reason, String identification_card) {
        this.unqualifiedNum = unqualifiedNum;
        this.ng_reason = ng_reason;
        this.identification_card = identification_card;
    }

    public String getUnqualifiedNum() {
        return unqualifiedNum;
    }

    public void setUnqualifiedNum(String unqualifiedNum) {
        this.unqualifiedNum = unqualifiedNum;
    }

    public String getNg_reason() {
        return ng_reason;
    }

    public void setNg_reason(String ng_reason) {
        this.ng_reason = ng_reason;
    }

    public String getIdentification_card() {
        return identification_card;
    }

    public void setIdentification_card(String identification_card) {
        this.identification_card = identification_card;
    }
}
