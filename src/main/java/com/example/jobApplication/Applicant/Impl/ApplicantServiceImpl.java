package com.example.jobApplication.Applicant.Impl;

import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Applicant.ApplicantRepository;
import com.example.jobApplication.Applicant.ApplicantService;
import com.example.jobApplication.Application.ApplicationRepository;
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


@Service
public class ApplicantServiceImpl implements ApplicantService {

    ApplicantServiceImpl(ApplicantRepository applicantRepository, UserRepository userRepository){
        this.applicantRepository = applicantRepository;
        this.userRepository = userRepository;
    }
    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void addApplicant(Applicant applicant) {
        Long userId = applicant.getUser().getUserId();  // must come from request
        User dbUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        dbUser.getRoles().add(Role.APPLICANT);
        applicant.setUser(dbUser);
        applicantRepository.save(applicant);
    }

    @Override
    @Transactional
    public void updateApplicant(Long applicantId, Applicant updatedApplicant) {

        Applicant app = applicantRepository.findById(applicantId).orElseThrow(() -> new EntityNotFoundException("Applicant not found"));
        if(updatedApplicant != null){
          app.setUser(updatedApplicant.getUser()!=null ? updatedApplicant.getUser() : app.getUser());
          app.setResumeLink(updatedApplicant.getResumeLink() != null ? updatedApplicant.getResumeLink() : app.getResumeLink());
          app.setDescription(updatedApplicant.getDescription()!= null ? updatedApplicant.getDescription() : app.getDescription());
        }

        applicantRepository.save(app);
    }

    @Override
    public boolean deleteApplicant(Long applicantId) {
       if(!applicantRepository.existsById(applicantId)) return false;
       try{
           applicantRepository.deleteById(applicantId);
           return true;
       }catch(Exception e){
           return false;
       }
    }

    @Override
    public Applicant getApplicantById(Long applicantId) {
       return applicantRepository.findById(applicantId).orElse(null);
    }

}
