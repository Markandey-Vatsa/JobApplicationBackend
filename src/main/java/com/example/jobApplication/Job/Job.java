package com.example.jobApplication.Job;


import com.example.jobApplication.Company.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    @JsonBackReference
    private Company company;

    @Column(name = "company_name",nullable = false)
    private Long companyId;

    @Column()
    private String location;

    @Column()
    private String maxSalary;

    @Column()
    private String minSalary;

    public Job(Long id, String title, String description, Company company, String location, String maxSalary, String minSalary) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.location = location;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }
}
