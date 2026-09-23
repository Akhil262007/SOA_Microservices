package com.example.enrollment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enrollment.client.CourseClient;
import com.example.enrollment.client.PaymentClient;
import com.example.enrollment.dto.CourseResponse;
import com.example.enrollment.dto.PaymentRequest;
import com.example.enrollment.dto.PaymentResponse;
import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseClient courseClient;

    private final PaymentClient paymentClient;

    // Create Enrollment
 // Create Enrollment
 // Create Enrollment
    public Enrollment createEnrollment(
            Enrollment enrollment,
            String authorizationHeader) {

    	CourseResponse course =
    	        courseClient.getCourseById(
    	                enrollment.getCourseId(),
    	                authorizationHeader);

        if (LocalDate.now().isAfter(course.getEnrollmentDeadline())) {

            throw new RuntimeException("Enrollment deadline has passed");

        }

        String response =
                courseClient.reserveSeat(
                        enrollment.getCourseId(),
                        authorizationHeader);

        System.out.println("Course Service Response: " + response);

        // Save enrollment as PENDING first

        enrollment.setStatus("PENDING");

        enrollment.setEnrollmentDate(LocalDateTime.now());

        Enrollment savedEnrollment =
                enrollmentRepository.save(enrollment);

        // Create Payment Request

        PaymentRequest paymentRequest = new PaymentRequest();

        paymentRequest.setEnrollmentId(savedEnrollment.getId());

        paymentRequest.setStudentId(savedEnrollment.getStudentId());

        paymentRequest.setAmount(5000.0);

        paymentRequest.setStatus("SUCCESS");

        paymentRequest.setPaymentDate(LocalDateTime.now());

        // Call Payment Service

        try {

        	PaymentResponse paymentResponse =
        	        paymentClient.makePayment(paymentRequest);

            if (paymentResponse == null) {

                savedEnrollment.setStatus("PAYMENT_FAILED");
                enrollmentRepository.saveAndFlush(savedEnrollment);

                throw new RuntimeException("No response received from Payment Service.");
            }

            if ("SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())) {

                savedEnrollment.setStatus("CONFIRMED");

            } else {

                savedEnrollment.setStatus("PAYMENT_FAILED");
                enrollmentRepository.saveAndFlush(savedEnrollment);

                throw new RuntimeException(
                        "Payment was not successful. Status: "
                                + paymentResponse.getStatus());
            }

        } catch (RuntimeException e) {

            // Re-throw business/payment failure exceptions
            throw e;

        } catch (Exception e) {

            // Payment Service communication failure
            savedEnrollment.setStatus("PAYMENT_FAILED");
            enrollmentRepository.saveAndFlush(savedEnrollment);

            throw new RuntimeException(
                    "Payment Service is unavailable. Enrollment not confirmed.", e);
        }

        // Save confirmed enrollment

        return enrollmentRepository.save(savedEnrollment);
    }

    // Get All Enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Get Enrollment By ID
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Enrollment not found with ID: " + id));
    }

    // Update Enrollment Status
    public Enrollment updateEnrollmentStatus(Long id, String status) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollment.setStatus(status);

        return enrollmentRepository.save(enrollment);
    }

    // Delete Enrollment
    public void deleteEnrollment(Long id) {

        Enrollment enrollment = getEnrollmentById(id);

        enrollmentRepository.delete(enrollment);
    }

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
            CourseClient courseClient,
            PaymentClient paymentClient) {

        this.enrollmentRepository = enrollmentRepository;
        this.courseClient = courseClient;
        this.paymentClient = paymentClient;
    }
}