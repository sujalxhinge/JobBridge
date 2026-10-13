# JobBridge — Database Design

## 1. Overview

JobBridge will use PostgreSQL as its relational database. Spring Data JPA and Hibernate will map Java entities to database tables. Primary keys, foreign keys, unique constraints, and validation will help maintain data integrity.

## 2. Database Tables

### 2.1 Users

Stores account information for candidates, recruiters, and admins.

| Column | Type | Constraints |
|---|---|---|
| id | BIGINT | Primary Key, Generated |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(150) | NOT NULL, UNIQUE |
| password | VARCHAR(255) | NOT NULL, stores password hash |
| role | VARCHAR(20) | NOT NULL |
| created_at | TIMESTAMP | NOT NULL |

Possible roles: CANDIDATE, RECRUITER, ADMIN.

Public registration will create CANDIDATE accounts only.

### 2.2 Jobs

Stores job listings created by recruiters.

| Column | Type | Constraints |
|---|---|---|
| id | BIGINT | Primary Key, Generated |
| title | VARCHAR(150) | NOT NULL |
| description | TEXT | NOT NULL |
| location | VARCHAR(150) | NOT NULL |
| salary_min | NUMERIC(12,2) | Optional |
| salary_max | NUMERIC(12,2) | Optional |
| job_type | VARCHAR(30) | NOT NULL |
| status | VARCHAR(20) | NOT NULL |
| created_at | TIMESTAMP | NOT NULL |
| recruiter_id | BIGINT | Foreign Key → Users(id) |

Possible job types: FULL_TIME, PART_TIME, INTERNSHIP, CONTRACT.

Possible statuses: OPEN, CLOSED, MODERATED.

Each job belongs to one recruiter. A recruiter can create multiple jobs.

### 2.3 Applications

Stores candidate applications and their current statuses.

| Column | Type | Constraints |
|---|---|---|
| id | BIGINT | Primary Key, Generated |
| candidate_id | BIGINT | Foreign Key → Users(id) |
| job_id | BIGINT | Foreign Key → Jobs(id) |
| applied_at | TIMESTAMP | NOT NULL |
| status | VARCHAR(20) | NOT NULL |

Possible statuses: PENDING, REVIEWED, SHORTLISTED, REJECTED, HIRED.

The combination of candidate_id and job_id must be UNIQUE, preventing duplicate applications to the same job.

## 3. Entity Relationships

1. Users (Recruiter) to Jobs: One-to-Many.
2. Users (Candidate) to Applications: One-to-Many.
3. Jobs to Applications: One-to-Many.
4. Applications to Users (Candidate): Many-to-One.
5. Applications to Jobs: Many-to-One.

The conceptual many-to-many relationship between candidates and jobs is represented by the Applications table because each application has its own status and application date.

## 4. Data Integrity Rules

- Every email address must be unique.
- Every job must reference an existing recruiter account.
- Every application must reference an existing candidate and job.
- A candidate cannot apply to the same job more than once.
- A candidate cannot apply to a closed job.
- Salary values must be non-negative, and the maximum salary must not be lower than the minimum salary when both are provided.
- Passwords must never be stored as plain text.
- Existing applications must remain available when a job is closed.

## 5. JPA Mapping Strategy

- Use `@Entity` to map Java classes to database tables.
- Use `@Id` and `@GeneratedValue` for generated primary keys.
- Use `@ManyToOne` and `@JoinColumn` to map foreign-key relationships.
- Use `@Column(unique = true)` or an equivalent database constraint for unique email addresses.
- Define a composite unique constraint on Applications(candidate_id, job_id).
- Use enums for roles, job types, job statuses, and application statuses.
- Use database constraints in addition to application-level validation.

## 6. Initial Design Decisions

The initial implementation will use three tables: Users, Jobs, and Applications. In Java, the User entity will represent all account roles, with authorization enforced by Spring Security.

We will review the schema before implementing entities and repositories.