package com.sujal.jobridge.repository;

import com.sujal.jobridge.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}

