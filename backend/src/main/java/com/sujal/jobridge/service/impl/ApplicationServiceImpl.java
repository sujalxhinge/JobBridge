package com.sujal.jobridge.service.impl;

import com.sujal.jobridge.entity.*;
import com.sujal.jobridge.repository.ApplicationRepository;
import com.sujal.jobridge.repository.JobRepository;
import com.sujal.jobridge.repository.UserRepository;
import com.sujal.jobridge.service.ApplicationService;
import org.springframework.stereotype.Service;

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
    public com.sujal.jobridge.entity.Application applyForJob(Long candidateId, Long jobId){
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(()->
                        new RuntimeException("candidate not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(()->
                        new RuntimeException("job not found"));

        if(candidate.getRole() != Role.CANDIDATE){
            throw new RuntimeException("only candidate can apply for this job");
        }
        if (job.getStatus() != JobStatus.OPEN) {
            throw new RuntimeException(
                    "Cannot apply for a closed job"
            );
        }

        boolean alreadyApplied =
                applicationRepository.existsByCandidateIdAndJobId(
                        candidateId,jobId
                );

        if (alreadyApplied){
            throw new RuntimeException(
                    "you have already applied for this job"
            );
        }
        Application application = new Application(candidate, job);

        return applicationRepository.save(application);
    }
}
