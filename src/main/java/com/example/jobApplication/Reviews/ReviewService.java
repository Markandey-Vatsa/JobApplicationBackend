package com.example.jobApplication.Reviews;

import com.example.jobApplication.Applicant.Applicant;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByCompanyId(Long companyId);
    boolean addReviewToCompany(Long companyId, Review review);
    Review getReviewById(Long companyId, Long reviewId);
    boolean UpdateReview(Long companyId, Long reviewId, Review updatedReview);
    boolean deleteReview(Long companyId, Long reviewId);
}
