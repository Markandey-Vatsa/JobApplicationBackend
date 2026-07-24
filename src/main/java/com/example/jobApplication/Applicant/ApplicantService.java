package com.example.jobApplication.Applicant;

import java.util.List;

public interface ApplicantService {
    void addApplicant(Applicant applicant);

    void updateApplicant(Long applicantId, Applicant updatedApplicant);

    boolean deleteApplicant(Long applicantId);

    Applicant getApplicantById(Long applicantId);

    List<Applicant> getAllApplicants();
}
