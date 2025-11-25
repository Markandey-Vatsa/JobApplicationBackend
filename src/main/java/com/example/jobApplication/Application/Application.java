package com.example.jobApplication.Application;


import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Recruiter.Recruiter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Application{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JsonBackReference("applicant-applications")
    private Applicant applicant;

    @Column
    private Long recruiterId;


    // Many applications belong to one job
    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference("job-applications") // child -> backref to parent Job
    private Job job;

    @Column(length = 2000)
    private String description;

    @Column
    private String profileLink;

    @Column
    private String resumeLink;

    @Column(nullable = false)
    private LocalDate appliedDate = LocalDate.now();


}
