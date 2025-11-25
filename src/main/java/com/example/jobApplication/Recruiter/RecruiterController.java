package com.example.jobApplication.Recruiter;

import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Recruiter.Impl.RecruiterServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {
    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/")
    public ResponseEntity<?> addRecruiter(@RequestBody Recruiter recruiter){
        recruiterService.addRecruiter(recruiter);
        return new ResponseEntity<>("Recruiter Profile created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{recruiterId}")
    public ResponseEntity<?> updateRecruiter(@PathVariable Long recruiterId, @RequestBody Recruiter updatedRecruiter) {
        try {
            recruiterService.updateRecruiter(recruiterId, updatedRecruiter);
            return new ResponseEntity<>("Recruiter details updated successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Couldn't Update recruiter details, Please make sure recruiter exists and email is unique.",HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/{recruiterId}")
    public ResponseEntity<?> getRecruiter(@PathVariable Long recruiterId) {
        Recruiter rec = recruiterService.getRecruiterById(recruiterId);
        if(rec == null) {
            return new ResponseEntity<>("Recruiter does not exist.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rec, HttpStatus.OK);
    }

    @DeleteMapping("/{recruiterId}")
    public ResponseEntity<?> deleteRecruiter(@PathVariable Long recruiterId) {
        boolean ans = recruiterService.deleteRecruiter(recruiterId);
        return ans ? new ResponseEntity<>("Recruiter profile deleted successfully",HttpStatus.OK) : new ResponseEntity<>("Recruiter not found",HttpStatus.NO_CONTENT);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllRecruiters(){
        return new ResponseEntity<>(recruiterService.getAllRecruiters(),HttpStatus.OK);
    }

//    Get all jobs posted by a particular recruiter
    @GetMapping("/jobs/{recruiterId}")
    public ResponseEntity<?> getJobsByRecruiter(@PathVariable Long recruiterId){
        List<Job> jobs = recruiterService.getJobsByRecruiter(recruiterId);
        if(jobs.isEmpty()){
            return new ResponseEntity<>("No jobs posted.",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs,HttpStatus.OK);

    }

//    Get all recruiters by company
    @GetMapping("/recruiter/{companyId}")
    public ResponseEntity<?> getRecruitersByCompany(@PathVariable Long companyId){
        List<Recruiter> recruiters = recruiterService.getRecruitersByCompany(companyId);
        if(recruiters.isEmpty()){
            return new ResponseEntity<>("No recruiters found for this company.",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recruiters,HttpStatus.OK);
    }
}
