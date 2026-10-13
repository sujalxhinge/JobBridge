# JobBridge — Use Case Document

## 1. Introduction

This document describes how different users interact with the JobBridge Job Portal and Application Tracking System. It identifies the system's actors, their responsibilities, and the main use cases supported by the application.

## 2. Actors

### 2.1 Guest
A user who has not logged in. Guests can register as candidates and browse available jobs.

### 2.2 Candidate
A registered user who can browse jobs, apply for jobs, and track application statuses.

### 2.3 Recruiter
A registered, authorized user who can create and manage job listings and review applications submitted for their own jobs.

### 2.4 Admin
An authorized user responsible for managing users and moderating job listings.

## 3. Use Case List

| ID | Use Case | Actor |
|---|---|---|
| UC-01 | Register Account | Guest |
| UC-02 | Login | Candidate, Recruiter, Admin |
| UC-03 | Browse Jobs | Guest, Candidate |
| UC-04 | Search and Filter Jobs | Guest, Candidate |
| UC-05 | View Job Details | Guest, Candidate |
| UC-06 | Apply for a Job | Candidate |
| UC-07 | Track Applications | Candidate |
| UC-08 | Create Job Listing | Recruiter |
| UC-09 | Update or Close Job Listing | Recruiter |
| UC-10 | View Applicants | Recruiter |
| UC-11 | Update Application Status | Recruiter |
| UC-12 | Manage Users | Admin |
| UC-13 | Moderate Job Listings | Admin |

## 4. Detailed Use Cases

### UC-01: Register Account

**Primary Actor:** Guest

**Preconditions:**
- The user is not required to have an existing account.
- The email address must not already be registered.

**Main Flow:**
1. The guest opens the registration page.
2. The guest enters their name, email, and password.
3. The system validates the submitted information.
4. The system checks whether the email address is already registered.
5. The system securely hashes the password.
6. The system creates a Candidate account.
7. The system confirms successful registration.

**Alternative Flows:**
- If the email already exists, the system rejects the registration.
- If the submitted data is invalid, the system returns validation errors.

**Postcondition:** A Candidate account is created successfully.

**Security Rule:** Public registration must not allow users to assign themselves Recruiter or Admin roles.

### UC-02: Login

**Primary Actor:** Candidate, Recruiter, Admin

**Preconditions:**
- The user has an existing account.

**Main Flow:**
1. The user enters their email and password.
2. The system verifies the credentials.
3. The system generates a JWT access token after successful authentication.
4. The system returns the token and appropriate user information.

**Alternative Flow:**
- Invalid credentials result in an authentication error.
- Expired or invalid JWT tokens cannot be used to access protected resources.

**Postcondition:** The authenticated user can access resources permitted by their role.

### UC-03: Browse Jobs

**Primary Actors:** Guest, Candidate

**Main Flow:**
1. The user opens the jobs page.
2. The system retrieves available open jobs.
3. The system displays job titles, companies or recruiters, locations, and other relevant details.
4. The user selects a job to view more information.

**Postcondition:** The user can view currently open job listings without needing to log in.

### UC-04: Search and Filter Jobs

**Primary Actors:** Guest, Candidate

**Main Flow:**
1. The user enters a keyword or selects filters.
2. The system searches open jobs using supported criteria such as keyword, location, job type, and salary range.
3. The system returns matching results.
4. If no jobs match, the system displays an appropriate empty result.

**Postcondition:** The user sees jobs matching the selected criteria.

### UC-05: View Job Details

**Primary Actors:** Guest, Candidate

**Main Flow:**
1. The user selects a job listing.
2. The system retrieves the job details.
3. The system displays the description, location, job type, salary information when available, and other relevant details.
4. If the job is closed, the system indicates that it is no longer accepting applications.

**Postcondition:** The user can review the selected job's details.

### UC-06: Apply for a Job

**Primary Actor:** Candidate

**Preconditions:**
- The candidate is authenticated.
- The job exists and is open.
- The candidate has not already applied for that job.

**Main Flow:**
1. The candidate opens an eligible job listing.
2. The candidate selects Apply.
3. The system verifies the candidate's identity and eligibility.
4. The system checks that the job is open and no application already exists for that candidate and job.
5. The system creates an application with an initial status of Pending.
6. The system confirms successful submission.

