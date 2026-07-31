package com.example.jobApplication.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    FileUploadController(FileUploadService fileUploadService){
        this.fileUploadService = fileUploadService;
    }

    private final FileUploadService fileUploadService;

    @PostMapping("/resume")
    public ResponseEntity<?> uploadResume(@RequestParam MultipartFile file,
                                          Authentication authentication) {
        return fileUploadService.uploadResume(file, authentication);
    }

    @GetMapping("/resume")
    public ResponseEntity<Resource> getResume(Authentication authentication) {
        return fileUploadService.getResume(authentication);
    }

    @PutMapping("/resume")
    public ResponseEntity<?> updateResume(@RequestParam MultipartFile file,
                                          Authentication authentication) {
        return fileUploadService.updateResume(file, authentication);
    }

    @DeleteMapping("/resume")
    public ResponseEntity<?> deleteResume(Authentication authentication) {
        return fileUploadService.deleteResume(authentication);
    }
}