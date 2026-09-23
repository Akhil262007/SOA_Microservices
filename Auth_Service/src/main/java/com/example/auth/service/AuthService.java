package com.example.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.util.JwtUtil;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Register User
    public User register(User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }

    // Login User and Generate JWT
    public String login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
    
 // Get all students
    public List<User> getAllStudents() {
        return userRepository.findByRole("STUDENT");
    }

    // Get all instructors
    public List<User> getAllInstructors() {
        return userRepository.findByRole("INSTRUCTOR");
    }
    
 // Update student or instructor by ID
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + id));

        // Only STUDENT and INSTRUCTOR can be managed
        if (!existingUser.getRole().equals("STUDENT")
                && !existingUser.getRole().equals("INSTRUCTOR")) {

            throw new RuntimeException(
                    "Admin can only update students and instructors");
        }

        // Update username if provided
        if (updatedUser.getUsername() != null
                && !updatedUser.getUsername().isBlank()) {

            User userWithSameUsername =
                    userRepository.findByUsername(updatedUser.getUsername());

            // Username belongs to another user
            if (userWithSameUsername != null
                    && !userWithSameUsername.getId().equals(id)) {

                throw new RuntimeException("Username already exists");
            }

            existingUser.setUsername(updatedUser.getUsername());
        }

        // Update password if provided
        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {

            existingUser.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(existingUser);
    }
    
 // Delete student or instructor by ID
    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + id));

        // Only STUDENT and INSTRUCTOR can be deleted
        if (!existingUser.getRole().equals("STUDENT")
                && !existingUser.getRole().equals("INSTRUCTOR")) {

            throw new RuntimeException(
                    "Admin can only delete students and instructors");
        }

        userRepository.delete(existingUser);
    }
}