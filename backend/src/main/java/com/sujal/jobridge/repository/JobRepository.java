package com.sujal.jobridge.repository;

import com.sujal.jobridge.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByLocationIgnoreCase(String location);
}

