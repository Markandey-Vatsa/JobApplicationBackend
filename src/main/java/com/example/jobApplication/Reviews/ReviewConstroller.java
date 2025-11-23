package com.example.jobApplication.Reviews;

import com.example.jobApplication.Company.impl.CompanyServiceImpl;
import com.example.jobApplication.Reviews.Impl.ReviewServiceImpl;
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
    public ResponseEntity<?> postReviewToCompany(@PathVariable Long companyId,@Valid @RequestBody Review review){
        boolean ans = reviewService.addReviewToCompany(companyId, review);
        return ans? new ResponseEntity<>("Review added successfully",HttpStatus.CREATED): new ResponseEntity<>("Company does not exist.",HttpStatus.NO_CONTENT);
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
        return ans? new ResponseEntity<>("Review deleted successfully", HttpStatus.OK): new ResponseEntity<>("Review not found",HttpStatus.NO_CONTENT);
    }


    //    Update particular review of particular company
    @PutMapping("/{companyId}/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review updatedReview){
        boolean ans = reviewService.UpdateReview(companyId,reviewId, updatedReview);
        return ans? new ResponseEntity<>("Review updated successfully",HttpStatus.OK): new ResponseEntity<>("Review not found",HttpStatus.NO_CONTENT);
    }

}



//    GET / companies/ {companyId}/reviews
//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}
