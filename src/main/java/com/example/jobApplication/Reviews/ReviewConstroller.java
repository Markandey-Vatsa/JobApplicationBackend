package com.example.jobApplication.Reviews;

import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Company.impl.CompanyServiceImpl;
import com.example.jobApplication.Reviews.Impl.ReviewServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reviews")
public class ReviewConstroller {

    @Autowired
    private ReviewServiceImpl reviewService;

    //    Get all review by company id
    @GetMapping("/{id}")
    public ResponseEntity<?> getACompanyReviews(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getReviewsByCompanyId(id), HttpStatus.OK);
    }

    //    Post review to company
    @PostMapping("/{companyId}")
    public ResponseEntity<?> postReviewToCompany(
            @PathVariable Long companyId,
            @RequestParam Long applicantId,
            @Valid @RequestBody Review review) {

        try {
            reviewService.addReviewToCompany(companyId, review, applicantId);
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

//    Get particular review of particular company
    @GetMapping("/{companyId}/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Long companyId,@PathVariable Long reviewId){
        Review r = reviewService.getReviewById(companyId,reviewId);
        if(r == null) return new ResponseEntity<>("No reviews yet",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }


    //    Delete particular review of particular company
    @DeleteMapping("/{companyId}/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId){
        boolean ans = reviewService.deleteReview(companyId,reviewId);
        return ans? new ResponseEntity<>("Review deleted successfully", HttpStatus.OK): new ResponseEntity<>("Review not found",HttpStatus.NOT_FOUND);
    }


    //    Update particular review of particular company
    @PutMapping("/{companyId}/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId,@RequestParam Long applicantId, @RequestBody Review updatedReview){
        try {
            reviewService.UpdateReview(companyId, reviewId,applicantId,updatedReview);
        }catch(Exception e){
            return new ResponseEntity<>("Couldn't update the review: "+e.getMessage(),HttpStatus.NOT_FOUND);
        }
       return new ResponseEntity<>("Review updated successfully",HttpStatus.OK);
    }

}



//    GET / companies/ {companyId}/reviews
//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}
