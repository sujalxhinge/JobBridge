package com.sujal.jobridge.controller;

import com.sujal.jobridge.dto.ApplicationResponseDTO;
import com.sujal.jobridge.entity.Application;
import com.sujal.jobridge.mapper.ApplicationMapper;
import com.sujal.jobridge.service.ApplicationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    public ApplicationController(
            ApplicationService applicationService,
            ApplicationMapper applicationMapper) {

        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
    }

    @PostMapping("/{jobId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponseDTO applyForJob(
            @PathVariable Long jobId) {

        Application application =
                applicationService.applyForJob(jobId);

        return applicationMapper.toDTO(application);
    }
}