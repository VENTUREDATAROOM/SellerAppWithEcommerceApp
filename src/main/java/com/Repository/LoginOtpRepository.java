package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.LoginOtp;

public interface LoginOtpRepository extends JpaRepository<LoginOtp, Long> {

	LoginOtp findByCustomerOtp(String otp);

	// LoginOtp fingByCustomerOtp(String otp);

}
