# JobBridge — Software Requirements Specification (SRS)

**Project Name:** JobBridge — Job Portal & Application Tracking System  
**Document Version:** 1.0  
**Status:** Initial Draft  
**Project Start Date:** 13 October 2026  
**Target Release Date:** 15 November 2026  
**Backend:** Java, Spring Boot, Spring Security  
**Frontend:** React.js  
**Database:** PostgreSQL

---

## 1. Introduction

### 1.1 Purpose

The purpose of JobBridge is to develop a full-stack web application that connects job seekers with recruiters. The application will allow candidates to browse and apply for jobs, recruiters to manage job listings and applications, and administrators to manage users and moderate job listings.

This document defines the functional requirements, non-functional requirements, user roles, business rules, and acceptance criteria for the Minimum Viable Product (MVP).

### 1.2 Project Scope

JobBridge will provide a centralized platform for job discovery and application management.

The MVP will include user authentication, role-based authorization, job management, job search and filtering, job applications, application status tracking, and administrative moderation.

The application will use a React frontend, a Spring Boot REST API, and a PostgreSQL database. The frontend, backend, and database will be deployed to suitable cloud hosting services.

### 1.3 Objectives

- Develop RESTful APIs using Java and Spring Boot.
- Implement authentication and authorization using Spring Security and JWT.
- Design and manage relational data using PostgreSQL and Spring Data JPA.
- Develop a responsive user interface using React.
- Implement job search, filtering, pagination, and sorting.
- Apply input validation, exception handling, and secure access control.
- Test and deploy the application to a publicly accessible environment.

### 1.4 Intended Audience

This document is intended for the developer, project reviewers, and recruiters evaluating the project's design and implementation.

---

## 2. Overall Description

### 2.1 Product Perspective

JobBridge is a client-server web application. The React frontend communicates with the Spring Boot backend through REST APIs over HTTPS. The backend validates requests, applies business rules, enforces security, and accesses PostgreSQL through Spring Data JPA.

### 2.2 User Classes

#### Candidate
A candidate can register, log in, browse available jobs, search and filter listings, apply for jobs, and track application status.

#### Recruiter
A recruiter can create and manage their own job listings, view applicants for their jobs, and update application statuses.

#### Admin
An administrator can view registered users, manage user roles where appropriate, and moderate job listings.

### 2.3 Operating Environment

- Modern web browsers on desktop and mobile devices.
- React frontend hosted on a cloud platform.
- Spring Boot backend hosted as a web service.
- Cloud-hosted PostgreSQL database.
- Git and GitHub for version control and project documentation.

### 2.4 Assumptions and Constraints

- The initial release is intended for portfolio demonstration and learning.
- Cloud deployment will use available free tiers where suitable.
- Free hosting may introduce cold starts, resource limits, or temporary service interruptions.
- Users need an internet connection to access the deployed application.
- The project must be completed by 15 November 2026.
- Optional features must not delay the core release.

---

## 3. Functional Requirements

### FR-01: User Registration

- The system shall allow candidates to register using required account details.
- The system shall validate required fields and reject duplicate email addresses.
- Passwords shall be stored as secure password hashes, not plaintext.
- Public registration shall not allow users to assign themselves the Admin role.
- Recruiter and Admin accounts shall be provisioned through a controlled process.

### FR-02: User Authentication

- The system shall allow registered users to log in using their credentials.
- The system shall authenticate requests using Spring Security and JWT.
- The system shall reject invalid, expired, or improperly signed tokens.
- The system shall return appropriate errors for failed authentication.
- The system shall not expose passwords or sensitive authentication data in API responses.

### FR-03: Role-Based Authorization

- The system shall distinguish Candidate, Recruiter, and Admin permissions.
- Only authorized recruiters shall manage their own job listings.
- Candidates shall manage their own applications.
- Recruiters shall access applicants only for jobs they own, unless an authorized administrative action applies.
- Admin-only operations shall be restricted to authorized administrators.

### FR-04: Job Management

- Recruiters shall be able to create job listings.
- Recruiters shall be able to view, update, and close their own listings.
- The system shall store job title, description, location, salary information, job type, status, creation date, and recruiter association.
- The system shall validate required job information.
- Candidates shall be able to view open job listings.
- Administrators shall be able to moderate inappropriate or invalid listings.

### FR-05: Job Search and Filtering

- Users shall be able to search jobs by keywords.
- Users shall be able to filter listings by location and job type.
- The system shall support salary range filtering where salary data is available.
- The system shall return an appropriate empty result when no jobs match the criteria.

### FR-06: Pagination and Sorting

