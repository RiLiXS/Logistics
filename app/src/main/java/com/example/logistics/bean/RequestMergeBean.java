package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :21.12.21
 */
public class RequestMergeBean implements Serializable {
    private String processCardId;
    private String processSeq;
    private List<ViceCard> viceCardList;

    public String getProcessCardId() {
        return processCardId;
    }

    public void setProcessCardId(String processCardId) {
        this.processCardId = processCardId;
    }

    public String getProcessSeq() {
        return processSeq;
    }

    public void setProcessSeq(String processSeq) {
        this.processSeq = processSeq;
    }

    public List<ViceCard> getViceCardList() {
        return viceCardList;
    }

    public void setViceCardList(List<ViceCard> viceCardList) {
        this.viceCardList = viceCardList;
    }
}
