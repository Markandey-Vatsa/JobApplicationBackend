package com.example.jobApplication.Company.impl;

import com.example.jobApplication.Company.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ReviewRepository reviewRepository;


    @Override
    @Transactional
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


//    Update company details
    @Override
    public boolean updateCompany(Long companyId, Company updatedCompany) {
        Company com = companyRepository.findById(companyId).orElse(null);
        if(com != null){
            com.setName(updatedCompany.getName());
            com.setDescription(updatedCompany.getDescription());
            companyRepository.save(com);
            return true;
        }

        return false;

    }


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



//    GET / companies/ {companyId}/reviews
//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}