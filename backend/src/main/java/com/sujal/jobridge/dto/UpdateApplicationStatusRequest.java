package com.sujal.jobridge.dto;

import com.sujal.jobridge.entity.ApplicationStatus;

public class UpdateApplicationStatusRequest {

    private ApplicationStatus status;

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}