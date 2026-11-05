
package com.sujal.jobridge.controller;

import com.sujal.jobridge.dto.JobResponseDTO;
import com.sujal.jobridge.entity.Job;
import com.sujal.jobridge.mapper.JobMapper;
import com.sujal.jobridge.service.JobService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final JobMapper jobMapper;

    public JobController(JobService jobService, JobMapper jobMapper) {
        this.jobService = jobService;
        this.jobMapper = jobMapper;
    }

    // Get all open jobs with pagination
    @GetMapping
    public Page<JobResponseDTO> getOpenJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return jobService.getOpenJobs(pageable)
                .map(jobMapper::toDTO);
    }

    // Search jobs by title
    @GetMapping("/search")
    public List<JobResponseDTO> searchJobsByTitle(
            @RequestParam String title) {

        return jobService.searchJobsByTitle(title)
                .stream()
                .map(jobMapper::toDTO)
                .toList();
    }

    // Filter jobs by location
    @GetMapping("/location")
    public List<JobResponseDTO> filterJobsByLocation(
            @RequestParam String location) {

        return jobService.filterJobsByLocation(location)
                .stream()
                .map(jobMapper::toDTO)
                .toList();
    }

    // Create a new job
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponseDTO createJob(
            @RequestBody Job job,
            @RequestParam Long recruiterId) {

        Job savedJob = jobService.createJob(job, recruiterId);

        return jobMapper.toDTO(savedJob);
    }

    // Get job by ID
    @GetMapping("/{jobId}")
    public JobResponseDTO getJobById(
            @PathVariable Long jobId) {

        Job job = jobService.getJobById(jobId);

        return jobMapper.toDTO(job);
    }

    // Update job
    @PutMapping("/{jobId}")
    public JobResponseDTO updateJob(
            @PathVariable Long jobId,
            @RequestBody Job updatedJob) {

        Job updated = jobService.updateJob(jobId, updatedJob);

        return jobMapper.toDTO(updated);
    }

    // Delete job
    @DeleteMapping("/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable Long jobId) {

        jobService.deleteJob(jobId);
    }
}
