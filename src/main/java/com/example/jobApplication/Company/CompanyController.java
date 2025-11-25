package com.example.jobApplication.Company;


import com.example.jobApplication.Company.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyService;


//    Add the company
    @PostMapping("/")
    public ResponseEntity<?> addCompany(@RequestBody Company company){
        companyService.addCompany(company);
        return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
    }

//    Get company by id
    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompany(@PathVariable Long companyId) {
        Company com = companyService.getCompanyById(companyId);
        if(com == null) {
            return new ResponseEntity<>("Company does not exist.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(com, HttpStatus.OK);
    }


//    Get all companies
    @GetMapping("/")
    public ResponseEntity<?> getAllCompanies() {
         return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }


//    Update company details
@PutMapping("/{companyId}")
public ResponseEntity<?> updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany){
     try {
         companyService.updateCompany(companyId, updatedCompany);
         return new ResponseEntity<>("Company details updated successfully", HttpStatus.OK);
     }catch(Exception e){
         return new ResponseEntity<>("Couldn't Update company details, Please make sure company exists and email is unique.",HttpStatus.NO_CONTENT);
     }
}


//Delete a particular company
    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyId){
        boolean ans = companyService.deleteCompany(companyId);
        return ans? new ResponseEntity<>("Company deleted successfully",HttpStatus.OK): new ResponseEntity<>("Company not found",HttpStatus.NO_CONTENT);
    }

}



//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}