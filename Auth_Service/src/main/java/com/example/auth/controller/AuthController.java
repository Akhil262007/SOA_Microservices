package com.example.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.auth.entity.User;
import com.example.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    // Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return authService.login(
                user.getUsername(),
                user.getPassword()
        );
    }
    
 // Get all students
    @GetMapping("/students")
    public List<User> getAllStudents() {
        return authService.getAllStudents();
    }

    // Get all instructors
    @GetMapping("/instructors")
    public List<User> getAllInstructors() {
        return authService.getAllInstructors();
    }
    
 // Update student or instructor by ID
    @PutMapping("/users/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        return authService.updateUser(id, updatedUser);
    }
    
 // Delete student or instructor by ID
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {

        authService.deleteUser(id);
    }
}