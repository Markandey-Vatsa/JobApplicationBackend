package com.example.jobApplication.Application.Impl;


import com.example.jobApplication.Application.Application;
import com.example.jobApplication.Application.ApplicationRepository;
import com.example.jobApplication.Application.ApplicationService;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    ApplicationServiceImpl(ApplicationRepository applicationRepository,
                           JobRepository jobRepository){
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    @Override
    public void addApplication(Application application) {
       // If client only provides a Job id, load the full Job entity so related recruiter is available
       if (application.getJob() != null && application.getJob().getId() != null) {
           Job job = jobRepository.findById(application.getJob().getId())
                   .orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + application.getJob().getId()));
           application.setJob(job);

       }

        try {
            applicationRepository.save(application);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Application already submitted !!");
        }
    }

    @Override
    public List<Application> getApplicationsByRecruiterId(Long RecruiterId) {
        return applicationRepository.findByJobRecruiterRecruiterId(RecruiterId);
    }

    @Override
    public List<Application> getAllApplicationsByApplicantId(Long applicantId) {
        return applicationRepository.findByApplicantApplicantId(applicantId);
    }

    @Override
    public boolean deleteApplication(Long applicationId) {
        if(applicationRepository.findById(applicationId).isEmpty()) return false;
        applicationRepository.deleteById(applicationId);
        return true;
    }

}
