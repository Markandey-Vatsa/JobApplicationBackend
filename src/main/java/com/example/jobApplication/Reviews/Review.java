package com.example.jobApplication.Reviews;
import com.example.jobApplication.Company.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;



@Getter
@Setter
@Entity
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

    @Column(nullable = false, length = 200)
    private String  reviewerName;

    @Column(nullable = false)
    private String  reviewText;

    @Min(0)
    @Max(10)
    @Column
    private int rating;

}
