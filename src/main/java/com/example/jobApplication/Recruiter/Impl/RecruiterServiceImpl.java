package com.example.jobApplication.Recruiter.Impl;

import com.example.jobApplication.Company.Company;
import com.example.jobApplication.Company.CompanyRepository;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobRepository;
import com.example.jobApplication.Recruiter.Recruiter;
import com.example.jobApplication.Recruiter.RecruiterRepository;
import com.example.jobApplication.Recruiter.RecruiterService;
import com.example.jobApplication.User.Role;
import com.example.jobApplication.User.User;
import com.example.jobApplication.User.UserRepository;
import com.example.jobApplication.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    RecruiterServiceImpl(RecruiterRepository recruiterRepository,
                         UserRepository userRepository,
                         UserService userService,
                         CompanyRepository companyRepository){
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CompanyRepository companyRepository;

//    @Autowired
//    private JobRepository jobRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void addRecruiter(Recruiter recruiter) {


        // Fetch company
        Company com = companyRepository.findById(recruiter.getCompany().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Company does not exist on platform. Please register your company first."
                ));

        // Fetch USER from DB
        Long userId = recruiter.getUser().getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "This account does not exist, kindly create your user account first."
                ));

        // Assign role on managed entity
        user.getRoles().add(Role.RECRUITER);

        // Attach correct references
        recruiter.setUser(user);
        recruiter.setCompany(com);

        // Save recruiter only
        recruiterRepository.save(recruiter);

    }

    @Override
    @Transactional
    public void updateRecruiter(Long recruiterId, Recruiter updatedRecruiter) {
        Recruiter rec = recruiterRepository.findById(recruiterId).orElseThrow(()-> new EntityNotFoundException("Recruiter not found"));
        userService.updateUser(rec.getUser().getUserId(),updatedRecruiter.getUser());
        rec.setPhoneNumber(updatedRecruiter.getPhoneNumber() != null ? updatedRecruiter.getPhoneNumber() : rec.getPhoneNumber());
        rec.setCompany(updatedRecruiter.getCompany() != null ? updatedRecruiter.getCompany() : rec.getCompany());
        rec.setJobs(updatedRecruiter.getJobs() != null ? updatedRecruiter.getJobs() : rec.getJobs());

        recruiterRepository.save(rec);
    }

    @Override
    public boolean deleteRecruiter(Long recruiterId) {
        if(!recruiterRepository.existsById(recruiterId)) return false;
       try{
           recruiterRepository.deleteById(recruiterId);
           return true;
       }catch(Exception e){
           return false;
       }
    }

    @Override
    public Recruiter getRecruiterById(Long recruiterId) {
       return recruiterRepository.findById(recruiterId).orElse(null);
    }

    public List<Recruiter> getAllRecruiters(){
        return recruiterRepository.findAll();
    }


//    Get all jobs posted by a recruiter
    public List<Job> getJobsByRecruiter(Long recruiterId) {
        Recruiter rec = recruiterRepository.findById(recruiterId).orElse(null);
        if (rec == null) return List.of();
        return rec.getJobs();
    }

//    Get all recruiters by company
    @Override
    public List<Recruiter> getRecruitersByCompany(Long companyId) {
        Company com = companyRepository.findById(companyId).orElse(null);
        if (com == null) return List.of();
        return com.getRecruiters();
    }

}
