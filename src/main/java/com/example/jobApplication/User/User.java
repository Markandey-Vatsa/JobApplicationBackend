package com.example.jobApplication.User;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Username/Email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

//    @NotBlank(message = "Please provide link to your resume to proceed.")
//    @Column(nullable = false)
//    private String resumeLink;
//
//    @Column
//    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private List<Role> roles = new ArrayList<>();
}
