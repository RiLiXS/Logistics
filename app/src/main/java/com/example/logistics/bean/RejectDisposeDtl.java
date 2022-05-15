package com.example.logistics.bean;

import java.io.Serializable;

/**
 * @author : jyx
 * @description:
 * @date :11.2.22
 */
public class RejectDisposeDtl implements Serializable {
    private String workCenterBadItemId;
    private String artificial;

    public RejectDisposeDtl(String workCenterBadItemId, String artificial) {
        this.workCenterBadItemId = workCenterBadItemId;
        this.artificial = artificial;
    }

    public String getWorkCenterBadItemId() {
        return workCenterBadItemId;
    }

    public void setWorkCenterBadItemId(String workCenterBadItemId) {
        this.workCenterBadItemId = workCenterBadItemId;
    }

    public String getArtificial() {
        return artificial;
    }

    public void setArtificial(String artificial) {
        this.artificial = artificial;
    }
}
