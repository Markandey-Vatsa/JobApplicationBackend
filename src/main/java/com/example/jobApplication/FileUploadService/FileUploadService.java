package com.example.jobApplication.FileUploadService;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {


    ResponseEntity<?> uploadResume(MultipartFile file, Authentication authentication);
    ResponseEntity<Resource> getResume(Authentication authentication);
    ResponseEntity<?> updateResume(MultipartFile file, Authentication authentication);
    ResponseEntity<?> deleteResume(Authentication authentication);
}
