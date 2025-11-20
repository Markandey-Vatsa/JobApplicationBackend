package com.example.jobApplication.Job;


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

    @Column(nullable = false,length = 200)
    private String company;

    @Column()
    private String location;

    @Column()
    private String maxSalary;

    @Column()
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