- The system shall return job listings in paginated results.
- Users shall be able to select supported sorting options, such as newest first and salary order.
- The backend shall validate pagination parameters and restrict sorting to supported fields.
- The API shall return pagination metadata where appropriate.

### FR-07: Job Applications

- Authenticated candidates shall be able to apply for open jobs.
- The system shall prevent a candidate from applying to the same job more than once.
- The system shall reject applications for closed jobs.
- The system shall record the candidate, job, application date, and current status.

### FR-08: Application Tracking

- Candidates shall be able to view their own applications and statuses.
- Recruiters shall be able to view applications submitted to their own jobs.
- Recruiters shall be able to update application statuses.
- The system shall restrict application access and status changes to authorized users.

### FR-09: Error Handling and Validation

- The backend shall validate incoming requests.
- The system shall return meaningful HTTP status codes and consistent error responses.
- The backend shall handle expected errors without exposing internal stack traces or sensitive information.
- The system shall prevent unauthorized access to protected resources.

### FR-10: API Documentation

- The project shall provide API documentation using Swagger/OpenAPI.
- The documentation shall describe endpoints, parameters, request bodies, response formats, and authentication requirements.

---

## 4. Non-Functional Requirements

### NFR-01: Security

- Passwords shall be hashed using an appropriate password encoder such as BCrypt.
- Protected endpoints shall enforce authentication and authorization on the server.
- Secrets, database credentials, and JWT signing keys shall be supplied through environment variables or secure configuration.
- The deployed application shall use HTTPS.
- The backend shall validate resource ownership to prevent users from accessing other users' private data.

### NFR-02: Performance

- Job search, filtering, and pagination shall avoid unnecessarily loading every job record into memory.
- Database queries shall retrieve only the data required for each request.
- The application shall remain usable within the resource constraints of its chosen hosting plans.

### NFR-03: Reliability

- The application shall handle invalid requests and expected database errors gracefully.
- Important user data shall be stored in persistent database storage.
- Deployment and database configuration shall be documented for reproducibility.

### NFR-04: Maintainability

- The backend shall use a layered structure consisting of controllers, services, and repositories.
- DTOs shall be used where appropriate to separate API request/response models from database entities.
- Business logic shall not be placed entirely in controller classes.
- Naming and package structure shall remain consistent.

### NFR-05: Testability

- Important business rules shall be covered by automated tests.
- Authentication and authorization shall be tested for allowed and denied requests.
- Critical API workflows shall be verified before release.

### NFR-06: Usability

- The frontend shall provide clear navigation for each user role.
- Forms shall display validation and error messages.
- Job listings shall support basic desktop and mobile layouts.
- Loading, empty, and error states shall be handled clearly.

---

## 5. Business Rules

1. Every user shall have a unique email address.
2. A public registration request shall not be allowed to grant administrative privileges.
3. A recruiter may manage only the job listings they own.
4. Only open jobs may receive new applications.
5. A candidate may submit at most one application per job.
6. Candidates may view only their own application records.
7. Recruiters may manage application statuses only for applications associated with their own jobs.
8. Job search and pagination parameters shall be validated by the backend.
9. Role checks alone shall not replace ownership checks on individual resources.
10. Closing a job shall prevent new applications but shall not automatically delete existing applications.

---

## 6. Out of Scope

The following features are excluded from the initial MVP:

- Resume upload and file management.
- Automated email notifications.
- Real payment processing.
- Chat and video interviews.
- AI-based job recommendations.
- Microservices and Kubernetes.
- Advanced analytics and reporting dashboards.

These features may be considered after the core application is tested and deployed.

---

## 7. Acceptance Criteria

The MVP will be considered ready for release when:

1. A candidate can register and log in successfully.
2. Authentication and role-based authorization work correctly.
3. Recruiters can create and manage their own job listings.
4. Candidates can browse, search, filter, paginate, and sort job listings.
5. Candidates can apply for open jobs and track application status.
6. Duplicate applications and applications to closed jobs are rejected.
7. Recruiters can manage applications only for their own listings.
8. Validation and error handling return appropriate responses.
9. Automated tests cover important business rules and access restrictions.
10. API documentation is available.
11. The frontend, backend, and database are configured for cloud deployment.
12. The deployed application can complete the main user workflows using its public URL.

---

## 8. Release Plan

The project will be developed in the following stages:

1. Requirements analysis and system design.
2. Backend foundation and job-management APIs.
3. Registration, authentication, and authorization.
4. Job application workflows and business logic.
5. React frontend and API integration.
6. Testing, API documentation, and cloud deployment.
7. Final verification, bug fixing, and resume preparation.

**Target release date:** 15 November 2026.
