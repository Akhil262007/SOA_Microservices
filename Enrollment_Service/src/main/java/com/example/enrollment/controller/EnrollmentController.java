package com.example.enrollment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.service.EnrollmentService;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // Create Enrollment
    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(
            @RequestBody Enrollment enrollment,
            @RequestHeader("Authorization") String authorizationHeader) {

        return ResponseEntity.ok(
                enrollmentService.createEnrollment(
                        enrollment,
                        authorizationHeader));
    }

    // Get All Enrollments
    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {

        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments());
    }

    // Get Enrollment By ID
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentById(id));
    }

    // Update Enrollment Status
    @PutMapping("/{id}/status")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                enrollmentService.updateEnrollmentStatus(id, status));
    }

    // Delete Enrollment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable Long id) {

        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}