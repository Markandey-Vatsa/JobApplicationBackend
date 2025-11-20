package com.example.jobApplication.Job;

import com.example.jobApplication.Job.impl.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Job")
public class JobController {
    @Autowired
    private JobServiceImpl jobService;

//    private List<Job> Jobs = new ArrayList<>();

    @GetMapping("/Jobs")
    public List<Job>findAll(){
        return jobService.findAll();
    }


    @PostMapping("/Jobs")
    public ResponseEntity<?> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return ResponseEntity.ok("Job added successfully");

    }

    @GetMapping("/getJobById/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id){
        Job ans = jobService.getJob(id);
        return (ans == null)? new ResponseEntity<>("Job does not exist.",HttpStatus.NOT_FOUND):new ResponseEntity<>(ans,HttpStatus.OK);
    }


    @DeleteMapping("/Jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id){
        boolean ans = jobService.deleteJob(id);
        return ans?new ResponseEntity("Job deleted successfully",HttpStatus.OK):new ResponseEntity("Job not found",HttpStatus.NO_CONTENT);
    }


    @PutMapping("/Jobs/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id,@RequestBody Job updatedJob){
        boolean ans = jobService.updateJob(id,updatedJob);
        return ans?new ResponseEntity<>("Job updated successfully",HttpStatus.OK): new ResponseEntity<>("Job not found",HttpStatus.NO_CONTENT);
    }


}

//GET /jobs: Get all jobs
//GET /jobs/{id}: Get a specific job by ID
//POST /jobs: Create a new job (request body should contain the job details)
//DELETE /jobs/{id}: Delete a specific job by ID
//PUT /jobs/{id}: Update a specific job by ID (request body should contain the updated job details)
