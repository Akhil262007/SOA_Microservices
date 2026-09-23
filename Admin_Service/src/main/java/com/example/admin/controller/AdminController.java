package com.example.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.admin.dto.UserResponse;
import com.example.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // View all students
    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getAllStudents() {

        return ResponseEntity.ok(
                adminService.getAllStudents());
    }

    // View all instructors
    @GetMapping("/instructors")
    public ResponseEntity<List<UserResponse>> getAllInstructors() {

        return ResponseEntity.ok(
                adminService.getAllInstructors());
    }

    // Update student or instructor
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserResponse user) {

        return ResponseEntity.ok(
                adminService.updateUser(id, user));
    }

    // Delete student or instructor
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}