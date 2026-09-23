package com.example.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.admin.client.AuthClient;
import com.example.admin.dto.UserResponse;

@Service
public class AdminService {

    private final AuthClient authClient;

    public AdminService(AuthClient authClient) {
        this.authClient = authClient;
    }

    // View all students
    public List<UserResponse> getAllStudents() {
        return authClient.getAllStudents();
    }

    // View all instructors
    public List<UserResponse> getAllInstructors() {
        return authClient.getAllInstructors();
    }

    // Update student or instructor
    public UserResponse updateUser(
            Long id,
            UserResponse user) {

        return authClient.updateUser(id, user);
    }

    // Delete student or instructor
    public void deleteUser(Long id) {
        authClient.deleteUser(id);
    }
}