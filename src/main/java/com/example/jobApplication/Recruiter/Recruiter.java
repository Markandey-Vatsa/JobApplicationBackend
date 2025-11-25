package com.example.jobApplication.Recruiter;

import com.example.jobApplication.Company.Company;
import com.example.jobApplication.Job.Job;
import com.example.jobApplication.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruiterId;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference("company-recruiters")
    private Company company;

    @OneToMany(mappedBy = "recruiter")
    @JsonManagedReference("recruiter-jobs")
    private List<Job> jobs = new ArrayList<>();


}
