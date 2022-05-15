package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class DefectStateCount implements Serializable {
    private String processState;
    private String count;

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
