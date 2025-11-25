package com.example.jobApplication.Job;

import java.util.List;

public interface JobService {
    boolean createJob(Job job, Long companyId);
    List<Job> findAll();
    Job getJob(Long id);
    boolean deleteJob(Long id);
    boolean updateJob(Long id, Job updatedJob);
    List<Job> getJobsByCompanyId(Long companyId);
}
