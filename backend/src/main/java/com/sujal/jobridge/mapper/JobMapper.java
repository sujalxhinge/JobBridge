package com.sujal.jobridge.mapper;

import com.sujal.jobridge.dto.JobResponseDTO;
import com.sujal.jobridge.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobResponseDTO toDTO(Job job) {

        JobResponseDTO dto = new JobResponseDTO();

        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setSalaryMin(job.getSalaryMin());
        dto.setSalaryMax(job.getSalaryMax());
        dto.setJobType(job.getJobType());
        dto.setStatus(job.getStatus());
        dto.setCreatedAt(job.getCreatedAt());

        // Extract only recruiter name
        dto.setRecruiterName(job.getRecruiter().getName());

        return dto;
    }
}