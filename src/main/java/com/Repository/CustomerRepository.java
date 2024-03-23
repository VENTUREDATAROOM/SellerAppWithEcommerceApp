package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	// UserDetails findByCustomerMobileNumber(String username);

	Customer findByCustomerMobileNumber(String mobileNumber);

	Customer findByCustomerUserCode(String customerUserCode);

}
