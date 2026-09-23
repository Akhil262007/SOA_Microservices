package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Modifying
    @Query("""
        UPDATE Course c
        SET c.availableSeats = c.availableSeats - 1
        WHERE c.id = :id
        AND c.availableSeats > 0
    """)
    int reserveSeat(@Param("id") Long id);
}