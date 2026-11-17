package com.sujal.jobridge.service;

import com.sujal.jobridge.entity.Application;
import java.util.List;
import com.sujal.jobridge.entity.ApplicationStatus;

public interface ApplicationService {

    Application applyForJob(Long jobId);
    List<Application> getMyApplications();
    List<Application> getApplicantsForJob(Long jobId);
    Application updateApplicationStatus(Long applicationId, ApplicationStatus status);
}