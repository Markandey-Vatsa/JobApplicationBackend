package com.example.jobApplication.Application;
import com.example.jobApplication.Application.Impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationServiceImpl applicationService;


//    Get all applications for a recruiter
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<?> getApplicationsForRecruiter(@PathVariable Long recruiterId) {
        List<Application> apps = applicationService.getApplicationsByRecruiterId(recruiterId);
        if(apps.isEmpty()){
            return new ResponseEntity<>("No applications yet.",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(apps, HttpStatus.OK);

    }


    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<?> getApplicationsForApplicant(@PathVariable Long applicantId) {
        List<Application> apps = applicationService.getAllApplicationsByApplicantId(applicantId);

        if(apps.isEmpty()) return new ResponseEntity<>("No applications yet.",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

    @PostMapping("/applicant/")
    public ResponseEntity<?> addApplication(@RequestBody Application application){
        boolean added = applicationService.addApplication(application);
        return added? new ResponseEntity<>("Application submitted successfully",HttpStatus.CREATED):new ResponseEntity<>("Application could not be submitted due to some error.",HttpStatus.BAD_REQUEST);
    }


//    Recruiter can delete an application in case of rejection and applicant can also delete applicaiton
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long applicationId){
        return applicationService.deleteApplication(applicationId) ? new ResponseEntity<>("Application deleted successfully",HttpStatus.OK) : new ResponseEntity<>("Application not found",HttpStatus.NO_CONTENT);
    }


}
