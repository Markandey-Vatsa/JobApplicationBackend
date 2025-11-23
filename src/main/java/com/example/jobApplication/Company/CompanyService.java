package com.example.jobApplication.Company;

import com.example.jobApplication.Reviews.Review;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    void addCompany(Company company);
    boolean updateCompany(Long companyId, Company updatedCompany);
    Company getCompanyById(Long companyId);
    boolean deleteCompany(Long companyId);
}

