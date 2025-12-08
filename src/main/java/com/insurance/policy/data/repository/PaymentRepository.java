package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByPaymentStatus(String status);
}
