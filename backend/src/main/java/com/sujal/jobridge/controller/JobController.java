
package com.sujal.jobridge.controller;

import com.sujal.jobridge.entity.Job;
import com.sujal.jobridge.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public Page<Job> getOpenJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return jobService.getOpenJobs(pageable);
    }

    @GetMapping("/search")
    public List<Job> searchJobsByTitle(@RequestParam String title){
        return jobService.searchJobsByTitle(title);
    }

    @GetMapping("/location")
    public List<Job> filterJobsByLocation(@RequestParam String location){
        return jobService.filterJobsByLocation(location);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Job createJob(@RequestBody Job job, @RequestParam Long recruiterId){
        return jobService.createJob(job,recruiterId);
    }

    @GetMapping("/{jobId}")
    public Job getJobById(@PathVariable Long jobId){
        return jobService.getJobById(jobId);
    }

    @PutMapping("/{jobId}")
    public Job updateJob(
            @PathVariable Long jobId,
            @RequestBody Job updatedJob) {

        return jobService.updateJob(jobId, updatedJob);
    }

    @DeleteMapping("/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
    }
}
