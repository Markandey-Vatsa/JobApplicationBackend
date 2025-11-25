package com.example.jobApplication.Application.Impl;

import com.example.jobApplication.Application.Application;
import com.example.jobApplication.Application.ApplicationRepository;
import com.example.jobApplication.Application.ApplicationService;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    JobRepository jobRepository;

    @Override
    public boolean addApplication(Application application) {
       // If client only provides a Job id, load the full Job entity so related recruiter is available
       if (application.getJob() != null && application.getJob().getId() != null) {
           Job job = jobRepository.findById(application.getJob().getId())
                   .orElseThrow(() -> new RuntimeException("Job not found with id: " + application.getJob().getId()));
           application.setJob(job);

           if (job.getRecruiter() != null && job.getRecruiter().getRecruiterId() != null) {
               application.setRecruiterId(job.getRecruiter().getRecruiterId());
           } else {
               application.setRecruiterId(null);
           }
       }

       applicationRepository.save(application);
       return true;
    }

    @Override
    public List<Application> getApplicationsByRecruiterId(Long RecruiterId) {
        return applicationRepository.findAllByRecruiterId(RecruiterId);
    }

    @Override
    public List<Application> getAllApplicationsByApplicantId(Long applicantId) {
        return applicationRepository.findByApplicantApplicantId(applicantId);
    }

    @Override
    public boolean deleteApplication(Long applicationId) {
        try {
            applicationRepository.deleteById(applicationId);
            return true;
        }catch(Exception e){
            return false;
        }
    }


}
