package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class DefectDealBean implements Serializable {
    private String handlingSuggestion;
    private String actionResults;
    private List<RejectDisposeDtl> rejectDisposeDtlList;

    public String getHandlingSuggestion() {
        return handlingSuggestion;
    }

    public void setHandlingSuggestion(String handlingSuggestion) {
        this.handlingSuggestion = handlingSuggestion;
    }

    public String getActionResults() {
        return actionResults;
    }

    public void setActionResults(String actionResults) {
        this.actionResults = actionResults;
    }

    public List<RejectDisposeDtl> getRejectDisposeDtlList() {
        return rejectDisposeDtlList;
    }

    public void setRejectDisposeDtlList(List<RejectDisposeDtl> rejectDisposeDtlList) {
        this.rejectDisposeDtlList = rejectDisposeDtlList;
    }
}
