package com.example.jobApplication.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);

    void updateUser(Long userId, User updatedUser);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    List<User> getAllUsers();

    Optional<User> findByEmail(String email);
}
