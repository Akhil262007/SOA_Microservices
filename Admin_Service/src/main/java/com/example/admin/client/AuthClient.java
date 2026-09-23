package com.example.admin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.admin.dto.UserResponse;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/auth/students")
    List<UserResponse> getAllStudents();

    @GetMapping("/auth/instructors")
    List<UserResponse> getAllInstructors();

    @PutMapping("/auth/users/{id}")
    UserResponse updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserResponse user);

    @DeleteMapping("/auth/users/{id}")
    void deleteUser(@PathVariable("id") Long id);
}