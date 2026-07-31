package com.example.jobApplication.Applicant;

import com.example.jobApplication.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByUser(User user);

}
