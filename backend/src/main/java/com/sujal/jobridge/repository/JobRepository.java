package com.sujal.jobridge.repository;

import com.sujal.jobridge.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sujal.jobridge.entity.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingIgnoreCaseAndStatus(
            String title, JobStatus status);

    List<Job> findByLocationIgnoreCaseAndStatus(
            String location, JobStatus status);

    Page<Job> findByStatus(JobStatus status,Pageable pageable);
}

