//package com.example.jobApplication.Applicant;
//
//import com.example.jobApplication.Application.Application;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class Applicant {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long applicantId;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    @Email
//    private String email;
//
//    @Column
//    private String resumeLink; // optional
//
//    @Column(length = 2000)
//    private String description;
//
//    // One applicant -> many applications
//    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference("applicant-applications")
//    private List<Application> applications = new ArrayList<>();
//}


package com.example.jobApplication.Applicant;

import com.example.jobApplication.Application.Application;
import com.example.jobApplication.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String resumeLink;

    @Column(length = 2000)
    private String description;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("applicant-applications")
    private List<Application> applications = new ArrayList<>();
}

