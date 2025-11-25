package com.example.jobApplication.Recruiter.Impl;

import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Job.JobRepository;
import com.example.jobApplication.Recruiter.Recruiter;
import com.example.jobApplication.Recruiter.RecruiterRepository;
import com.example.jobApplication.Recruiter.RecruiterService;
import com.example.jobApplication.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private JobRepository jobRepository;

    @Override
    public void addRecruiter(Recruiter recruiter) {
        recruiterRepository.save(recruiter);
    }

    @Override
    @Transactional
    public void updateRecruiter(Long recruiterId, Recruiter updatedRecruiter) {
        Recruiter rec = recruiterRepository.findById(recruiterId).orElseThrow(()-> new EntityNotFoundException("Recruiter not found"));


            rec.getUser().setName(updatedRecruiter.getUser().getName());

            String email = updatedRecruiter.getUser().getEmail();
            if(!email.equals(rec.getUser().getEmail()) && userRepository.existsByEmail(email)){
                throw new IllegalArgumentException("Email already in use");
            }
            rec.getUser().setEmail(updatedRecruiter.getUser().getEmail());
            rec.setPhoneNumber(updatedRecruiter.getPhoneNumber());
            recruiterRepository.save(rec);


    }

    @Override
    public boolean deleteRecruiter(Long recruiterId) {
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


}
