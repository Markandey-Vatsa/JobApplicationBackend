package com.example.jobApplication.Application;
import java.util.List;
public interface ApplicationService {

    boolean addApplication(Application application);

//    For recruiter to see all applications for a specific job
//    List<Application> getApplicationsByJobId(Long jobId);

    List<Application>getApplicationsByRecruiterId(Long recruiterId);

//    For applicant to see all their applications
    List<Application> getAllApplicationsByApplicantId(Long applicantId);

    boolean deleteApplication(Long applicationId);

}
