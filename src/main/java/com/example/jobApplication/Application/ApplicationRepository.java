package com.example.jobApplication.Application;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

//    List<Application> findByJobId(Long jobId);
    // ApplicationRepository
    List<Application> findByApplicantApplicantId(Long applicantId);


    List<Application> findAllByRecruiterId(Long recruiterId);
//    @Query("SELECT a FROM Application a JOIN a.job j WHERE j.jobCode = :jobCode")
//    List<Application> findAllByJobId(@Param("jobCode") Long jobCode);


}
