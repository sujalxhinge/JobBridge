
package com.sujal.jobridge.controller;

import com.sujal.jobridge.entity.Job;
import com.sujal.jobridge.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
}
