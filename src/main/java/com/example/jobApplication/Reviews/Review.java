package com.example.jobApplication.Reviews;
import com.example.jobApplication.Company.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.example.jobApplication.Applicant.Applicant;
import java.time.LocalDateTime;


//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//public class Review {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long reviewId;
//
//    @ManyToOne
//    @JoinColumn(name = "company_id", nullable = false)
//    @JsonBackReference("company-reviews")
//    private Company company;
//
//    @Column(nullable = false, length = 200)
//    private String  reviewerName;
//
//    @Column(nullable = false)
//    private String  reviewText;
//
//    @Min(0)
//    @Max(10)
//    @Column
//    private int rating;
//
//}



@Entity
@Table(
        name = "review",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"applicant_id", "company_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    // Who wrote review
    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    // Which company
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference("company-reviews")
    private Company company;

    @Column(nullable = false, length = 2000)
    private String reviewText;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
