
package com.sujal.jobridge.service;

import com.sujal.jobridge.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    List<Job> getAllJobs();

    Job getJobById(Long jobId);

    Job createJob(Job job, Long recruiterId);

    List<Job> searchJobsByTitle(String title);

    List<Job> filterJobsByLocation(String location);

    Page<Job> getOpenJobs(Pageable pageable);

    Job updateJob(Long jobId, Job updateJob);

    void deleteJob(Long jobId);
}
