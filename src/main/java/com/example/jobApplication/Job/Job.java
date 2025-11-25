package com.example.jobApplication.Job;



import com.example.jobApplication.Application.Application;
import com.example.jobApplication.Company.Company;
import com.example.jobApplication.Recruiter.Recruiter;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "job_table")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 200)
    private String title;
    @Column()
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company",nullable = false)
    @JsonBackReference("company-jobs")
    private Company company;


    @Column()
    private String location;

    @Column()
    private String maxSalary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id",nullable = false)
    @JsonBackReference("recruiter-jobs")
    private Recruiter recruiter;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("job-applications")
    private List<Application> applications = new ArrayList<>();

    @Column()
    private String minSalary;
}
