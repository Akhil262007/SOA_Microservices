package com.example.enrollment.dto;

import java.time.LocalDateTime;

public class PaymentRequest {

    private Long enrollmentId;
    private Long studentId;
    private Double amount;
    private String status;
    private LocalDateTime paymentDate;

    public PaymentRequest() {
    }

    public PaymentRequest(Long enrollmentId, Long studentId, Double amount,
                          String status, LocalDateTime paymentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}