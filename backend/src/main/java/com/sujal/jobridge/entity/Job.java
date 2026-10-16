package com.sujal.jobridge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String location;
    private Double salaryMin;
    private Double salaryMax;
    private String jobType;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
    @ManyToOne
    @JoinColumn(name = "recruiter_id",nullable = false)
    private User Recruiter;

}
