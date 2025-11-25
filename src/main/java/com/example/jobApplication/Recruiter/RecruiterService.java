package com.example.jobApplication.Recruiter;

import com.example.jobApplication.Job.Job;

import java.util.List;

public interface RecruiterService {

    void addRecruiter(Recruiter recruiter);
    void updateRecruiter(Long recruiterId, Recruiter updatedRecruiter);
    boolean deleteRecruiter(Long recruiterId);
    Recruiter getRecruiterById(Long recruiterId);
    List<Recruiter> getAllRecruiters();
    List<Job> getJobsByRecruiter(Long recruiterId);
    List<Recruiter> getRecruitersByCompany(Long companyId);
}
