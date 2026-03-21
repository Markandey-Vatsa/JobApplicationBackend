package com.example.jobApplication.Job.impl;

import com.example.jobApplication.Company.Company;
import com.example.jobApplication.Company.CompanyRepository;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobRepository;
import com.example.jobApplication.Job.JobService;
import com.example.jobApplication.Recruiter.Recruiter;
import com.example.jobApplication.Recruiter.RecruiterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {
    JobServiceImpl(JobRepository jobRepository,CompanyRepository companyService,RecruiterRepository recruiterRepository){
        this.jobRepository = jobRepository;
        this.companyService = companyService;
        this.recruiterRepository = recruiterRepository;
    }
//    private List<Job> Jobs = new ArrayList<>();

    private final JobRepository jobRepository;
    private final CompanyRepository companyService;
    private final RecruiterRepository recruiterRepository;

    @Override
    public void createJob(Job job,Long companyId) {
//        Company com = companyService.findById(companyId).orElse(null);
//
//        if(com!=null) {
//            job.setCompany(com);
//            job.getCompany().setId(companyId);
//            jobRepository.save(job);
//            return true;
//        }

        Company company = companyService.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        Recruiter recruiter = recruiterRepository.findById(job.getRecruiter().getRecruiterId())
                .orElseThrow(() -> new EntityNotFoundException("Recruiter not found"));

        job.setCompany(company);
        job.setRecruiter(recruiter);

        jobRepository.save(job);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJob(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> optionalJob = jobRepository.findById(id);

        if(optionalJob.isPresent()){
            Job existingJob = optionalJob.get();
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setCompany(updatedJob.getCompany());
            existingJob.setLocation(updatedJob.getLocation());
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
            existingJob.setMinSalary(updatedJob.getMinSalary());
            existingJob.setId(updatedJob.getId());
            jobRepository.save(existingJob);
            return true;
        }
        return false;

    }

    @Override
    public List<Job> getJobsByCompanyId(Long companyId) {
        Company com = companyService.findById(companyId).orElse(null);
        if (com == null) return List.of();
        return com.getJobs();
    }


}
