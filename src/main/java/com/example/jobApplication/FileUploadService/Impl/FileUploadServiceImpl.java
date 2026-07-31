package com.example.jobApplication.FileUploadService.Impl;

import com.example.jobApplication.Applicant.Applicant;
import com.example.jobApplication.Applicant.ApplicantRepository;
import com.example.jobApplication.FileUploadService.FileUploadService;
import com.example.jobApplication.User.User;
import com.example.jobApplication.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    FileUploadServiceImpl(ApplicantRepository applicantRepository, UserRepository userRepository){
        this.applicantRepository = applicantRepository;
        this.userRepository = userRepository;
    }

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;


    private final String uploadDir = "D:/JobAppResumesUpload/";

    @Override
    public ResponseEntity<?> uploadResume(MultipartFile file, Authentication authentication) {

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        try {
            Files.write(filePath, file.getBytes());

            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Applicant applicant = applicantRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Applicant not found"));

            applicant.setResumeLink(filePath.toString());
            applicantRepository.save(applicant);

            return ResponseEntity.ok(
                    "File uploaded successfully and saved at path: " + filePath);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Resource> getResume(Authentication authentication) {

        try {

            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Applicant applicant = applicantRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Applicant not found"));

            Path path = Paths.get(applicant.getResumeLink());

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<?> updateResume(MultipartFile file,
                                          Authentication authentication) {

        try {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Applicant applicant = applicantRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Applicant not found"));

            if (applicant.getResumeLink() != null) {
                Files.deleteIfExists(Paths.get(applicant.getResumeLink()));
            }

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.write(filePath, file.getBytes());

            applicant.setResumeLink(filePath.toString());
            applicantRepository.save(applicant);

            return ResponseEntity.ok("Resume updated successfully.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteResume(Authentication authentication) {

        try {

            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Applicant applicant = applicantRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Applicant not found"));

            if (applicant.getResumeLink() == null ||
                    applicant.getResumeLink().isBlank()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No resume uploaded.");
            }

            Files.deleteIfExists(Paths.get(applicant.getResumeLink()));

            applicant.setResumeLink(null);
            applicantRepository.save(applicant);

            return ResponseEntity.ok("Resume deleted successfully.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}