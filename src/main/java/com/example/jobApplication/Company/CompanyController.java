package com.example.jobApplication.Company;


import com.example.jobApplication.Company.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyService;


//    Add the company
    @PostMapping("/")
    public ResponseEntity<?> addCompany(@RequestBody Company company){
        companyService.addCompany(company);
        return new ResponseEntity<>("Company added successfully", HttpStatus.OK);
    }

//    Get all companies
    @GetMapping("/")
    public ResponseEntity<?> getAllCompanies() {
         return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }


//    Update company details
@PutMapping("/{companyId}")
public ResponseEntity<?> updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany){
        boolean ans = companyService.updateCompany(companyId, updatedCompany);
        return ans? new ResponseEntity<>("Company updated successfully",HttpStatus.OK): new ResponseEntity<>("Company does not exist.",HttpStatus.NO_CONTENT);
}


//    Get all review by company id
    @GetMapping("/reviews/{id}")
    public ResponseEntity<?> getACompanyReviews(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.getReviewsByCompanyId(id),HttpStatus.OK);
    }


//    Post review to company
    @PostMapping("reviews/{companyId}")
    public ResponseEntity<?> postReviewToCompany(@PathVariable Long companyId, @RequestBody Review review){
        boolean ans = companyService.addReviewToCompany(companyId, review);
        return ans? new ResponseEntity<>("Review added successfully",HttpStatus.OK): new ResponseEntity<>("Company does not exist.",HttpStatus.NO_CONTENT);
    }


//    Get particular review of particular company
    @GetMapping("review/{companyId}/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Long companyId,@PathVariable Long reviewId){
        Review r = companyService.getReviewById(companyId,reviewId);
        if(r == null) return new ResponseEntity<>("No reviews yet",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

//    Delete particular review of particular company
    @DeleteMapping("review/{companyId}/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId){
        boolean ans = companyService.deleteReview(companyId,reviewId);
        return ans? new ResponseEntity<>("Review deleted successfully",HttpStatus.OK): new ResponseEntity<>("Review not found",HttpStatus.NO_CONTENT);
    }

//    Update particular review of particular company
@PutMapping("review/{companyId}/{reviewId}")
public ResponseEntity<?> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review updatedReview){
        boolean ans = companyService.UpdateReview(companyId,reviewId, updatedReview);
        return ans? new ResponseEntity<>("Review updated successfully",HttpStatus.OK): new ResponseEntity<>("Review not found",HttpStatus.NO_CONTENT);
}



}



//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}