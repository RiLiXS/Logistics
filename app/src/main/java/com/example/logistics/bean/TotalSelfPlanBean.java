package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :21.2.22
 */
public class TotalSelfPlanBean implements Serializable {
    private String planNumSum;
    private String completionNumSum;
    private String date;
    private String delivery;

    public String getPlanNumSum() {
        return planNumSum;
    }

    public void setPlanNumSum(String planNumSum) {
        this.planNumSum = planNumSum;
    }

    public String getCompletionNumSum() {
        return completionNumSum;
    }

    public void setCompletionNumSum(String completionNumSum) {
        this.completionNumSum = completionNumSum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
