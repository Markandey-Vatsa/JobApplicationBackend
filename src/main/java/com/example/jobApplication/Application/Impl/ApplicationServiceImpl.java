package com.example.jobApplication.Application.Impl;


import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Applicant.ApplicantRepository;
import com.example.jobApplication.Application.Application;
import com.example.jobApplication.Application.ApplicationRepository;
import com.example.jobApplication.Application.ApplicationService;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.connector.Response;
import org.hibernate.annotations.DialectOverride;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    ApplicationServiceImpl(ApplicationRepository applicationRepository,
                           JobRepository jobRepository,
                           ApplicantRepository applicantRepository){
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.applicantRepository = applicantRepository;
    }

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final ApplicantRepository applicantRepository;


    @Override
    public void addApplication(Application application) {

        // Load the Job from the database
        Job job = jobRepository.findById(application.getJob().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Job not found with id: " + application.getJob().getId()));

        // Load the Applicant from the database
        Applicant applicant = applicantRepository.findById(application.getApplicant().getApplicantId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Applicant not found with id: " + application.getApplicant().getApplicantId()));

        // Ensure the applicant has uploaded a resume
        if (applicant.getResumeLink() == null || applicant.getResumeLink().isBlank()) {
            throw new IllegalStateException(
                    "Resume not available. Kindly upload your resume on your profile before applying.");
        }

        // Attach managed entities
        application.setJob(job);
        application.setApplicant(applicant);

        // Store a snapshot of the resume used for this application
        application.setResumeLink(applicant.getResumeLink());

        try {
            applicationRepository.save(application);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Application already submitted!!");
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

    @Override
    public ResponseEntity<Resource> getApplicantResumeForRecruiter(Long applicationId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Application not found"));

        if (application.getResumeLink() == null || application.getResumeLink().isBlank()) {
            throw new EntityNotFoundException("Resume not available.");
        }

        try {
            Path path = Paths.get(application.getResumeLink());
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new EntityNotFoundException("Resume file not found.");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to load resume.", e);
        }
    }

}
