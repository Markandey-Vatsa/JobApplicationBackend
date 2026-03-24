package com.example.jobApplication.Security;

import com.example.jobApplication.User.Role;
import com.example.jobApplication.User.User;
import com.example.jobApplication.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean adminExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getRoles().contains(Role.ADMIN));

        if (!adminExists) {

            User admin = new User();
            admin.setEmail("admin@test.com");
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // ✅ encoded
            admin.getRoles().add(Role.ADMIN); // ✅ enum
            admin.getRoles().add(Role.USER);
            userRepository.save(admin);

            System.out.println("Admin created: admin@test.com / admin123");
        }
    }
}
