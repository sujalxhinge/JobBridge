package com.sujal.jobridge.controller;

import com.sujal.jobridge.dto.ApplicationResponseDTO;
import com.sujal.jobridge.entity.Application;
import com.sujal.jobridge.mapper.ApplicationMapper;
import com.sujal.jobridge.service.ApplicationService;
import java.util.List;
import java.util.stream.Collectors;
import com.sujal.jobridge.dto.UpdateApplicationStatusRequest;

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

    @GetMapping("/my-applications")
    public List<ApplicationResponseDTO> getMyApplications() {

        List<Application> applications =
                applicationService.getMyApplications();

        return applications.stream()
                .map(applicationMapper::toDTO)
                .toList();
    }

    @GetMapping("/job/{jobId}")
    public List<ApplicationResponseDTO> getApplicantsForJob(
            @PathVariable Long jobId) {

        List<Application> applications =
                applicationService.getApplicantsForJob(jobId);

        return applications.stream()
                .map(applicationMapper::toDTO)
                .toList();
    }

    @PutMapping("/{applicationId}/status")
    public ApplicationResponseDTO updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody UpdateApplicationStatusRequest request) {

        Application updated = applicationService.updateApplicationStatus(
                applicationId,
                request.getStatus()
        );

        return applicationMapper.toDTO(updated);
    }
}