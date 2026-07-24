package com.example.jobApplication.User;
import com.example.jobApplication.Applicant.ApplicantRepository;
import com.example.jobApplication.Company.CompanyRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/create")
    public ResponseEntity<?> CreateUser(@RequestBody User user) {
        // Logic to create a new user
        try {
            userService.addUser(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Email already in use, please ensure that the role is selected.",
                    HttpStatus.CONFLICT);
        }
    }

//    To get information of current logged-in (Authenticated) user
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser() {
        // Get authenticated user from Spring Security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Not authenticated", HttpStatus.UNAUTHORIZED);
        }
        // The principal is the username (email in this case)
        String email = authentication.getName();
        // Fetch user by email
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return !userService.getAllUsers().isEmpty() ? new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK)
                : new ResponseEntity<>("No users found", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody @Valid User updatedUser) {
        try {
            userService.updateUser(userId, updatedUser);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not update user, please ensure the user exists and email is unique.",
                    HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
