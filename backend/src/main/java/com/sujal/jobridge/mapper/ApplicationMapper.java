package com.sujal.jobridge.mapper;

import com.sujal.jobridge.dto.ApplicationResponseDTO;
import com.sujal.jobridge.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationResponseDTO toDTO(Application application) {

        ApplicationResponseDTO dto = new ApplicationResponseDTO();

        dto.setId(application.getId());
        dto.setJobId(application.getJob().getId());
        dto.setJobTitle(application.getJob().getTitle());
        dto.setLocation(application.getJob().getLocation());
        dto.setAppliedAt(application.getAppliedAt());
        dto.setStatus(application.getStatus());

        return dto;
    }
}