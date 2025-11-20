package com.example.jobApplication.Job;

import java.util.List;

public interface JobService {
    void createJob(Job job);
    List<Job> findAll();
    Job getJob(Long id);
    boolean deleteJob(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
