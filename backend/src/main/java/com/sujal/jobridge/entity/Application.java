package com.sujal.jobridge.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications",
uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_candidate_job",
                columnNames = {"candidate_id","job_id"}
        )}
)
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

    protected Application(){

    }
    public Application(User candidate, Job job) {
        this.candidate = candidate;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
