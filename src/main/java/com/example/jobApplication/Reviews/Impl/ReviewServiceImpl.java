package com.example.jobApplication.Reviews.Impl;

import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Applicant.ApplicantRepository;
import com.example.jobApplication.Company.Company;
import com.example.jobApplication.Company.CompanyRepository;
import com.example.jobApplication.Reviews.Review;
import com.example.jobApplication.Reviews.ReviewRepository;
import com.example.jobApplication.Reviews.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewServiceImpl(CompanyRepository companyRepository,
                      ReviewRepository reviewRepository,
                      ApplicantRepository applicantRepository){
        this.companyRepository = companyRepository;
        this.reviewRepository = reviewRepository;
        this.applicantRepository = applicantRepository;
    }

    private final CompanyRepository companyRepository;
    private final ReviewRepository reviewRepository;
    private final ApplicantRepository applicantRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Review> getReviewsByCompanyId(Long companyId) {
        Company com = companyRepository.findById(companyId).orElse(null);

        if(com != null){
            return com.getReviews()==null?List.of():com.getReviews();
        }

        return List.of();

    }

    @Override
    @Transactional
    public void addReviewToCompany(Long companyId, Review review, Long applicantId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new EntityNotFoundException("Applicant not found"));

        // Set managed entities
        review.setCompany(company);
        review.setApplicant(applicant);

        // Optional: service-level check
        if (reviewRepository.existsByApplicantApplicantIdAndCompanyId(applicantId, companyId)) {
            throw new IllegalStateException("You have already reviewed this company");
        }

        try {
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("You have already reviewed this company");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Review getReviewById(Long companyId, Long reviewId) {
        return reviewRepository.findById(reviewId)
                .filter(r -> r.getCompany() != null && companyId.equals(r.getCompany().getId()))
                .orElse(null);
    }


    @Transactional
    @Override
    public void UpdateReview(Long companyId, Long reviewId,Long applicantId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));


        if (review.getCompany() == null || !companyId.equals(review.getCompany().getId())) {
            throw new IllegalArgumentException("Review does not belong to this company");
        }

        if (review.getApplicant() == null || !applicantId.equals(review.getApplicant().getApplicantId())) {
            throw new IllegalStateException("You are not allowed to update this review");
        }

        if (updatedReview.getReviewText() != null) {
            review.setReviewText(updatedReview.getReviewText());
        }

        if (updatedReview.getRating() != 0) { // assuming 1–5 valid
            review.setRating(updatedReview.getRating());
        }
    }

    @Override
    @Transactional
    public boolean deleteReview(Long companyId, Long reviewId) {
        return reviewRepository.findById(reviewId)
                .filter(r -> r.getCompany() != null && companyId.equals(r.getCompany().getId()))
                .map(r -> {
                    reviewRepository.delete(r);
                    return true;
                }).orElse(false);
    }





}
