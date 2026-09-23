package com.example.instructor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstructorController {

    @GetMapping("/instructors/test")
    public String test() {
        return "Instructor access granted";
    }
}