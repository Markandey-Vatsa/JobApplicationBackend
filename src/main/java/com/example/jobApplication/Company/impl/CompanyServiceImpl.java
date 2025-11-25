package com.example.jobApplication.Company.impl;

import com.example.jobApplication.Company.*;

import com.example.jobApplication.Reviews.Review;
import com.example.jobApplication.Reviews.ReviewRepository;
import com.example.jobApplication.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;


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
    @Transactional
    public void updateCompany(Long companyId, Company updatedCompany) {
        Company com = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("Applicant not found"));
        com.getUser().setName((updatedCompany.getUser().getName() != null? updatedCompany.getUser().getName(): com.getUser().getName()));
        com.setDescription(updatedCompany.getDescription()!= null? updatedCompany.getDescription(): com.getDescription());
        String email = updatedCompany.getUser().getEmail();

        if(userRepository.existsByEmail(email) && !email.equals(com.getUser().getEmail())){
            throw new IllegalArgumentException("Email already in use");
        }
        com.getUser().setEmail(updatedCompany.getUser().getEmail() != null ? updatedCompany.getUser().getEmail() : com.getUser().getEmail());
        com.getUser().setPassword(updatedCompany.getUser().getPassword() != null ? updatedCompany.getUser().getPassword() : com.getUser().getPassword());
        companyRepository.save(com);
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