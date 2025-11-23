package com.example.jobApplication.Company.impl;

import com.example.jobApplication.Company.*;

import com.example.jobApplication.Reviews.Review;
import com.example.jobApplication.Reviews.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;


    @Override
    @Transactional
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteCompany(Long companyId) {
        try {
            companyRepository.deleteById(companyId);
            return true;
        }catch(Exception e){
            return false;
        }
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
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }


}



//    GET / companies/ {companyId}/reviews
//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}