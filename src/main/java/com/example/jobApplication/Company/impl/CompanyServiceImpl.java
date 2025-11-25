package com.example.jobApplication.Company.impl;

import com.example.jobApplication.Company.*;

import com.example.jobApplication.Reviews.Review;
import com.example.jobApplication.Reviews.ReviewRepository;
import com.example.jobApplication.User.Role;
import com.example.jobApplication.User.UserRepository;
import com.example.jobApplication.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public void addCompany(Company company) {
        company.getUser().getRoles().add(Role.COMPANY);
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
        com.setDescription(updatedCompany.getDescription()!= null? updatedCompany.getDescription(): com.getDescription());
        com.setJobs(updatedCompany.getJobs()!=null ? updatedCompany.getJobs(): com.getJobs());
        com.setRecruiters(updatedCompany.getRecruiters()!=null ? updatedCompany.getRecruiters(): com.getRecruiters());
        com.setUser(updatedCompany.getUser()!=null ? updatedCompany.getUser(): com.getUser());
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