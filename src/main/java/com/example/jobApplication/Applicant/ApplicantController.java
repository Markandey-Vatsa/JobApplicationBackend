package com.example.jobApplication.Applicant;

import com.example.jobApplication.Applicant.Impl.ApplicantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    @Autowired
    ApplicantService applicantService;

//    Create an account
    @PostMapping("/")
    public ResponseEntity<?> addApplicant(@RequestBody Applicant applicant){
        applicantService.addApplicant(applicant);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

//   Update applicant details
  @PutMapping("/{applicantId}")
  public ResponseEntity<?> updateApplicant(@PathVariable Long applicantId,@RequestBody Applicant updatedApplicant){
        try {
            applicantService.updateApplicant(applicantId, updatedApplicant);
            return new ResponseEntity<>("Applicant details updated successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Couldn't Update applicant details, Please make sure username is correct and applicant exists.",HttpStatus.NO_CONTENT);
        }


  }

//  Delete applicant account
    @DeleteMapping("/{applicantId}")
    public ResponseEntity<?> deleteApplicant(@PathVariable Long applicantId){
        boolean ans = applicantService.deleteApplicant(applicantId);
        return !ans? new ResponseEntity<>("Applicant not found",HttpStatus.NO_CONTENT): new ResponseEntity<>("Account deleted successfully",HttpStatus.OK);
    }

//    Get applicant by id
    @GetMapping("/{applicantId}")
    public ResponseEntity<?> getApplicant(@PathVariable Long applicantId) {
        Applicant applicant = applicantService.getApplicantById(applicantId);
        if(applicant == null) return new ResponseEntity<>("Applicant does not exist.",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(applicant,HttpStatus.OK);
    }

}
