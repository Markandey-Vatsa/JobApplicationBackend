package com.example.jobApplication.Job.impl;

import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class JobServiceImpl implements JobService {

    private Long nextId = 1L;

    private List<Job> Jobs = new ArrayList<>();


    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        Jobs.add(job);
    }

    @Override
    public List<Job> findAll() {
        return Jobs;
    }

    @Override
    public Job getJob(Long id) {
        for(Job job: Jobs){
            if(job.getId().equals(id)){
                return job;
            }
        }
        return null;
    }

    @Override
    public boolean deleteJob(Long id) {
        for(Job job:Jobs){
            if(job.getId().equals(id)){
                Jobs.remove(job);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        for(Job job:Jobs){
            if(job.getId().equals(id)){
                job.setTitle(updatedJob.getTitle());
                job.setCompany(updatedJob.getCompany());
                job.setDescription(updatedJob.getDescription());
                job.setLocation(updatedJob.getLocation());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setMinSalary(updatedJob.getMinSalary());

                return true;
            }
        }

        return false;
    }

}
