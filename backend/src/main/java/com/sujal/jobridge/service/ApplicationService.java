package com.sujal.jobridge.service;

import com.sujal.jobridge.entity.Application;
import java.util.List;

public interface ApplicationService {

    Application applyForJob(Long jobId);
    List<Application> getMyApplications();
}