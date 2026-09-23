package com.example.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}