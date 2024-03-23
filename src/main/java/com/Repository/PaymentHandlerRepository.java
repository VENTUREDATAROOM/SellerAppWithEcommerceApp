package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.PaymentHandler;

public interface PaymentHandlerRepository extends JpaRepository<PaymentHandler, Long> {

	PaymentHandler findByOrderId(String orderId);

}
