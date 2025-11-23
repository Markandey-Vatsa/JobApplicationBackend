package com.example.jobApplication.Company;


import com.example.jobApplication.Job.Job;
import com.example.jobApplication.Reviews.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "company")
    @JsonManagedReference
    private List<Job> jobs = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

}


//    GET / companies/ {companyId}/reviews
//    POST / companies/ {companyId}/reviews
//    GET / companies/{companyId}/reviews/ {reviewId}
//    PUT / companies/ {companyId} /reviews/ {reviewId}
//    DELETE /companies/ {companyId}/reviews/ {reviewId}