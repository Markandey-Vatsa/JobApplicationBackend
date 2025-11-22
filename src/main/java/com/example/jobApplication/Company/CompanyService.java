package com.example.jobApplication.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    List<Review> getReviewsByCompanyId(Long companyId);
    boolean addReviewToCompany(Long companyId, Review review);
    Review getReviewById(Long companyId, Long reviewId);
    boolean UpdateReview(Long companyId, Long reviewId, Review updatedReview);
    boolean deleteReview(Long companyId, Long reviewId);
    void addCompany(Company company);
    boolean updateCompany(Long companyId, Company updatedCompany);
    Company getCompanyById(Long companyId);
    boolean deleteCompany(Long companyId);
}

//    GET / companies/ {companyId}/reviews
//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}