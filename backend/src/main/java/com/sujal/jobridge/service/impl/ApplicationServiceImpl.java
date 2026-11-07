package com.sujal.jobridge.service.impl;

import com.sujal.jobridge.entity.*;
import com.sujal.jobridge.exception.BusinessRuleException;
import com.sujal.jobridge.exception.ResourceNotFoundException;
import com.sujal.jobridge.repository.ApplicationRepository;
import com.sujal.jobridge.repository.JobRepository;
import com.sujal.jobridge.repository.UserRepository;
import com.sujal.jobridge.service.ApplicationService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Application applyForJob(Long jobId) {

        // Get authenticated user's email
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // Find candidate using email
        User candidate = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email));

        // Check candidate role
        if (candidate.getRole() != Role.CANDIDATE) {
            throw new BusinessRuleException(
                    "Only candidates can apply for jobs");
        }

        // Find job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + jobId));

        // Check job status
        if (job.getStatus() != JobStatus.OPEN) {
            throw new BusinessRuleException(
                    "Cannot apply for a closed job");
        }

        // Check duplicate application
        boolean alreadyApplied =
                applicationRepository.existsByCandidateIdAndJobId(
                        candidate.getId(), jobId);

        if (alreadyApplied) {
            throw new BusinessRuleException(
                    "You have already applied for this job");
        }

        // Create application
        Application application = new Application(candidate, job);

        return applicationRepository.save(application);
    }
}