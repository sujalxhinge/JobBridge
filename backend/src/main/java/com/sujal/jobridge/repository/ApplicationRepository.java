package com.sujal.jobridge.repository;

import com.sujal.jobridge.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    boolean existsByJobId(Long jobId);
}



