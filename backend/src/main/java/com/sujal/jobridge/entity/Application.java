package com.sujal.jobridge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id",nullable = false)
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "job_id",nullable = false)
    private Job job;

    private LocalDateTime appliedAt;
    @PrePersist
    protected void onCreate(){
        appliedAt = LocalDateTime.now();
        if(status == null){
            status = ApplicationStatus.PENDING;
        }
    }

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
