package com.example.jobApplication.Reviews.Impl;

import com.example.jobApplication.Company.Company;
import com.example.jobApplication.Company.CompanyRepository;
import com.example.jobApplication.Reviews.Review;
import com.example.jobApplication.Reviews.ReviewRepository;
import com.example.jobApplication.Reviews.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ReviewRepository reviewRepository;

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
    public boolean addReviewToCompany(Long companyId, Review review) {
        Company com = companyRepository.findById(companyId).orElse(null);
        if(com != null){
            review.setCompany(com);
            com.getReviews().add(review);
            companyRepository.save(com);
            return true;
        }
        return false;
    }


    @Override
    @Transactional(readOnly = true)
    public Review getReviewById(Long companyId, Long reviewId) {
        return reviewRepository.findById(reviewId)
                .filter(r -> r.getCompany() != null && companyId.equals(r.getCompany().getId()))
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean UpdateReview(Long companyId, Long reviewId, Review updatedReview) {
        return reviewRepository.findById(reviewId)
                .filter(r -> r.getCompany() != null && companyId.equals(r.getCompany().getId()))
                .map(r -> {
                    r.setReviewerName(updatedReview.getReviewerName());
                    r.setReviewText(updatedReview.getReviewText());
                    r.setRating(updatedReview.getRating());
                    reviewRepository.save(r);
                    return true;
                }).orElse(false);
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
