package com.example.jobApplication.Application;


import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Recruiter.Recruiter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(
        name = "application",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"applicant_id", "job_id"})
        }
)
public class Application{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

//    @ManyToOne
//    @JsonBackReference("applicant-applications")
//    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    @JsonBackReference("applicant-applications")
    private Applicant applicant;


//    @Column
//    private Long recruiterId;


    // Many applications belong to one job
    @ManyToOne
    @JoinColumn(name = "job_id",nullable = false)
    @JsonBackReference("job-applications") // child -> backref to parent Job
    private Job job;

    @Column(length = 2000)
    private String description;

    @Column
    private String profileLink;

    @Column
    @JsonIgnore
    private String resumeLink;

    @Column(nullable = false)
    private LocalDate appliedDate = LocalDate.now();


}
