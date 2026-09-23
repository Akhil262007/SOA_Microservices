package com.example.enrollment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.enrollment.dto.CourseResponse;

@FeignClient(name = "course-service")
public interface CourseClient {

    @GetMapping("/courses/{id}")
    CourseResponse getCourseById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/courses/{id}/reserve-seat")
    String reserveSeat(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorizationHeader);
}