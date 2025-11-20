package com.example.jobApplication.Job;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Job {
    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private String maxSalary;
    private String minSalary;

    public Job(Long id, String title, String description, String company, String location, String maxSalary, String minSalary) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.location = location;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }
}
