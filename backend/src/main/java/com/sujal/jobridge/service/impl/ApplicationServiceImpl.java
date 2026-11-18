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
import java.util.List;
import com.sujal.jobridge.entity.ApplicationStatus;

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
    @Override
    @Transactional(readOnly = true)
    public List<Application> getMyApplications() {

        // Get authenticated user's email
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // Find logged-in candidate
        User candidate = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email));

        // Verify candidate role
        if (candidate.getRole() != Role.CANDIDATE) {
            throw new BusinessRuleException(
                    "Only candidates can view their applications");
        }

        // Fetch applications belonging to this candidate
        return applicationRepository.findByCandidateId(candidate.getId());
    }
    @Override
    @Transactional(readOnly = true)
    public List<Application> getApplicantsForJob(Long jobId) {

        // Get authenticated user's email
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // Find recruiter
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email));

        // Verify recruiter role
        if (recruiter.getRole() != Role.RECRUITER) {
            throw new BusinessRuleException(
                    "Only recruiters can view applicants");
        }

        // Find job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + jobId));

        // Verify job ownership
        if (!job.getRecruiter().getId().equals(recruiter.getId())) {
            throw new BusinessRuleException(
                    "You are not authorized to view applicants for this job");
        }

        // Fetch applicants
        return applicationRepository.findByJobId(jobId);
    }
    @Override
    @Transactional
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus status) {

        // 1. Get logged-in recruiter's email
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Find recruiter
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        // 3. Verify recruiter role
        if (recruiter.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Only recruiters can update application status");
        }

        // 4. Find application
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        // 5. Verify job ownership
        if (!application.getJob().getRecruiter().getId().equals(recruiter.getId())) {
            throw new RuntimeException("You are not authorized to update this application");
        }

        // 6. Validate status
        if (status == null) {
            throw new IllegalArgumentException("Application status cannot be null");
        }

        // 7. Update and save
        application.setStatus(status);

        return applicationRepository.save(application);
    }
}