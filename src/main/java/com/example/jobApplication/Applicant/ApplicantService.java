package com.example.jobApplication.Applicant;

public interface ApplicantService{
    void addApplicant(Applicant applicant);
    void updateApplicant(Long applicantId, Applicant updatedApplicant);
    boolean deleteApplicant(Long applicantId);
    Applicant getApplicantById(Long applicantId);
}
