package com.example.jobApplication.Reviews;

import com.example.jobApplication.Applicant.Applicant;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByCompanyId(Long companyId);
    void addReviewToCompany(Long companyId, Review review, Long applicantId);
    Review getReviewById(Long companyId, Long reviewId);
    void UpdateReview(Long companyId, Long reviewId,Long applicantId,Review updatedReview);
    boolean deleteReview(Long companyId, Long reviewId);
}
