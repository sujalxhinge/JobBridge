package com.sujal.jobridge.service.impl;

import com.sujal.jobridge.entity.*;
import com.sujal.jobridge.exception.BusinessRuleException;
import com.sujal.jobridge.exception.ResourceNotFoundException;
import com.sujal.jobridge.repository.ApplicationRepository;
import com.sujal.jobridge.repository.JobRepository;
import com.sujal.jobridge.repository.UserRepository;
import com.sujal.jobridge.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, JobRepository jobRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public com.sujal.jobridge.entity.Application applyForJob(Long candidateId, Long jobId){
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Candidate not found with id: " + candidateId));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + jobId));

        if(candidate.getRole() != Role.CANDIDATE){
            throw new BusinessRuleException("only candidate can apply for this job");
        }
        if (job.getStatus() != JobStatus.OPEN) {
            throw new BusinessRuleException(
                    "Cannot apply for a closed job"
            );
        }

        boolean alreadyApplied =
                applicationRepository.existsByCandidateIdAndJobId(
                        candidateId,jobId
                );

        if (alreadyApplied){
            throw new BusinessRuleException(
                    "you have already applied for this job"
            );
        }
        Application application = new Application(candidate, job);

        return applicationRepository.save(application);
    }
}

