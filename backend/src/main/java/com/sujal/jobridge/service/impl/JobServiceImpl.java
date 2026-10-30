package com.sujal.jobridge.service.impl;

import com.sujal.jobridge.entity.Job;
import com.sujal.jobridge.entity.JobStatus;
import com.sujal.jobridge.exception.ResourceNotFoundException;
import com.sujal.jobridge.repository.JobRepository;
import com.sujal.jobridge.service.JobService;
import org.springframework.stereotype.Service;
import com.sujal.jobridge.entity.Role;
import com.sujal.jobridge.entity.User;
import com.sujal.jobridge.repository.UserRepository;
import com.sujal.jobridge.exception.BusinessRuleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobServiceImpl(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(Long jobId){
        return jobRepository.findById(jobId)
                .orElseThrow(()->
                        new ResourceNotFoundException("job not found with this id : "+jobId));
    }

    //Create Job Method
    @Override
    public Job createJob(Job job,Long recruiterId){
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Recruiter not found with id: " + recruiterId));

        if(recruiter.getRole()!=Role.RECRUITER){
            throw new BusinessRuleException("Only recruiters can create jobs");
        }
        job.setRecruiter(recruiter);
        return jobRepository.save(job);
    }
    @Override
    public List<Job> searchJobsByTitle(String title){
        return jobRepository.findByTitleContainingIgnoreCaseAndStatus(title, JobStatus.OPEN);
    }
    @Override
    public List<Job> filterJobsByLocation(String location){
        return jobRepository.findByLocationIgnoreCaseAndStatus(location,JobStatus.OPEN);
    }
    @Override
    public Page<Job> getOpenJobs(Pageable pageable) {
        return jobRepository.findByStatus(JobStatus.OPEN, pageable);
    }
    @Override
    public Job updateJob(Long jobId, Job updatedJob) {

        Job existingJob = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + jobId));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalaryMin(updatedJob.getSalaryMin());
        existingJob.setSalaryMax(updatedJob.getSalaryMax());
        existingJob.setJobType(updatedJob.getJobType());
        existingJob.setStatus(updatedJob.getStatus());

        return jobRepository.save(existingJob);
    }
}