**Alternative Flows:**
- Unauthenticated users must log in before applying.
- Applications to closed jobs are rejected.
- Duplicate applications are rejected.
- Invalid requests return appropriate error messages.

**Postcondition:** One application is recorded for the candidate and job.

### UC-07: Track Applications

**Primary Actor:** Candidate

**Preconditions:**
- The candidate is authenticated.

**Main Flow:**
1. The candidate opens the My Applications page.
2. The system retrieves applications belonging to that candidate.
3. The system displays the associated job details, application date, and current status.
4. The candidate views status changes made by the recruiter.

**Postcondition:** The candidate can track only their own applications.

### UC-08: Create Job Listing

**Primary Actor:** Recruiter

**Preconditions:**
- The recruiter is authenticated and authorized.

**Main Flow:**
1. The recruiter opens the job creation form.
2. The recruiter enters the job title, description, location, job type, and other supported details.
3. The system validates the submitted information.
4. The system associates the job listing with the authenticated recruiter.
5. The system saves the listing.
6. The system confirms successful creation.

**Alternative Flow:** Invalid or incomplete data is rejected with validation errors.

**Postcondition:** A new job listing is created and associated with its recruiter.

### UC-09: Update or Close Job Listing

**Primary Actor:** Recruiter

**Preconditions:**
- The recruiter is authenticated.
- The job exists and belongs to that recruiter.

**Main Flow:**
1. The recruiter selects one of their job listings.
2. The recruiter edits permitted details or closes the job.
3. The system verifies ownership and validates the changes.
4. The system saves the updated listing or closed status.
5. The system confirms the operation.

**Alternative Flows:**
- A recruiter cannot modify another recruiter's listing.
- Invalid changes are rejected.

**Postcondition:** The listing is updated or closed. Existing applications remain available even after the job closes.

### UC-10: View Applicants

**Primary Actor:** Recruiter

**Preconditions:**
- The recruiter is authenticated.
- The recruiter owns the selected job listing.

**Main Flow:**
1. The recruiter selects one of their job listings.
2. The system verifies ownership.
3. The system retrieves applications submitted for that job.
4. The recruiter views the permitted applicant and application information.

**Postcondition:** The recruiter can view applicants only for their own jobs.

### UC-11: Update Application Status

**Primary Actor:** Recruiter

**Preconditions:**
- The recruiter is authenticated.
- The application belongs to a job owned by that recruiter.

**Main Flow:**
1. The recruiter selects an application.
2. The recruiter chooses a permitted status, such as Reviewed, Shortlisted, Rejected, or Hired.
3. The system verifies ownership and validates the requested status.
4. The system updates the application.
5. The updated status becomes visible to the candidate.

**Alternative Flow:** Unauthorized status changes are rejected.

**Postcondition:** The application status is updated and can be viewed by the relevant candidate.

### UC-12: Manage Users

**Primary Actor:** Admin

**Preconditions:**
- The user is authenticated and has Admin privileges.

**Main Flow:**
1. The admin opens the user management section.
2. The system displays permitted user information.
3. The admin performs an allowed management operation.
4. The system verifies authorization and saves the change.

**Postcondition:** The permitted user management operation is completed.

### UC-13: Moderate Job Listings

**Primary Actor:** Admin

**Preconditions:**
- The admin is authenticated and authorized.

**Main Flow:**
1. The admin opens the job moderation section.
2. The system displays job listings requiring review or management.
3. The admin selects a listing and performs an allowed moderation action.
4. The system validates authorization and records the change.

**Postcondition:** The selected listing is moderated according to the admin's permissions.

## 5. General Security Rules

1. Passwords must be stored as secure hashes, never as plain text.
2. Protected APIs require valid authentication.
3. Role-based authorization must be enforced by the backend.
4. Recruiters can manage only their own job listings and related applications.
5. Candidates can view and manage only their own applications.
6. Public registration creates Candidate accounts only.
7. Client-side checks must not replace backend authorization checks.

## 6. Conclusion

These use cases define the main interactions required for the JobBridge MVP. They will guide database design, REST API development, Spring Security implementation, React integration, and Postman testing.