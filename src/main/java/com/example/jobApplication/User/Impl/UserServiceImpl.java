package com.example.jobApplication.User.Impl;

import com.example.jobApplication.User.User;
import com.example.jobApplication.User.UserRepository;
import com.example.jobApplication.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    Create user
    @Override
    public void addUser(User user) {

        String email = user.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already in use");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }

        userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }


    @Override
    @Transactional
    public void updateUser(Long userId,User updatedUser){
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));
        String newEmail = (updatedUser.getEmail() != null) ? updatedUser.getEmail() : user.getEmail();
        if(!newEmail.equals(user.getEmail()) && userRepository.existsByEmail(newEmail)){
            throw new IllegalArgumentException("Email already in use");
        }
        user.setName((updatedUser.getName() != null)? updatedUser.getName(): user.getName());
        user.setEmail(newEmail);
        user.setPassword((updatedUser.getPassword() != null)? updatedUser.getPassword(): user.getPassword());
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
