package com.sujal.jobridge.service;

import com.sujal.jobridge.entity.Application;

public interface ApplicationService {
    Application applyForJob(Long candidateId, Long jobId);
}
