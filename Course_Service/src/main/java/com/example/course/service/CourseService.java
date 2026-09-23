package com.example.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course.entity.Course;
import com.example.course.repository.CourseRepository;

import org.springframework.transaction.annotation.Transactional;
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Create Course
    public Course createCourse(Course course) {
        course.setAvailableSeats(course.getCapacity());
        return courseRepository.save(course);
    }

    // Get All Courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get Course By ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
    }

    // Update Course
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = getCourseById(id);

        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setCourseCode(updatedCourse.getCourseCode());
        existingCourse.setInstructorName(updatedCourse.getInstructorName());
        existingCourse.setCapacity(updatedCourse.getCapacity());
        existingCourse.setEnrollmentDeadline(updatedCourse.getEnrollmentDeadline());

        return courseRepository.save(existingCourse);
    }

    // Delete Course
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
    
    @Transactional
    public boolean reserveSeat(Long id) {

        int updatedRows = courseRepository.reserveSeat(id);

        return updatedRows > 0;
    }
}