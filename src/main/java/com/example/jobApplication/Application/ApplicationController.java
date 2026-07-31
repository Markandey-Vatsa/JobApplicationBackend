package com.example.jobApplication.Application;
import com.example.jobApplication.Application.Impl.ApplicationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    public ApplicationController(ApplicationService applicationService,ApplicationRepository applicationRepository){
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
    }

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

//    Get all applications for a recruiter
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<?> getApplicationsForRecruiter(@PathVariable Long recruiterId) {
        List<Application> apps = applicationService.getApplicationsByRecruiterId(recruiterId);
        if(apps.isEmpty()){
            return new ResponseEntity<>("No applications yet.",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

    //    Get applicant resume (View resume by recruiter) for recruiter from application
    @GetMapping("/recruiter/resume/{applicationId}")
    public ResponseEntity<Resource> getApplicantResumeForRecruiter(@PathVariable Long applicationId){
       return applicationService.getApplicantResumeForRecruiter(applicationId);
    }


    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<?> getApplicationsForApplicant(@PathVariable Long applicantId) {
        List<Application> apps = applicationService.getAllApplicationsByApplicantId(applicantId);
        if(apps.isEmpty()) return new ResponseEntity<>("No applications yet.",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long Id){
     Application app = applicationRepository.findById(Id).orElse(null);
     if(app == null) return new ResponseEntity<>("Application does not exist !!",HttpStatus.NOT_FOUND);
     return new ResponseEntity<>(app,HttpStatus.OK);
    }

    @PostMapping("/applicant/")
    public ResponseEntity<?> addApplication(@RequestBody Application application){
        try {
            applicationService.addApplication(application);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>("Application couldn't not be submitted: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>("Application submitted successfully",HttpStatus.CREATED);
    }


//    Recruiter can delete an application in case of rejection and applicant can also delete applicaiton
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long applicationId){
        return applicationService.deleteApplication(applicationId) ? new ResponseEntity<>("Application deleted successfully",HttpStatus.OK) : new ResponseEntity<>("Application not found",HttpStatus.NOT_FOUND);
    }

}
